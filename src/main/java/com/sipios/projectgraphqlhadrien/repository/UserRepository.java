package com.sipios.projectgraphqlhadrien.repository;

import com.sipios.projectgraphqlhadrien.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> getById(Integer id);
}
