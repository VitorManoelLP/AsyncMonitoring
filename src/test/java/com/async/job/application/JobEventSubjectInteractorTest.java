package com.async.job.application;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.gateway.JobEventObserverGateway;
import com.async.job.infra.interactor.JobEventSubjectInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class JobEventSubjectInteractorTest {

    private JobEventSubjectInteractor jobEventSubjectInteractor;

    private ObserverFake observerFake;

    @BeforeEach
    public void setup() {
        observerFake = spy(new ObserverFake());
        jobEventSubjectInteractor = new JobEventSubjectInteractor(List.of(observerFake));
    }

    @Test
    public void shouldCallNextMethodWhenEmitIsCalled() {
        final JobEvent jobEvent = JobEvent.ofNew("id");
        jobEventSubjectInteractor.emit(jobEvent);
        verify(observerFake, times(1)).next(jobEvent);
    }

    private static class ObserverFake implements JobEventObserverGateway {
        @Override
        public void next(JobEvent job) {
        }
    }

}
