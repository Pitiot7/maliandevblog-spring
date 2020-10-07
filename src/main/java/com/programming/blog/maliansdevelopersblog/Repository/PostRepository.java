package com.programming.blog.maliansdevelopersblog.Repository;

import com.programming.blog.maliansdevelopersblog.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
