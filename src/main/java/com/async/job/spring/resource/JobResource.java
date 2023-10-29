package com.async.job.spring.resource;

import com.async.job.infra.domain.Job;
import com.async.job.infra.interactor.JobExecutionInteractor;
import com.async.job.infra.interactor.JobProgressInteractor;
import com.async.job.infra.util.JobMockUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/job")
@AllArgsConstructor
public class JobResource {

    private final JobExecutionInteractor jobExecutionInteractor;
    private final JobProgressInteractor jobProgressInteractor;

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> runJob(@RequestParam("jobMessage") String jobMessage) {
        jobExecutionInteractor.runJob(jobMessage, () -> JobMockUtil.runMockAsyncJob(jobMessage));
        return ResponseEntity.ok("STARTED " + jobMessage);
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> stopJob(@RequestParam("jobId") String jobId) {
        jobExecutionInteractor.stopJob(jobId);
        return ResponseEntity.ok(jobId + " STOPPED");
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        return ResponseEntity.ok(jobProgressInteractor.findAll());
    }

}
