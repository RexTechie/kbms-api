package space.rexhub.kbms.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
//        log.info("执行定时任务, 当前时间：" + dateFormat.format(new Date()));
    }
}