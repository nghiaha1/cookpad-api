package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.Role;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsernameAndStatus(String username, Status status);

    Optional<User> findByIdAndStatus(Long id, Status status);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findAllByStatus(Status status);

    List<User> findAllByRole(Role role);
}
