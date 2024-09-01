package org.example.todo.repository;

import org.example.todo.entity.Category;
import org.example.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findTaskByCategory(Category category);
}
