package com.flab.bbt.auth.service;

import com.flab.bbt.exception.UserNotFoundException;
import com.flab.bbt.user.domain.User;
import com.flab.bbt.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncrypter passwordEncrypter;

    public AuthService(UserRepository userRepository, PasswordEncrypter passwordEncrypter) {
        this.userRepository = userRepository;
        this.passwordEncrypter = passwordEncrypter;
    }

    public void signUp(User user) {
        // [ToDo]이메일 중복체크

        // password 암호화
        user.setPassword(passwordEncrypter.encrypt(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String password){
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);

        if(user.isEmpty()){
            throw new UserNotFoundException();
        }

        return user;
    }
}
