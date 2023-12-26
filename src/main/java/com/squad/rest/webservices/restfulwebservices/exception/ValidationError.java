package com.squad.rest.webservices.restfulwebservices.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    private List<ErrorDetails> violations = new ArrayList<>();

    public List<ErrorDetails> getViolations() {
        return violations;
    }

    public void addViolation(ErrorDetails violation) {
        this.violations.add(violation);
    }
}
