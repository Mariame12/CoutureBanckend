package com.iam.backendCouture.repository;

import com.iam.backendCouture.entities.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesureRepository extends JpaRepository<Mesure, Long> {
}
