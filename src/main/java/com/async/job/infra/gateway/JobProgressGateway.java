package com.async.job.infra.gateway;

import com.async.job.infra.domain.Job;
import com.async.job.infra.enums.JobStatus;

public interface JobProgressGateway {

    Job saveJob(String jobId);

    Job updateJob(JobStatus jobStatus, String id);

}
