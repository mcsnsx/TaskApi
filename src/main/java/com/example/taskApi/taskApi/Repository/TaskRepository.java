package com.example.taskApi.taskApi.Repository;

import com.example.taskApi.taskApi.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findAllByNameContainingIgnoreCase (String name);

    public List<Task> findAllByKeywordContainingIgnoreCase (String keyword);

}
