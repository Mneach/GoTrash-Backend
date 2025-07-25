package com.gotrash.repository;

import com.gotrash.entity.TrashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrashRepository extends JpaRepository<TrashEntity, UUID> {

  @Query("SELECT t FROM TrashEntity t WHERE LOWER(t.name) = LOWER(:name)")
  Optional<TrashEntity> findByNameIgnoreCase(@Param("name") String name);

}
