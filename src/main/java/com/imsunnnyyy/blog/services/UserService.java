package com.imsunnnyyy.blog.services;

import com.imsunnnyyy.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}

