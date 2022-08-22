package com.example.demo.task;

import com.example.demo.freelancer.Freelancer;
import com.example.demo.freelancer.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final FreelancerRepository freelancerRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository, FreelancerRepository freelancerRepository) {
        this.taskRepository = taskRepository;
        this.freelancerRepository = freelancerRepository;
    }

    public void addNewTask(Long id,Task task) {
        Freelancer freelancer = freelancerRepository.findById(id).
                orElseThrow(()->new
                        IllegalStateException("Freelancer with id "+ id + " doesn't exist"));
        task.setFreelancer(freelancer);
        taskRepository.save(task);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByFreelancerID(Long freelancerId) {
        Freelancer freelancer = freelancerRepository.findById(freelancerId).
                orElseThrow(()->new
                        IllegalStateException("Freelancer with id "+ freelancerId + " doesn't exist"));
        return freelancer.getTasks();
    }

    public void updateTask (Task newTask) {
        Task t=taskRepository.findById(newTask.getId())
                 .orElseThrow(()->new
                         IllegalStateException("Task with id "+ newTask.getId() + " doesn't exist"));
        newTask.setFreelancer(t.getFreelancer());
         taskRepository.save(newTask);


    }
    public void deleteTaskById(Long taskId) {
        boolean exists = taskRepository.existsById(taskId);
        if(!exists){
            throw new IllegalStateException("Task with id "+ taskId + " doesn't exist");
        }
        taskRepository.deleteById(taskId);
    }

    public List<Freelancer> getRankingList() {
        List<Task> doneList = taskRepository.findDoneTasks("done");
        List<Freelancer> rankingList = new ArrayList<>();
        if(doneList.isEmpty()){
            return rankingList;
        }
        Map<Freelancer,Integer> freelancerTaskCounter = new HashMap<>();
        for(Task t:doneList){
            if(!freelancerTaskCounter.containsKey(t.getFreelancer())){
                freelancerTaskCounter.put(t.getFreelancer(),1);
            }
            else{
                Integer value =freelancerTaskCounter.get(t.getFreelancer());
                freelancerTaskCounter.replace(t.getFreelancer(),value+1);
            }
        }
        Map<Freelancer, Integer> sortedMap = sortByValue(freelancerTaskCounter);

        rankingList= new ArrayList<>(sortedMap.keySet());
        Collections.reverse(rankingList);

        return rankingList;
    }
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;

    }
}
