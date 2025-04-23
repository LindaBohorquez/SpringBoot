package com.api.demo.service;


import com.api.demo.model.User;
import com.api.demo.repository.UserRepository;

import jakarta.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateById(User request, Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(request.getName());
        user.setLasteName(request.getLasteName());
        user.setEmail(request.getEmail());
        return userRepository.save(user); // Muy importante guardar los cambios
    }

    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // dentro de UserService
    @Autowired
    private EntityManager entityManager;

    public List<Number> getRevisions(Long userId) {
    AuditReader reader = AuditReaderFactory.get(entityManager);
    return reader.getRevisions(User.class, userId);
    }

    public User getRevision(Long userId, Number revision) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.find(User.class, userId, revision);
    }
    

}
