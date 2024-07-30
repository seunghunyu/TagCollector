package com.tag.collector.repository;

import org.springframework.stereotype.Repository;

public interface UserRepository {
    Boolean joinMember(String userId, String username, String password);
    Boolean login(String userId, String password);
    Boolean findById(String userId);
}
