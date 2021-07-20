package com.hrAssistantWeb.services;


import com.hrAssistantWeb.entites.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User getCurrentUser();
}
