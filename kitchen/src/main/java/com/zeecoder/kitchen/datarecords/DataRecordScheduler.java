package com.zeecoder.kitchen.datarecords;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataRecordScheduler {

    DataRecordProcessor dataRecordProcessor;

//    @Scheduled    //TODO add period
//    private void process(){
//        dataRecordProcessor.getDataRecords();
//    }
}

