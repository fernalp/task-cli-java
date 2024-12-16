package com.fernando_almanza.task_cli.models;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    public static TaskStatus fromString(String status) {
        try {
            return TaskStatus.valueOf(status.toUpperCase().replace(" ","_"));
        } catch (IllegalArgumentException e) {
            return TODO;
        }
    }
}
