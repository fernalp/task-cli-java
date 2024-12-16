package com.fernando_almanza.task_cli.usecases;

import com.fernando_almanza.task_cli.models.Task;
import com.fernando_almanza.task_cli.models.TaskStatus;
import com.fernando_almanza.task_cli.repositories.TaskRepository;

import java.util.Optional;

public class UpdateTaskUseCase {

    private final TaskRepository repository;

    public UpdateTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void updateDescription(Integer id, String newDescription) {

       Optional<Task> task = repository.getTaskById(id);

        if (task.isPresent()) {
            Task updatedTask = task.get();
            updatedTask.setDescription(newDescription);
            repository.update(id, updatedTask);
            System.out.println("Tarea actualizada con ID: " + id);
        } else {
            System.out.println("Tarea con ID " + id + " no encontrada.");
        }
    }

    public void updateStatus(Integer id, TaskStatus newStatus) {
        Optional<Task> task = repository.getTaskById(id);

        if (task.isPresent()) {
            Task updatedTask = task.get();
            updatedTask.setStatus(newStatus);
            repository.update(id, updatedTask);
            System.out.println("Tarea con ID: " + id + " marcada como " + (TaskStatus.IN_PROGRESS.equals(newStatus) ? "en progreso" : "finalizada"));
        } else {
            System.out.println("Tarea con ID " + id + " no encontrada.");
        }
    }

}
