package org.example.todo.controller;

import lombok.RequiredArgsConstructor;
import org.example.todo.dto.request.TaskRequestDto;
import org.example.todo.dto.response.TaskResponseDto;
import org.example.todo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;


    @PostMapping("createTask")
    public String createTask(@RequestBody TaskRequestDto taskRequestDto){
      return taskService.createTask(taskRequestDto);
    }


    @PutMapping("updateTask")
    public String updateTask(@RequestParam Long id, @RequestBody TaskRequestDto taskRequestDto){
        return taskService.updateTask(id,taskRequestDto);
    }

    @DeleteMapping("deleteTask")
    public String deleteTask(@RequestParam Long id){
        return taskService.deleteTask(id);
    }

    @GetMapping("getAllTasks")
    public List<TaskResponseDto> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("getTaskById")
    public TaskResponseDto getTaskById(@RequestParam Long id){
        return taskService.getTaskById(id);
    }


    @GetMapping("getTaskByCategory")
    public List<TaskResponseDto> getTaskByCategory(@RequestParam String name){
        return taskService.getTaskByCategory(name);
    }
}
