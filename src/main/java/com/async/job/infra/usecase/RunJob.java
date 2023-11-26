package com.async.job.infra.usecase;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.domain.JobExecution;
import com.async.job.infra.interactor.JobEventSubjectInteractor;
import com.async.job.infra.interactor.ThreadScannerInteractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
public class RunJob {

    private final JobEventSubjectInteractor eventSubjectInteractor;
    private final ThreadScannerInteractor threadScannerInteractor;

    public void execute(final String rotina, Runnable run) {
        runJob(rotina, run);
    }

    private void runJob(final String rotina, Runnable run) {
        final JobExecution jobExecution = new JobExecution(rotina, threadScannerInteractor.findAllThreads());
        Executors.newCachedThreadPool(runnable -> createThreadWithPrefix(runnable, jobExecution.getJobIdSequence()))
                .execute(() -> runThread(run, jobExecution));
    }

    private void runThread(Runnable run, JobExecution jobExecution) {
        try {
            log.info("Running thread: " + jobExecution);
            run.run();
        } catch (Exception exception) {
            log.error("Thread running was fail", exception);
            jobExecution.failure(exception.getMessage());
        } finally {
            eventSubjectInteractor.emit(JobEvent.ofPossibilityFailure(jobExecution));
        }
    }

    private Thread createThreadWithPrefix(Runnable runnable, String jobIdSequence) {
        eventSubjectInteractor.emit(JobEvent.ofNew(jobIdSequence));
        return new Thread(runnable, jobIdSequence);
    }

}
