package com.caionastu.postapi.user.repository;

import com.caionastu.postapi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User user set user.name = :name where user.id = :id")
    void updateName(@Param("id") UUID id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query("update User user set user.active = :active where user.id = :id")
    void updateActive(@Param("id") UUID id, @Param("active") boolean active);

}
