package com.jmcosta.shopmebackend.user.repository;

import com.jmcosta.shopmebackend.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
