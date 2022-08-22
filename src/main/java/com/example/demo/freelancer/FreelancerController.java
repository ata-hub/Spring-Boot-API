package com.example.demo.freelancer;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.task.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "api/v1/freelancer")
@CrossOrigin(origins = "http://localhost:3000/")
public class FreelancerController {
    private FreelancerService freelancerService;

    @Autowired
    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @GetMapping
    public List<Freelancer> getFreelancers(){
        return freelancerService.getFreelancers();
    }
    @GetMapping("/unvalidated")
    public List<Freelancer> getUnvalidatedFreelancers(){
        return freelancerService.getUnvalidatedFreelancers();
    }
    @GetMapping("/validated")
    public List<Freelancer> getValidatedFreelancers(){
        return freelancerService.getValidatedFreelancers();
    }
    @PostMapping
    public void registerNewFreelancer(@RequestBody Freelancer freelancer){
        freelancerService.addNewFreelancer(freelancer);

    }
    @DeleteMapping(path = "{freelancerId}")
    public void deleteFreelancer(@PathVariable("freelancerId") Long freelancerId){
        freelancerService.deleteFreelancer(freelancerId);
    }
    @GetMapping(path = "{freelancerId}")
    public Freelancer getFreelancer(@PathVariable("freelancerId") Long freelancerId){
        return freelancerService.getFreelancerById(freelancerId);
    }
    @PutMapping(path = "{freelancerId}")
    public void updateFreelancer(@PathVariable("freelancerId") Long freelancerId,
    @RequestParam(required = false) String name,
    @RequestParam (required = false) String email) {
        freelancerService.updateFreelancer(freelancerId,name,email);
    }
    @PutMapping(path = "{freelancerId}/validation")
    public void updateFreelancerValidationTrue(@PathVariable("freelancerId") Long freelancerId) {
        freelancerService.updateFreelancerValidationTrue(freelancerId);
    }
    /*
    @GetMapping()
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Freelancer freelancer = freelancerService.getFreelancer(username);

                String access_token = JWT.create().withSubject(freelancer.getEmail()).withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                        .withIssuer(request.getRequestURL().toString()).withClaim("roles",freelancer.getRole().stream().map(Role::getName).toList())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception e){

                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(),errors);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        }
        else{
            throw new RuntimeException("Refresh token is missing");
        }
    }
    */

}
