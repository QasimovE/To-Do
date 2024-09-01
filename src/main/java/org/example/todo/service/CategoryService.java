package org.example.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.todo.dto.request.CategoryRequestDto;
import org.example.todo.dto.response.CategoryResponseDto;
import org.example.todo.entity.Category;
import org.example.todo.entity.Task;
import org.example.todo.repository.CategoryRepository;
import org.example.todo.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    public String  createCategory(CategoryRequestDto categoryRequestDto){
        Category category = modelMapper.map(categoryRequestDto, Category.class);
        Category categoryByName = categoryRepository.findCategoryByName(category.getName());
        if(categoryByName==null){
            categoryRepository.save(category);
            return "Category created successfully";
        }
        return "It is already exist";
    }

    public List<CategoryResponseDto> getAllCategory(){
        List<Category> allCategory = categoryRepository.findAll();
        return listMapping(allCategory, CategoryResponseDto.class);
    }

    public <D,S> List<D> listMapping(List<S> model, Class<D> dto){
        return model.stream().map(m->modelMapper.map(m,dto)).toList();
    }

    public String deleteCategory(Long id){
        Category byId = categoryRepository.findById(id).orElseThrow();
        if(byId!=null){
            List<Task> taskByCategory = taskRepository.findTaskByCategory(byId);
            for (Task t:taskByCategory){
                t.setCategory(null);
                taskRepository.save(t);
            }
            categoryRepository.deleteById(id);
            return "Deleted successfully";
        }
        return "It doesn't exist";
    }
}
