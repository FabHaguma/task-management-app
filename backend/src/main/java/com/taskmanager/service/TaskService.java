package com.taskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.dto.TaskDto;
import com.taskmanager.util.MapperUtil;
import com.taskmanager.model.Task;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private MapperUtil mapperUtil;

    public List<TaskDto> getAllTaskDtos() {
        List<Task> tasks = taskRepository.findAll();
        return mapperUtil.toDtoList(tasks);
    }

    public Task createTask(Task task) {
        System.out.println("TaskService - single task creation");
        return taskRepository.save(task);
    }

    public List<Task> createTasks(List<Task> tasks) {
        System.out.println("TaskService - multiple task creation");
        return taskRepository.saveAll(tasks); // Save all tasks in one go
    }

    public Task updateTaskCompletionStatus(Long taskId, boolean completed) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setCompleted(completed);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task updateTask(Long taskId, Task task) {
        if (taskRepository.existsById(taskId)) {
            task.setId(taskId);
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }
}
