package com.async.job.spring.gateway;

import com.async.job.infra.gateway.ThreadScannerGateway;
import org.apache.commons.lang3.ThreadUtils;

import java.util.List;

public class ThreadScannerApacheGateway implements ThreadScannerGateway {

    @Override
    public List<Thread> findAllThreads() {
        return ThreadUtils.getAllThreads().stream().toList();
    }

    @Override
    public List<Thread> findAllThreadsByName(String name) {
        return ThreadUtils.findThreadsByName(name).stream().toList();
    }

}
