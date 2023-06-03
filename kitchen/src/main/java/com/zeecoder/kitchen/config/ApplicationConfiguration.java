package com.zeecoder.kitchen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = {"com.zeecoder.kafka"})
@EnableScheduling
public class ApplicationConfiguration {
}
