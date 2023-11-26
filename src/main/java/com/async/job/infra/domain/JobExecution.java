package com.async.job.infra.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
@Getter
@ToString(of = "jobId")
@EqualsAndHashCode(of = "jobId")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobExecution {

    String errorMessage = "";
    final String jobId;
    final String jobIdSequence;

    public void failure(final String failedMessage) {
        errorMessage = failedMessage;
    }

    public JobExecution(final String rotina, final List<Thread> threads) {
        String jobId = String.format("JOB-%s", rotina.toUpperCase());
        log.info("Next Job to save: " + jobId);
        final long sizeThreads = getSequenceOfThreads(threads, jobId);
        final String jobIdSequence = jobId.concat("-" + sizeThreads);
        log.info("Sequence identified: " + jobIdSequence);
        this.jobId = jobId;
        this.jobIdSequence = jobIdSequence;
    }

    private long getSequenceOfThreads(Collection<Thread> allThreads, String jobId) {
        return allThreads.stream()
                .filter(thread -> thread.getName().startsWith(jobId))
                .count();
    }

}
