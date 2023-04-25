package com.jmcosta.shopmebackend.roles.repository;

import com.jmcosta.shopmebackend.roles.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
