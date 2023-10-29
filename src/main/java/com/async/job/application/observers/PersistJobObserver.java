package com.async.job.application.observers;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.enums.JobStatus;
import com.async.job.infra.gateway.JobEventObserverGateway;
import com.async.job.infra.interactor.JobProgressInteractor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersistJobObserver implements JobEventObserverGateway {

    private final JobProgressInteractor jobProgressInteractor;

    @Override
    public void next(JobEvent job) {

        if (job.isNewJob()) {
            jobProgressInteractor.saveJob(job.getJobId());
            return;
        }

        if (job.isInterrupted()) {
            jobProgressInteractor.updateJob(JobStatus.STOPPED, job.getJobId(), null);
            return;
        }

        if (job.isFailed()) {
            jobProgressInteractor.updateJob(JobStatus.FAILED, job.getMessage(), job.getJobId());
            return;
        }

        jobProgressInteractor.updateJob(JobStatus.COMPLETED, job.getJobId(), null);
    }

}
