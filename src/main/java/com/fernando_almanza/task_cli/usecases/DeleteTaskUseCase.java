package com.fernando_almanza.task_cli.usecases;

import com.fernando_almanza.task_cli.models.Task;
import com.fernando_almanza.task_cli.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeleteTaskUseCase {

    private final TaskRepository repository;

    public DeleteTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void execute(Integer id) {

        Optional<Task> task = repository.getTaskById(id);

        if (task.isPresent()) {
            repository.delete(id);
            System.out.println("Tarea eliminada con ID: " + id);
        } else {
            System.out.println("Tarea con ID " + id + " no encontrada.");
        }
    }

}
