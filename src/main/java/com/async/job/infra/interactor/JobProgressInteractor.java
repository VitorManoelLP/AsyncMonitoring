package com.async.job.infra.interactor;

import com.async.job.infra.domain.Job;
import com.async.job.infra.enums.JobStatus;
import com.async.job.infra.gateway.JobProgressGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JobProgressInteractor {

    private final JobProgressGateway jobProgressGateway;

    public void saveJob(String jobId) {
        jobProgressGateway.saveJob(jobId);
    }

    public void updateJob(JobStatus jobStatus, String id, String message) {
        jobProgressGateway.updateJob(jobStatus, id, message);
    }

    public List<Job> findAll() {
        return jobProgressGateway.findAll();
    }

}
