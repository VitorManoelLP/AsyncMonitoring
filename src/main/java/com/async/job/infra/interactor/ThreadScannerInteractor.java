package com.async.job.infra.interactor;

import com.async.job.infra.gateway.ThreadScannerGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ThreadScannerInteractor {

    private final ThreadScannerGateway threadScannerGateway;

    public List<Thread> findAllThreads() {
        return threadScannerGateway.findAllThreads();
    }

    public List<Thread> findAllThreadsByName(String name) {
        return threadScannerGateway.findAllThreadsByName(name);
    }

}
