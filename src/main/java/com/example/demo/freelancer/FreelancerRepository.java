package com.example.demo.freelancer;

import com.example.demo.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreelancerRepository
        extends JpaRepository<Freelancer,Long> {
    @Query("SELECT s FROM Freelancer s WHERE s.email = ?1")
    Optional<Freelancer> findFreelancerByEmail(String email);
    @Query("SELECT t FROM Freelancer t WHERE t.isValidated=?1")
    List<Freelancer> findFreelancerByValidation(boolean validated);



}
