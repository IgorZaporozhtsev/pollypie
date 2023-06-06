package com.zeecoder.kitchen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.zeecoder.kafka"})
public class ApplicationConfiguration {
}
