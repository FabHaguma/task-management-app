package com.taskmanager.controller;

import com.taskmanager.dto.TaskDto;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTaskDtos();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no tasks found
        }
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<Task>> createTasks(@RequestBody List<Task> tasks) {
        List<Task> createdTasks = taskService.createTasks(tasks); // Update service to handle a list
        return ResponseEntity.ok(createdTasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task); // Save the task using the service
        return ResponseEntity.ok(createdTask); // Return the created task
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id); // Delete the task using the service
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTaskCompletionStatus(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTaskCompletionStatus(id, task.isCompleted());
        return ResponseEntity.ok(updatedTask);
    }

}
