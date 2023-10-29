package com.async.job.infra.gateway;

import com.async.job.infra.exception.JobException;

public interface JobExecutionGateway {

    void runJob(String executionName, Runnable run) throws JobException;

    void stopJob(String jobId);

}
