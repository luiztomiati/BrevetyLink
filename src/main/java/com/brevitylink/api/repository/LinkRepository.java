package com.brevitylink.api.repository;

import com.brevitylink.api.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link,Long> {
    Optional<Link> findByShortCode(String code);
    boolean existsByIdAndUserId(Long id, java.util.UUID userId);
}
