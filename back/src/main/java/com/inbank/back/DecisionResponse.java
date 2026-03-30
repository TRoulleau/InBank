package com.inbank.back;

public record DecisionResponse(
    boolean approved, 
    int amount, 
    int period,
    String message
) {}