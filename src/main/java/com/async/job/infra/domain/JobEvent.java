package com.async.job.infra.domain;

import lombok.*;

import java.util.Objects;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public final class JobEvent {

    private final String message;
    private final String jobId;

    @Builder.Default
    private final boolean interrupted = Boolean.FALSE;

    @Builder.Default
    private final boolean newJob = Boolean.FALSE;

    public boolean isFailed() {
        return Objects.nonNull(message);
    }

    public static JobEvent ofInterrupted(String jobId) {
        return JobEvent.builder()
                .jobId(jobId)
                .interrupted(true)
                .build();
    }

    public static JobEvent ofNew(String jobId) {
        return JobEvent.builder()
                .jobId(jobId)
                .newJob(true)
                .build();
    }

    public static JobEvent ofPossibilityFailure(final JobExecution jobExecution) {
        return JobEvent.builder()
                .jobId(jobExecution.getJobId())
                .message(jobExecution.getErrorMessage())
                .build();
    }


}
