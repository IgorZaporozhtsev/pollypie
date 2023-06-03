package com.zeecoder.kitchen.repository;

import com.zeecoder.kitchen.dto.DataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DataRecordsRepository extends JpaRepository<DataRecord, UUID> {
}
