package com.zeecoder.kitchen.service;

import com.zeecoder.kitchen.dto.DataRecord;
import com.zeecoder.kitchen.repository.DataRecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataRecordsService {

    private final DataRecordsRepository repository;

    public void saveDataRecord(DataRecord dataRecord) {
        repository.save(dataRecord);
    }
}
