package com.async.job.spring.resource;

import com.async.job.infra.interactor.JobExecutionInteractor;
import com.async.job.infra.util.JobMockUtil;
import com.async.job.spring.entity.MockProcessingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-runner")
@RequiredArgsConstructor
public class MockJobRunnerResource {
    
    private final JobExecutionInteractor jobExecutionInteractor;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> runJob(@RequestBody MockProcessingEntity mockProcessingEntity) {

        final String jobIdentifier = mockProcessingEntity.getJobIdentifier();

        jobExecutionInteractor.runJob(jobIdentifier, () -> JobMockUtil.runMockAsyncJob(jobIdentifier));

        return ResponseEntity.ok("STARTED " + jobIdentifier);
    }

}
