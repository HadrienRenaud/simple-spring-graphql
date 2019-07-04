package com.sipios.projectgraphqlhadrien.repository;

import com.sipios.projectgraphqlhadrien.models.MediaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<MediaModel, Integer> {

    List<MediaModel> findAll();

}
