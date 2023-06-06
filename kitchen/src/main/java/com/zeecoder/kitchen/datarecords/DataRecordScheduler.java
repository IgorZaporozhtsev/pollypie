package com.zeecoder.kitchen.datarecords;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataRecordScheduler {

    private final DataRecordProcessor dataRecordProcessor;

    //@Scheduled(cron = "@daily")
    @Scheduled(cron = "0 * * * * *")
    @SchedulerLock(name = "drScheduledTask")
    public void process() {
        log.info("Data Record processing in progress ...");
        dataRecordProcessor.getDataRecords();
    }
}

