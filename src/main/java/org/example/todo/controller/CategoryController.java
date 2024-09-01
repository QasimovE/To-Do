package org.example.todo.controller;

import lombok.RequiredArgsConstructor;
import org.example.todo.dto.request.CategoryRequestDto;
import org.example.todo.dto.response.CategoryResponseDto;
import org.example.todo.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("createCategory")
    public String  createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
       return categoryService.createCategory(categoryRequestDto);
    }

    @GetMapping("getAllCategory")
    public List<CategoryResponseDto> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @DeleteMapping("deleteCategory")
    public String deleteCategory(@RequestParam Long id){
      return   categoryService.deleteCategory(id);
    }

}
