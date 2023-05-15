package com.zeecoder.recipient.async;

import com.zeecoder.common.dto.OrderPadDto;
import com.zeecoder.kafka.Producer;
import com.zeecoder.recipient.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = {Producer.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class KafkaOrderProcessingTest extends AbstractKafkaIntegrationTest {


    private Consumer<String, Object> consumer;
    @Autowired
    private Producer<OrderPadDto> producer;

    private OrderPadDto orderPadDto;

    @BeforeEach
    void setUp() {

        var id = UUID.randomUUID();
        var order = Order.builder()
                .id(id)
                .orderDefinitions(Map.of("Margarita", 2))
                .build();

        orderPadDto = OrderPadDto.builder()
                .orderId(order.getId())
                .wishes(order.getOrderDefinitions())
                .build();
    }

    @BeforeEach
    void setupKafkaOrdersConsumer() {
        final Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(brokers, "testConsumer", "true");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("com.zeecoder.domains", "com.zeecoder.common.dto");

        var consumerFactory = new DefaultKafkaConsumerFactory<String, Object>(consumerProps, new StringDeserializer(), jsonDeserializer);

        consumer = consumerFactory.createConsumer();
        consumer.subscribe(List.of(TOPIC));
    }

    @AfterEach
    void closeConsumer() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    void shouldSendKafkaMessage() {
        producer.sendMessage(TOPIC, orderPadDto.orderId(), orderPadDto);

        WAIT.untilAsserted(() -> {
            final ConsumerRecords<String, Object> records = KafkaTestUtils.getRecords(consumer);

            var singleRecord = records.iterator().next();
            log.info(" ------- shouldSendKafkaMessage, topic: {}, partition {}, offset {}------",
                    singleRecord.topic(), singleRecord.partition(), singleRecord.offset());

            assertThat(records.count()).isEqualTo(1);

            assertThat(singleRecord).isNotNull();

            assertThat(singleRecord.key()).isEqualTo(orderPadDto.orderId().toString());

            JSONAssert.assertEquals(objectMapper.writeValueAsString(orderPadDto), objectMapper.writeValueAsString(singleRecord.value()), JSONCompareMode.STRICT);
        });

    }


}
