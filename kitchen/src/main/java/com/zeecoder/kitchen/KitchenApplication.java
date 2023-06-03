package com.zeecoder.kitchen;

import com.zeecoder.kitchen.datarecords.DataRecordProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor

public class KitchenApplication {


    private final DataRecordProcessor dataRecordProcessor;


    public static void main(String[] args) {
        SpringApplication.run(KitchenApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> dataRecordProcessor.getDataRecords();
    }

}
