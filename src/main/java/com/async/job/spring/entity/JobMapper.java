package com.async.job.spring.entity;

import com.async.job.infra.domain.Job;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JobMapper {

    public Job fromEntity(JobEntity job) {
        return new Job(job.getJobId(), job.getJobStatus(), job.getMessage());
    }

}
