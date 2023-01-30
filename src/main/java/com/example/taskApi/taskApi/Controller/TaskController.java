package com.example.taskApi.taskApi.Controller;

import com.example.taskApi.taskApi.Model.Task;
import com.example.taskApi.taskApi.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/alltask")
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable long id) {
        return taskRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Task>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(taskRepository.findAllByNameContainingIgnoreCase(name));
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<Task>> getKeyword (@PathVariable String keyword) {
        return ResponseEntity.ok(taskRepository.findAllByKeywordContainingIgnoreCase(keyword));
    }

    @PostMapping("/registertask")
    public ResponseEntity<Task> resgisterTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(task));
    }

    @PutMapping("/updatetask")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        return taskRepository.findById(task.getId())
                .map(resp -> { return ResponseEntity.ok().body(taskRepository.save(task));})
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        return taskRepository.findById(id)
                .map(resp -> { taskRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();})
                .orElse(ResponseEntity.notFound().build());
    }

}
