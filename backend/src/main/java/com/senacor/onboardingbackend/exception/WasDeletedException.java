package com.senacor.onboardingbackend.exception;

import java.io.Serializable;

public class WasDeletedException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public WasDeletedException(String msg) {
        super(msg);
    }
}
