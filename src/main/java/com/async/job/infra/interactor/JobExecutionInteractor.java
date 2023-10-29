package com.async.job.infra.interactor;

import com.async.job.infra.exception.JobException;
import com.async.job.infra.gateway.JobExecutionGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobExecutionInteractor {

    private final JobExecutionGateway jobExecutionGateway;

    public void runJob(String executionName, Runnable run) throws JobException {
        jobExecutionGateway.runJob(executionName, run);
    }

    public void stopJob(String jobId) {
        jobExecutionGateway.stopJob(jobId);
    }

}
