package org.example.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Slice<Post> findAllBy(Pageable pageable);
  Slice<Post> findPostByIdLessThanOrderByIdDesc(long id, Pageable pageable);
}
