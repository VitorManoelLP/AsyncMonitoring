package com.async.job.infra.domain;

import com.async.job.infra.enums.JobStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Job {

    String jobId;
    JobStatus jobStatus;
    String message;

}
