package com.brevitylink.api.repository;
import com.brevitylink.api.model.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link,Long> {
    Optional<Link> findByShortCode(String code);
    boolean existsByIdAndUserId(Long id, java.util.UUID userId);
    Page<Link> findByUserId(UUID userId, Pageable pageable);
}
