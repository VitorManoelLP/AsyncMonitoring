package com.async.job.infra.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class JobMockUtil {

    public void runMockAsyncJob(final String jobMessage) {

        for (int i = 0; i < 100; i++) {
            System.out.println(i + " - " + jobMessage);
            sleep();
        }

    }

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
    }

}
