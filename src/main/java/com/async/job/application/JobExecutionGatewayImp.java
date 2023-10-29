package com.async.job.application;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.gateway.JobExecutionGateway;
import com.async.job.infra.interactor.JobEventSubjectInteractor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ThreadUtils;

import java.util.Collection;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class JobExecutionGatewayImp implements JobExecutionGateway {

    private final JobEventSubjectInteractor eventSubjectInteractor;

    @Override
    public void runJob(final String rotina, Runnable run) {

        String jobId = String.format("JOB-%s", rotina.toUpperCase());

        final Collection<Thread> allThreads = ThreadUtils.getAllThreads();

        final long sizeThreads = allThreads.stream()
                .filter(it -> it.getName().startsWith(jobId))
                .count();

        final String jobIdSequence = jobId.concat("-" + sizeThreads);

        Executors.newCachedThreadPool(runnable -> {
            eventSubjectInteractor.emit(JobEvent.ofNew(jobIdSequence));
            return new Thread(runnable, jobIdSequence);
        }).execute(() -> runThread(run, jobIdSequence));
    }

    private void runThread(Runnable run, String jobId) {

        String exceptionMessage = null;

        try {
            run.run();
        } catch (Exception exception) {
            exceptionMessage = exception.getMessage();
        } finally {
            eventSubjectInteractor.emit(JobEvent.ofPossibilityFailure(jobId, exceptionMessage));
        }
    }

    @Override
    public void stopJob(final String jobId) {
        ThreadUtils.findThreadsByName(jobId).stream()
                .findFirst()
                .ifPresent(jobThread -> {
                    jobThread.interrupt();
                    eventSubjectInteractor.emit(JobEvent.ofInterrupted(jobId));
                });
    }

}
