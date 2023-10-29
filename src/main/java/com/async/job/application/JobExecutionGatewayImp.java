package com.async.job.application;

import com.async.job.infra.enums.JobStatus;
import com.async.job.infra.exception.JobException;
import com.async.job.infra.gateway.JobExecutionGateway;
import com.async.job.infra.interactor.JobProgressInteractor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ThreadUtils;

import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class JobExecutionGatewayImp implements JobExecutionGateway {

    private final JobProgressInteractor jobProgressInteractor;

    @Override
    public void runJob(final String rotina, Runnable run) {

        String jobId = String.format("JOB-%s", rotina.toUpperCase());

        Executors.newCachedThreadPool(runnable -> {
            jobProgressInteractor.saveJob(jobId);
            return new Thread(runnable, jobId);
        }).execute(() -> runThread(run, jobId));
    }

    private void runThread(Runnable run, String jobId) {

        boolean occurrenceError = false;

        try {

            run.run();

        } catch (Exception exception) {

            occurrenceError = true;

            throw new JobException(exception.getMessage(), exception);

        } finally {

            if (occurrenceError) {
                jobProgressInteractor.updateJob(JobStatus.FAILED, jobId);
            } else {
                jobProgressInteractor.updateJob(JobStatus.COMPLETED, jobId);
            }

        }
    }

    @Override
    public void stopJob(final String jobId) {
        ThreadUtils.findThreadsByName(jobId).stream()
                .findFirst()
                .ifPresent(jobThread -> {
                    jobThread.interrupt();
                    jobProgressInteractor.updateJob(JobStatus.STOPPED, jobId);
                });
    }

}
