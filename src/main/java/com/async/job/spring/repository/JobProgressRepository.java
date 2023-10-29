package com.async.job.spring.repository;

import com.async.job.spring.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobProgressRepository extends JpaRepository<JobEntity, String> {
}
