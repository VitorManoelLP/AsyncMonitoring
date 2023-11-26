package com.async.job.infra.usecase;

import com.async.job.infra.interactor.JobEventSubjectInteractor;
import com.async.job.infra.interactor.ThreadScannerInteractor;
import com.async.job.spring.gateway.ThreadScannerApacheGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StopJobTest {

    private StopJob stopJob;
    private JobEventSubjectInteractor eventSubjectInteractor;

    @Mock
    private ThreadScannerInteractor threadScannerInteractor;

    @BeforeEach
    public void setup() {
        eventSubjectInteractor = spy(new JobEventSubjectInteractor(List.of()));
        stopJob = new StopJob(eventSubjectInteractor, threadScannerInteractor);
    }

    @Test
    public void shouldStopThreadWhenItDoesExists() {
        final String jobId = "TESTE-THREAD";
        final Thread thread = new Thread(jobId);
        when(threadScannerInteractor.findAllThreadsByName(jobId)).thenReturn(List.of(thread));
        stopJob.execute(jobId);
        Assertions.assertThat(thread.isInterrupted()).isTrue();
    }

}
