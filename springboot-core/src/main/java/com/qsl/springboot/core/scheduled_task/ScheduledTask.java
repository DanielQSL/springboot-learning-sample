package com.qsl.springboot.core.scheduled_task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 默认定时任务线程池数量为1
 * 解决方案1：spring.task.scheduling.pool.size
 * 解决方案2：ScheduleConfig
 *
 * @author qianshuailong
 * @date 2021/1/7
 */
@SuppressWarnings("all")
@Slf4j
@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 1000)
    public void test01() throws Exception {
        log.info("Scheduled Task process task01");
        while (true) {
            Thread.sleep(2000);
            log.info("Scheduled Task process task01 something");
        }
    }

    @Scheduled(fixedRate = 1000)
    public void test02() throws Exception {
        log.info("Scheduled Task process task02");
    }

}
