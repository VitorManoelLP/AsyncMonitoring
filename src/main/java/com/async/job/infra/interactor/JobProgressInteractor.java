package com.async.job.infra.interactor;

import com.async.job.infra.enums.JobStatus;
import com.async.job.infra.gateway.JobProgressGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobProgressInteractor {

    private final JobProgressGateway jobProgressGateway;

    public void saveJob(String jobId) {
        jobProgressGateway.saveJob(jobId);
    }

    public void updateJob(JobStatus jobStatus, String id) {
        jobProgressGateway.updateJob(jobStatus, id);
    }

}
