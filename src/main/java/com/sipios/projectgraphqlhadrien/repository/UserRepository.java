package com.sipios.projectgraphqlhadrien.repository;

import com.sipios.projectgraphqlhadrien.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
