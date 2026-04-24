package com.brevitylink.api.repository;

import com.brevitylink.api.model.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmailIgnoreCase(@Email String email);
    Boolean existsByEmailIgnoreCase(@Nullable String email);
    Boolean existsByNickNameIgnoreCase(@Nullable String nickname);

    boolean existsByEmailAndIdNot(@Email String email, UUID id);

    boolean existsByPasswordAndId(@Nullable String oldEncode, @NotNull UUID id);

    Optional<Object> findByEmail(String userIdentifier);
}
