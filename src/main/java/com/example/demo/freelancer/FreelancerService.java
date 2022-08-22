package com.example.demo.freelancer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FreelancerService implements UserDetailsService {
    private final FreelancerRepository freelancerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FreelancerService(FreelancerRepository freelancerRepository , PasswordEncoder encoder) {
        this.freelancerRepository = freelancerRepository;
        passwordEncoder = encoder; //burda password encoderı oluştur. Hata çıkarsa burayı sil
    }


    public List<Freelancer> getFreelancers(){
    return freelancerRepository.findAll();
    }

    public void addNewFreelancer(Freelancer freelancer) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.
                findFreelancerByEmail(freelancer.getEmail());
        if(freelancerOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        else{
            //freelancer.setPassword(passwordEncoder.encode(freelancer.getPassword()));
            freelancerRepository.save(freelancer);
        }
    }

    public void deleteFreelancer(Long freelancerId) {
        boolean exists = freelancerRepository.existsById(freelancerId);
        if(!exists){
            throw new IllegalStateException("Freelancer with id "+ freelancerId + " doesn't exist");
        }
        freelancerRepository.deleteById(freelancerId);
    }
    @Transactional
    public void updateFreelancer(Long freelancerId, String name, String email) {
        boolean exists = freelancerRepository.existsById(freelancerId);
        Freelancer freelancer = freelancerRepository.findById(freelancerId).
                orElseThrow(()->new
                        IllegalStateException("Freelancer with id "+ freelancerId + " doesn't exist"));
        if(name != null && name.length()>0 && !Objects.equals(freelancer.getName(),name)){
            freelancer.setName(name);
        }
        if(email != null && email.length()>0 && !Objects.equals(freelancer.getEmail(),email)){
            Optional<Freelancer> freelancerByEmail = freelancerRepository.findFreelancerByEmail(email);
            if(freelancerByEmail.isPresent()){
                throw new IllegalStateException("email is taken");
            }
            freelancer.setEmail(email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Freelancer freelancer = freelancerRepository.findFreelancerByEmail(email).
                orElseThrow(()->new
                        UsernameNotFoundException("Freelancer with email" + email + " doesn't exist"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        authorities.add(new SimpleGrantedAuthority("admin"));
        return new org.springframework.security.core.userdetails.User(freelancer.getEmail(),freelancer.getPassword(), authorities);
    }

    public Freelancer getFreelancer(String email) {
        Freelancer freelancer = freelancerRepository.findFreelancerByEmail(email).
                orElseThrow(()->new
                        UsernameNotFoundException("Freelancer with email" + email + " doesn't exist"));
        return freelancer;
    }

    public Freelancer getFreelancerById(Long freelancerId) {
        boolean exists = freelancerRepository.existsById(freelancerId);
        if(exists){
            Freelancer freelancer = freelancerRepository.findById(freelancerId).
                    orElseThrow(()->new
                            IllegalStateException("Freelancer with id "+ freelancerId + " doesn't exist"));
            return freelancer;
        }


        return null;
    }

    public List<Freelancer> getUnvalidatedFreelancers() {
        List<Freelancer> freelancersByValidation = freelancerRepository.findFreelancerByValidation(false);
        return freelancersByValidation;
    }

    public List<Freelancer> getValidatedFreelancers() {
        List<Freelancer> freelancersByValidation = freelancerRepository.findFreelancerByValidation(true);
        return freelancersByValidation;
    }
    @Transactional
    public void updateFreelancerValidationTrue(Long freelancerId) {
        boolean exists = freelancerRepository.existsById(freelancerId);
        if(exists){
            Freelancer freelancer = freelancerRepository.findById(freelancerId).
                    orElseThrow(()->new
                            IllegalStateException("Freelancer with id "+ freelancerId + " doesn't exist"));
            freelancer.setValidated(true);
        }
    }
}
