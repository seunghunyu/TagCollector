package com.tag.collector.service;

import com.tag.collector.repository.UserJDBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserJDBCRepository userRepository;

    public Boolean joinMember(String userId, String username, String password){
        return userRepository.joinMember(userId, username, password);
    }
    public Boolean login(String userId, String password){
        return userRepository.login(userId, password);
    }
    public Boolean findById(String userId){
        return userRepository.findById(userId);
    }

}
