package com.quartzconfig;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cancelunpaidordersjob.CancelUnpaidOrdersJob;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail cancelUnpaidOrdersJobDetail() {
        return JobBuilder.newJob(CancelUnpaidOrdersJob.class)
                .withIdentity("cancelUnpaidOrdersJob")
                .storeDurably()  // 任務在未關聯觸發器時保留
                .build();
    }

    @Bean
    public Trigger cancelUnpaidOrdersTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(cancelUnpaidOrdersJobDetail())
                .withIdentity("cancelUnpaidOrdersTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(3)  // 每 3 分鐘執行一次
                        .repeatForever())
                .build();
    }
}
