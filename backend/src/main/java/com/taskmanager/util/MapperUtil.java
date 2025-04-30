package com.taskmanager.util;

import com.taskmanager.model.Task;
import com.taskmanager.dto.TaskDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {
    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setDescription(task.getDescription());
        taskDto.setCompleted(task.isCompleted());
        return taskDto;
    }

    public Task toModel(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        return task;
    }

    public List<TaskDto> toDtoList(List<Task> tasks) {
        if (tasks == null) {
            return null;
        }
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            taskDtos.add(toDto(task));
        }
        return taskDtos;
    }

    public List<Task> toModelList(List<TaskDto> taskDtos) {
        if (taskDtos == null) {
            return null;
        }
        List<Task> tasks = new ArrayList<>();
        for (TaskDto taskDto : taskDtos) {
            tasks.add(toModel(taskDto));
        }
        return tasks;
    }
}
