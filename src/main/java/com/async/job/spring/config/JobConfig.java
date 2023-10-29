package com.async.job.spring.config;

import com.async.job.application.JobExecutionGatewayImp;
import com.async.job.spring.gateway.JobProgressH2GatewayImp;
import com.async.job.infra.gateway.JobExecutionGateway;
import com.async.job.infra.gateway.JobProgressGateway;
import com.async.job.infra.interactor.JobExecutionInteractor;
import com.async.job.infra.interactor.JobProgressInteractor;
import com.async.job.spring.repository.JobProgressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    @Bean
    public JobExecutionGateway jobExecutionGateway(JobProgressInteractor jobProgressInteractor) {
        return new JobExecutionGatewayImp(jobProgressInteractor);
    }

    @Bean
    public JobExecutionInteractor jobExecutionInteractor(JobExecutionGateway jobExecutionGateway) {
        return new JobExecutionInteractor(jobExecutionGateway);
    }

    @Bean
    public JobProgressGateway jobProgressGateway(JobProgressRepository jobProgressRepository) {
        return new JobProgressH2GatewayImp(jobProgressRepository);
    }

    @Bean
    public JobProgressInteractor jobProgressInteractor(JobProgressGateway jobProgressGateway) {
        return new JobProgressInteractor(jobProgressGateway);
    }

}
