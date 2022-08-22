package com.example.demo.task;

import com.example.demo.freelancer.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("SELECT s FROM Freelancer s WHERE s.email = ?1")
    Optional<Freelancer> findByFreelancerId(String email);
    @Query("SELECT t FROM Task t WHERE t.type=?1")
    List<Task> findDoneTasks(String done);
}
