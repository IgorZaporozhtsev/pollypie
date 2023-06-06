package com.zeecoder.kitchen.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
//We would also like to disable scheduling during running tests. For this, we need to add a condition
@ConditionalOnProperty(name = "app.scheduler.enabled", matchIfMissing = true)
public class SchedulerConfiguration {

}
