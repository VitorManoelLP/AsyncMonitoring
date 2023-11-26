package com.async.job.infra.usecase;

import com.async.job.infra.domain.JobEvent;
import com.async.job.infra.interactor.JobEventSubjectInteractor;
import com.async.job.infra.interactor.ThreadScannerInteractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class StopJob {

    private final JobEventSubjectInteractor eventSubjectInteractor;
    private final ThreadScannerInteractor threadScannerInteractor;

    public void execute(final String jobId) {
        threadScannerInteractor.findAllThreadsByName(jobId).stream()
                .findFirst()
                .ifPresent(jobThread -> stop(jobId, jobThread));
    }

    private void stop(String jobId, Thread jobThread) {
        log.info("Interrupting thread: " + jobThread);
        jobThread.interrupt();
        eventSubjectInteractor.emit(JobEvent.ofInterrupted(jobId));
    }

}
