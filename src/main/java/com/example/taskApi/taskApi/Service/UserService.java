package com.example.taskApi.taskApi.Service;

import com.example.taskApi.taskApi.Model.User;
import com.example.taskApi.taskApi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> updateUser(User user) {
        if(userRepository.findById(user.getId()).isPresent()) {
            Optional<User> findUser = userRepository.findByEmail(user.getEmail());
            if(findUser.isPresent()) {
                if(findUser.get().getId() != user.getId()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário já existe!", null);
                }
            }
            return Optional.of(userRepository.save(user));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
    }

}
