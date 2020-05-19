package com.volkodav4ik.acessingdatapostgresql;

import com.volkodav4ik.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);
}
