package com.async.job.spring.entity;

import com.async.job.infra.enums.JobRunners;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "mock_process")
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class MockProcessingEntity implements JobEntityRunner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Override
    public String getJobIdentifier() {
        return JobRunners.MOCK_RUNNER.getIdentifier();
    }

}
