package com.fernando_almanza.task_cli.usecases;

import com.fernando_almanza.task_cli.models.Task;
import com.fernando_almanza.task_cli.repositories.TaskRepository;

import java.util.List;

public class AddTaskUseCase {

    private final TaskRepository repository;

    public AddTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void execute(String description) {
        Integer id = repository.generateNextId();
        Task task = new Task(id, description);
        repository.add(task);
        System.out.println("Tarea agregada: " + task);
    }

}
