package com.async.job.infra.interactor;

import com.async.job.infra.domain.Job;
import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.gateway.JobEventObserverGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JobEventSubjectInteractor {

    private final List<JobEventObserverGateway> observers;

    public void emit(JobEvent job) {
        observers.forEach(observer -> observer.next(job));
    }

}
