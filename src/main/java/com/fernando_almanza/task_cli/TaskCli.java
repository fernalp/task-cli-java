package com.fernando_almanza.task_cli;


import com.fernando_almanza.task_cli.models.TaskStatus;
import com.fernando_almanza.task_cli.repositories.TaskRepository;
import com.fernando_almanza.task_cli.usecases.AddTaskUseCase;
import com.fernando_almanza.task_cli.usecases.DeleteTaskUseCase;
import com.fernando_almanza.task_cli.usecases.RetrieveTaskUseCase;
import com.fernando_almanza.task_cli.usecases.UpdateTaskUseCase;

import java.util.Arrays;

public class TaskCli {

    private static final TaskRepository taskRepository = new TaskRepository();

    private final AddTaskUseCase addTaskUseCase;
    private final RetrieveTaskUseCase retrieveTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskCli() {
        this.addTaskUseCase = new AddTaskUseCase(taskRepository);
        this.retrieveTaskUseCase = new RetrieveTaskUseCase(taskRepository);
        this.updateTaskUseCase = new UpdateTaskUseCase(taskRepository);
        this.deleteTaskUseCase = new DeleteTaskUseCase(taskRepository);
    }

    public static void main(String[] args) {

        TaskCli taskCli = new TaskCli();
        if (args.length == 0){
            taskCli.showHelp();
        }

        String command = args[0];
        switch (command) {
            case "add" -> {
                if (args.length < 2) {
                    System.out.println("Error: Debes proporcionar una descripción para la tarea.");
                } else {
                    taskCli.addTaskUseCase.execute(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                }
            }
            case "list" -> {
                if (args.length < 2){
                    taskCli.retrieveTaskUseCase.getAll();
                }else {
                    String subcommand = args[1];
                    switch (subcommand) {
                        case "todo" -> taskCli.retrieveTaskUseCase.getTasksByStatus(TaskStatus.TODO);
                        case "in-progress" -> taskCli.retrieveTaskUseCase.getTasksByStatus(TaskStatus.IN_PROGRESS);
                        case "done" -> taskCli.retrieveTaskUseCase.getTasksByStatus(TaskStatus.DONE);
                        default -> System.out.println("Error: Comando desconocido. Use 'list todo', 'list in-progress', o 'list done'.");
                    }
                }
            }
            case "delete" -> {
                if (args.length < 2) {
                    System.out.println("Error: Debes proporcionar el ID de la tarea para eliminar.");
                } else {
                    try {
                        String id = args[1];
                        taskCli.deleteTaskUseCase.execute(Integer.parseInt(id));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: El ID de la tarea no es válido.");
                    }
                }
            }
            case "update" -> {
                if (args.length < 3) {
                    System.out.println("Error: Debes proporcionar el ID de la tarea y la nueva descripción.");
                } else {
                    try {
                        String id = args[1];
                        String description = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                        taskCli.updateTaskUseCase.updateDescription(Integer.parseInt(id), description);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: El ID de la tarea no es válido.");
                    }
                }
            }
            case "mark-in-progress" -> {
                if (args.length < 2) {
                    System.out.println("Error: Debes proporcionar el ID de la tarea para marcarla como en curso.");
                } else {
                    try {
                        String id = args[1];
                        taskCli.updateTaskUseCase.updateStatus(Integer.parseInt(id), TaskStatus.IN_PROGRESS);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: El ID de la tarea no es válido.");
                    }
                }
            }
            case "mark-done" -> {
                if (args.length < 2) {
                    System.out.println("Error: Debes proporcionar el ID de la tarea para marcarla como en terminada.");
                } else {
                    try {
                        String id = args[1];
                        taskCli.updateTaskUseCase.updateStatus(Integer.parseInt(id), TaskStatus.DONE);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: El ID de la tarea no es válido.");
                    }
                }
            }
            default -> taskCli.showHelp();
        }

    }
    private void showHelp() {
        System.out.println("Uso:");
        System.out.println("  task-cli add <descripcion> - Agrega una nueva tarea");
        System.out.println("  task-cli list - Lista todas las tareas");
        System.out.println("  task-cli remove <id> - Elimina la tarea con el ID especificado");
        System.out.println("  task-cli update <id> <descripcion> - Actualiza la tarea con el ID especificado");
    }
}