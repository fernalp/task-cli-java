package com.fernando_almanza.task_cli.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    private final Integer id;
    private String description;
    private TaskStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Task(Integer id, String description) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Task(Integer id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "ID='" + id + '\'' +
                ", Description='" + description + '\'' +
                ", Status=" + status.toString().toLowerCase() +
                ", Created At=" + createdAt.format(FORMATTER) +
                ", Updated At=" + updatedAt.format(FORMATTER) +
                '}';
    }

}
