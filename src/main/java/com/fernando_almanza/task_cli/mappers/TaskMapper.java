package com.fernando_almanza.task_cli.mappers;

import com.fernando_almanza.task_cli.models.Task;
import com.fernando_almanza.task_cli.models.TaskStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskMapper {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toJson(Task task) {
        return "{\"id\":" + task.getId() +
                ",\"description\":\"" + task.getDescription() + "\"" +
                ",\"status\":\"" + task.getStatus().toString().toLowerCase() + "\"" +
                ",\"createdAt\":\"" + task.getCreatedAt().format(FORMATTER) + "\"" +
                ",\"updatedAt\":\"" + task.getUpdatedAt().format(FORMATTER) + "\"" +
                "}";
    }

    public static Task fromJson(String json) {
        int id = Integer.parseInt(json.replaceAll(".*\"id\":(\\d+),.*", "$1"));
        String description = json.replaceAll(".*\"description\":\"([^\"]+)\".*", "$1");
        TaskStatus status = TaskStatus.fromString(json.replaceAll(".*\"status\":\"([^\"]+)\".*", "$1"));
        LocalDateTime createdAt = LocalDateTime.parse(json.replaceAll(".*\"createdAt\":\"([^\"]+)\".*", "$1"), FORMATTER);
        LocalDateTime updatedAt = LocalDateTime.parse(json.replaceAll(".*\"updatedAt\":\"([^\"]+)\".*", "$1"), FORMATTER);
        return new Task(id, description, status, createdAt, updatedAt);
    }

}
