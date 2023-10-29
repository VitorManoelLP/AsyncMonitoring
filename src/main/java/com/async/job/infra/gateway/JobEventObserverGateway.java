package com.async.job.infra.gateway;

import com.async.job.infra.domain.Job;
import com.async.job.infra.domain.JobEvent;

public interface JobEventObserverGateway {

    void next(JobEvent job);

}
