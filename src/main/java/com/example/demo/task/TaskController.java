package com.example.demo.task;

import com.example.demo.freelancer.Freelancer;
import com.example.demo.freelancer.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin(origins = "http://localhost:3000/")
public class TaskController {
    private TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/tasks")
    public List<Task> getTasks(){
        return taskService.getTasks();
    }
    @GetMapping(path ="freelancers/{freelancerId}/tasks")
    public List<Task> getTasksByID(@PathVariable Long freelancerId){
        return taskService.getTasksByFreelancerID(freelancerId);
    }
    @PostMapping(path ="freelancers/{freelancerId}/tasks")
    public void addNewTask(@PathVariable Long freelancerId,
            @RequestBody Task task){
        taskService.addNewTask(freelancerId,task);
    }
    @PutMapping("/freelancers/tasks")
    public void updateTask( @RequestBody Task task){
        taskService.updateTask(task);
    }
    @DeleteMapping(path = "/freelancers/tasks/{taskId}")
    public void deleteTaskById(@PathVariable("taskId") Long taskId){
        taskService.deleteTaskById(taskId);
    }
    @GetMapping("/freelancers/ranking")
    public List<Freelancer> getRankingList(){return taskService.getRankingList();}


}
