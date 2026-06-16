package com.coffeeconnect.repository;

import com.coffeeconnect.entity.Connection;
import com.coffeeconnect.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Optional<Connection> findByUserOneAndUserTwo(User userOne, User userTwo);
    Optional<Connection> findByUserTwoAndUserOne(User userTwo, User userOne);
    boolean existsByUserOneAndUserTwo(User userOne, User userTwo);
    boolean existsByUserTwoAndUserOne(User userTwo, User userOne);
    long count();
    
    @Query("SELECT c FROM Connection c WHERE c.userOne = :user OR c.userTwo = :user ORDER BY c.connectedAt DESC")
    List<Connection> findAllConnectionsForUser(@Param("user") User user);
    
    @Query("SELECT c FROM Connection c WHERE c.userOne = :user OR c.userTwo = :user ORDER BY c.connectedAt DESC")
    Page<Connection> findConnectionsForUser(@Param("user") User user, Pageable pageable);
    
    @Query("SELECT COUNT(c) FROM Connection c WHERE c.userOne = :user OR c.userTwo = :user")
    long countConnectionsForUser(@Param("user") User user);
    
    @Query("SELECT CASE WHEN c.userOne = :user THEN c.userTwo ELSE c.userOne END FROM Connection c WHERE c.userOne = :user OR c.userTwo = :user")
    List<User> findConnectedUsers(@Param("user") User user);
}
