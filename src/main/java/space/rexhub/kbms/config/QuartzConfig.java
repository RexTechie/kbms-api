package space.rexhub.kbms.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.rexhub.kbms.schedule.SampleJob;

@Configuration
public class QuartzConfig {

    /**
     * 定义 JobDetail
     * @return 任务详情
     */
    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity("sampleJob")
                .storeDurably()
                .build();
    }

    /**
     * 定义 Trigger
     * @return 触发器
     */
    @Bean
    public Trigger sampleJobTrigger() {
        // 每 5 秒执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail())
                .withIdentity("sampleTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
