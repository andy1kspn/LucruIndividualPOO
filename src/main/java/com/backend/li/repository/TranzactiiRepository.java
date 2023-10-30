package com.backend.li.repository;

import com.backend.li.model.TranzactiiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranzactiiRepository extends JpaRepository<TranzactiiEntity, Long> {
    Optional<TranzactiiEntity> findById(Integer id_utilizator);
}
