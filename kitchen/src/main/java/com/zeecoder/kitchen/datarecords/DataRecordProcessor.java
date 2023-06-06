package com.zeecoder.kitchen.datarecords;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.kitchen.dto.DataRecord;
import com.zeecoder.kitchen.service.DataRecordsService;
import com.zeecoder.kitchen.webclient.DataRecordsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataRecordProcessor {

    private final DataRecordsClient dataRecordsClient;
    private final DataRecordsService dataRecordsService;
    private final ObjectMapper objectMapper;
    static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public void getDataRecords() {
        List<String> jsonString = getDataRecordsByAPI();
        var data = collectForDataRecords(jsonString);
        saveDadaRecords(data);
    }

    private List<String> getDataRecordsByAPI() {
        return Optional.of(alphabet.chars()
                        .mapToObj(c -> (char) c)
                        .map(dataRecordsClient::getDataRecords)
                        .toList())
                .orElseThrow();
    }

    private Map<String, JsonNode> collectForDataRecords(List<String> nodes) {
        return nodes.stream()
                .map(readNode())
                .filter(jsonNode -> !jsonNode.get("drinks").isNull())
                .map(jsonNode -> jsonNode.withArray("drinks").iterator())
                .flatMap(DataRecordProcessor::iteratorToStream)
                .collect(Collectors.toMap(node -> node.get("strDrink").asText(), Function.identity()));
    }

    private Function<String, JsonNode> readNode() {
        return stringNode -> {
            try {
                return objectMapper.readTree(stringNode);
            } catch (JsonProcessingException e) {
                throw new JsonParsingException("Error parsing JSON", e);
            }
        };
    }

    private static Stream<JsonNode> iteratorToStream(Iterator<JsonNode> iterator) {
        Iterable<JsonNode> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    private void saveDadaRecords(Map<String, JsonNode> drMap) {
        drMap.forEach((drinkName, node) ->
                dataRecordsService.saveDataRecord(
                        DataRecord.builder().node(node).name(drinkName).build()));

    }
}
