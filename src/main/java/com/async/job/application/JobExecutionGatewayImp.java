package com.async.job.application;

import com.async.job.infra.gateway.JobExecutionGateway;
import com.async.job.infra.usecase.RunJob;
import com.async.job.infra.usecase.StopJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JobExecutionGatewayImp implements JobExecutionGateway {

    private final RunJob runJob;
    private final StopJob stopJob;

    @Override
    public void runJob(final String rotina, Runnable run) {
        runJob.execute(rotina, run);
    }

    @Override
    public void stopJob(final String jobId) {
        stopJob.execute(jobId);
    }

}
