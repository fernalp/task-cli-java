package com.fernando_almanza.task_cli.usecases;

import com.fernando_almanza.task_cli.models.Task;
import com.fernando_almanza.task_cli.models.TaskStatus;
import com.fernando_almanza.task_cli.repositories.TaskRepository;

import java.util.List;

public class RetrieveTaskUseCase {

    private final TaskRepository repository;

    public RetrieveTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void getAll() {
        List<Task> tasks = repository.getTasks();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void getTasksByStatus(TaskStatus taskStatus) {
        List<Task> tasks = repository.getTasks(taskStatus);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}
