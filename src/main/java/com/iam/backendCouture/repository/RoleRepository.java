package com.iam.backendCouture.repository;

import com.iam.backendCouture.entities.ERole;
import com.iam.backendCouture.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,
		Long> {
	Optional<Role> findByName(ERole name);
}