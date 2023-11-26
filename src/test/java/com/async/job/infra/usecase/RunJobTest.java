package com.async.job.infra.usecase;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.interactor.JobEventSubjectInteractor;
import com.async.job.infra.interactor.ThreadScannerInteractor;
import com.async.job.spring.gateway.ThreadScannerApacheGateway;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.logging.log4j.ThreadContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RunJobTest {

    private RunJob runJob;

    private JobEventSubjectInteractor jobEventSubjectInteractor;

    @Captor
    private ArgumentCaptor<JobEvent> jobEventCaptor;

    @BeforeEach
    public void setup() {
        final ThreadScannerApacheGateway apacheGateway = mock(ThreadScannerApacheGateway.class);
        when(apacheGateway.findAllThreads()).thenReturn(List.of());
        ThreadScannerInteractor threadScannerInteractor = new ThreadScannerInteractor(apacheGateway);
        jobEventSubjectInteractor = spy(new JobEventSubjectInteractor(List.of()));
        runJob = new RunJob(jobEventSubjectInteractor, threadScannerInteractor);
    }

    @Test
    public void shouldCreateThreadWithNameRotina() {
        runJob.execute("ROTINA", () -> {
        });
        final Set<Thread> threads = Thread.getAllStackTraces().keySet();
        Assertions.assertThat(threads)
                .filteredOn(thread -> thread.getName().equals("JOB-ROTINA-0"))
                .isNotEmpty();
    }

    @Test
    public void shouldCreateJobEventAndEmitSubjectWhenThreadWasCreated() {
        runJob.execute("ROTINA", () -> {
        });
        verify(jobEventSubjectInteractor, times(2)).emit(jobEventCaptor.capture());
        final List<JobEvent> jobEvent = jobEventCaptor.getAllValues();
        Assertions.assertThat(jobEvent)
                .anySatisfy(job -> {
                    Assertions.assertThat(job.getJobId()).isEqualTo("JOB-ROTINA-0");
                    Assertions.assertThat(job.getMessage()).isNull();
                    Assertions.assertThat(job.isNewJob()).isTrue();
                    Assertions.assertThat(job.isFailed()).isFalse();
                    Assertions.assertThat(job.isInterrupted()).isFalse();
                });
    }

    @Test
    public void shouldCreateJobEventWithMessageWhenRunJobReturnThrow() {
        runJob.execute("ROTINA", () -> {
            throw new IllegalStateException("Erro");
        });
        verify(jobEventSubjectInteractor, times(2)).emit(jobEventCaptor.capture());
        final List<JobEvent> jobEvent = jobEventCaptor.getAllValues();
        Assertions.assertThat(jobEvent)
                .anySatisfy(job -> {
                    Assertions.assertThat(job.getJobId()).isEqualTo("JOB-ROTINA");
                    Assertions.assertThat(job.getMessage()).isEqualTo("Erro");
                    Assertions.assertThat(job.isNewJob()).isFalse();
                    Assertions.assertThat(job.isFailed()).isTrue();
                    Assertions.assertThat(job.isInterrupted()).isFalse();
                });
    }

}
