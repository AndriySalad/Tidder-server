package com.tidder.dao;

import com.tidder.domains.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostRepository extends JpaRepository<Post, Long> {
}
