package com.tastytown.backend.Helper;

import java.util.NoSuchElementException;


import org.springframework.stereotype.Component;
import com.tastytown.backend.entity.User;
import com.tastytown.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {
    private final UserRepository userRepository;

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("user not found with id" + userId));
    }
}
