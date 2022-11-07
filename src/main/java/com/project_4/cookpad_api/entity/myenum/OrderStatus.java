package com.project_4.cookpad_api.entity.myenum;

import java.util.Random;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    DONE,
    PROCESSING, PROCESSED, ON_DELIVERY, DELIVERED, REJECTED;
    private static final Random PRNG = new Random();

    public static OrderStatus randomDirection()  {
        OrderStatus[] statuses = values();
        return statuses[PRNG.nextInt(statuses.length)];
    }
}
