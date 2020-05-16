package com.volkodav4ik.acessingdatapostgresql;

import com.volkodav4ik.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Integer> {

}
