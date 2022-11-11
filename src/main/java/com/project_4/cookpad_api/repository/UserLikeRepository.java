package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.UserLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLikes, Long>{

    List<UserLikes> findAllByPostId(Long id);
}
