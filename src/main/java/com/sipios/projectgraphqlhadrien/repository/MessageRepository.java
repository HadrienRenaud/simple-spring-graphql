package com.sipios.projectgraphqlhadrien.repository;

import com.sipios.projectgraphqlhadrien.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

    List<MessageModel> findAll();
    Optional<MessageModel> findById(Integer id);
    List<MessageModel> findAllByOrderByCreatedAtDesc();

}
