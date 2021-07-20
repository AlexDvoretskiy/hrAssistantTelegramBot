package com.hrAssistantWeb.repositories;

import com.hrAssistantWeb.entites.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByUserName(String userName);
}
