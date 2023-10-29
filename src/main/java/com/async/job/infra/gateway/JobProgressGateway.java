package com.async.job.infra.gateway;

import com.async.job.infra.domain.Job;
import com.async.job.infra.enums.JobStatus;

import java.util.List;

public interface JobProgressGateway {

    Job saveJob(String jobId);

    Job updateJob(JobStatus jobStatus, String id, String message);

    List<Job> findAll();

}
