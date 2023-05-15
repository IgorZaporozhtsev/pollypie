package com.zeecoder.recipient.async;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.kafka.KafkaConsumerConfig;
import com.zeecoder.kafka.KafkaProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.core.ConditionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@Slf4j
@Testcontainers
@AutoConfigureJson
@Import({
        KafkaConsumerConfig.class,
        KafkaProducerConfig.class,
        KafkaAutoConfiguration.class
        //KafkaTopicConfig.class //need create another topic for test purpose, using @TestPropertySource
})
abstract class AbstractKafkaIntegrationTest {

    static final String TOPIC = "kitchen-test-topic";

    @Container
    static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka").withTag("7.2.1")
    ).withEnv("KAFKA_NUM_PARTITIONS", "2");

    @DynamicPropertySource
    public static void updateConfig(final DynamicPropertyRegistry registry) {
        log.info("Kafka brokers: {}", KAFKA.getBootstrapServers());
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    protected static final ConditionFactory WAIT = await()
            .atMost(Duration.ofSeconds(8))
            .pollInterval(Duration.ofSeconds(1))
            .pollDelay(Duration.ofSeconds(1));

    @Value("${spring.kafka.bootstrap-servers}")
    protected String brokers;

    @Autowired
    protected ObjectMapper objectMapper;

}