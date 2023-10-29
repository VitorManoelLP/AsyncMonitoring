package com.async.job.spring.entity;

import com.async.job.infra.enums.JobStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "job_process")
@EqualsAndHashCode(of = "jobId")
@ToString(of = "jobId")
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @Column(name = "JOB_ID")
    private String jobId;

    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_STATUS")
    private JobStatus jobStatus;

    private String message;

    public void attrStatus(JobStatus jobStatus, String message) {
        this.jobStatus =  jobStatus;
        this.message = message;
    }
}
