package org.example.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.dto.request.TaskRequestDto;
import org.example.todo.dto.response.TaskResponseDto;
import org.example.todo.entity.Category;
import org.example.todo.entity.Task;
import org.example.todo.repository.CategoryRepository;
import org.example.todo.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;


    public String createTask(TaskRequestDto taskRequestDto){
        try {
            Task task = modelMapper.map(taskRequestDto, Task.class);
            log.info("map: {}", task);
            Category category = categoryRepository.findById(taskRequestDto.getCategoryId())
                    .orElseThrow(()-> new RuntimeException("Category not found by ID - "+taskRequestDto.getCategoryId()));
            task.setCategory(category);
            Task save = taskRepository.save(task);
            log.info("save: {}", save);
            return "Task created successfully";
        } catch (DataIntegrityViolationException e) {
            return "It is already exist";
        }
    }


    public String updateTask(Long id, TaskRequestDto taskRequestDto){
        taskRepository.findById(id).orElseThrow();
        Task map = modelMapper.map(taskRequestDto, Task.class);
        map.setCategory(categoryRepository.findById(taskRequestDto.getCategoryId()).orElseThrow());
        map.setId(id);
        taskRepository.save(map);
        log.info("save :{}", map);

        return "Update is successfully";
    }


    public String deleteTask(Long id){
        Optional<Task> byId = taskRepository.findById(id);
        if(byId.isPresent()){
            taskRepository.deleteById(id);
            return "Deleted successfully";
        }
        return id+" not found";
    }

    public List<TaskResponseDto> getAllTasks(){
        List<Task> all = taskRepository.findAll();
        return listMapping(all, TaskResponseDto.class);
    }

    public <D, S> List<D> listMapping(List<S> model,Class<D> dto){
      return   model.stream().map(m-> modelMapper.map(m,dto)).toList();

    }

    public TaskResponseDto getTaskById(Long id){
        Optional<Task> byId = taskRepository.findById(id);
        return modelMapper.map(byId, TaskResponseDto.class);
    }

    public List<TaskResponseDto> getTaskByCategory(String name){
        Category categoryByName = categoryRepository.findCategoryByName(name);
        List<Task> task = categoryByName.getTasks();
        return listMapping(task,TaskResponseDto.class);
    }
}
