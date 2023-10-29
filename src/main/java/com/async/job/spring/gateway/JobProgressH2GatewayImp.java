package com.async.job.spring.gateway;

import com.async.job.infra.domain.Job;
import com.async.job.infra.enums.JobStatus;
import com.async.job.infra.gateway.JobProgressGateway;
import com.async.job.spring.entity.JobEntity;
import com.async.job.spring.entity.JobMapper;
import com.async.job.spring.repository.JobProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobProgressH2GatewayImp implements JobProgressGateway {

    private final JobProgressRepository jobProgressRepository;

    @Override
    public Job saveJob(String jobId) {
        final JobEntity jobEntity = jobProgressRepository.save(new JobEntity(jobId, JobStatus.IN_PROCESS));
        return JobMapper.fromEntity(jobEntity);
    }

    @Override
    public Job updateJob(JobStatus jobStatus, String id) {
        final JobEntity job = jobProgressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job não encontrado!"));
        job.attrStatus(jobStatus);
        jobProgressRepository.save(job);
        return JobMapper.fromEntity(job);
    }

}
