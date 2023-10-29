package com.async.job.infra.exception;

import jakarta.persistence.RollbackException;

public class JobException extends RollbackException {

    public JobException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
