package com.fernando_almanza.task_cli.repositories;

import com.fernando_almanza.task_cli.mappers.TaskMapper;
import com.fernando_almanza.task_cli.models.Task;
import com.fernando_almanza.task_cli.models.TaskStatus;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {

    private static final String FILE_NAME = "tasks.json";

    public Integer generateNextId() {
        int maxId = 0;
        List<Task> tasks = loadTasks();
        if (!tasks.isEmpty()) {
            maxId = tasks.stream().map(Task::getId).max(Integer::compare).orElse(0);
        }
        return maxId + 1;
    }

    private List<Task> loadTasks() {
        Path path = Paths.get(FILE_NAME);
        if (Files.notExists(path)) {
            return new ArrayList<>();
        }

        try {
            String content = new String(Files.readAllBytes(path));
            if (content.trim().isEmpty() || content.equals("[]")) {
                return new ArrayList<>();
            }

            // Parse JSON content
            List<Task> tasks = new ArrayList<>();
            String[] taskJsons = content.replace("[", "").replace("]", "").split("},\\{");

            if (taskJsons.length > 0) {
                taskJsons[0] = taskJsons[0].replace("{", "");
                taskJsons[taskJsons.length - 1] = taskJsons[taskJsons.length - 1].replace("}", "");
            }
            for (String taskJson : taskJsons) {
                tasks.add(TaskMapper.fromJson("{" + taskJson + "}"));
            }
            return tasks;

        } catch (IOException e) {
            System.out.println("Error al cargar las tareas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveTasks(List<Task> tasks) {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            StringBuilder content = new StringBuilder();
            content.append("[");
            for (int i = 0; i < tasks.size(); i++) {
                content.append(TaskMapper.toJson(tasks.get(i)));
                if (i < tasks.size() - 1) {
                    content.append(",");
                }
            }
            content.append("]");
            fileWriter.write(content.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

    public List<Task> getTasks() {
        return loadTasks();
    }
    public List<Task> getTasks(TaskStatus taskstatus) {
        return loadTasks().stream().filter(task -> task.getStatus().equals(taskstatus)).toList();
    }

    public Optional<Task> getTaskById(Integer id) {
        return loadTasks().stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    public void add(Task task) {
        List<Task> tasks = loadTasks();
        tasks.add(task);
        saveTasks(tasks);
    }

    public void delete(Integer id) {
        List<Task> tasks = loadTasks();
        tasks.removeIf(t -> t.getId().equals(id));
        saveTasks(tasks);
    }

    public void update(Integer id, Task updatedTask) {
        List<Task> tasks = loadTasks();
        saveTasks(tasks.stream().map( task -> {
            if (task.getId().equals(id)) {
                return updatedTask;
            }
            return task;
        }).toList());
    }

}
