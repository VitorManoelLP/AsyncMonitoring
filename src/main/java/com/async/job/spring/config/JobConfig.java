package com.async.job.spring.config;

import com.async.job.application.JobExecutionGatewayImp;
import com.async.job.application.observers.NotificationTemplateJobObserver;
import com.async.job.application.observers.PersistJobObserver;
import com.async.job.infra.gateway.JobEventObserverGateway;
import com.async.job.infra.interactor.JobEventSubjectInteractor;
import com.async.job.spring.gateway.JobProgressH2GatewayImp;
import com.async.job.infra.gateway.JobExecutionGateway;
import com.async.job.infra.gateway.JobProgressGateway;
import com.async.job.infra.interactor.JobExecutionInteractor;
import com.async.job.infra.interactor.JobProgressInteractor;
import com.async.job.spring.repository.JobProgressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JobConfig {

    @Bean
    public JobEventObserverGateway persistObserver(JobProgressInteractor jobProgressInteractor) {
        return new PersistJobObserver(jobProgressInteractor);
    }

    @Bean
    public JobEventObserverGateway notificationTemplateJobObserver() {
        return new NotificationTemplateJobObserver();
    }

    @Bean
    public JobExecutionGateway jobExecutionGateway(JobEventSubjectInteractor eventSubjectInteractor) {
        return new JobExecutionGatewayImp(eventSubjectInteractor);
    }

    @Bean
    public JobEventSubjectInteractor jobEventSubjectInteractor(List<JobEventObserverGateway> observers) {
        return new JobEventSubjectInteractor(observers);
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
