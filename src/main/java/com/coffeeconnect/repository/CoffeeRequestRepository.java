package com.coffeeconnect.repository;

import com.coffeeconnect.entity.CoffeeRequest;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRequestRepository extends JpaRepository<CoffeeRequest, Long> {
    List<CoffeeRequest> findBySender(User sender);
    List<CoffeeRequest> findByReceiver(User receiver);
    Page<CoffeeRequest> findBySender(User sender, Pageable pageable);
    Page<CoffeeRequest> findByReceiver(User receiver, Pageable pageable);
    Page<CoffeeRequest> findByStatus(RequestStatus status, Pageable pageable);
    long countByStatus(RequestStatus status);
    Optional<CoffeeRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, RequestStatus status);
    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, RequestStatus status);
    long count();
    
    @Query("SELECT COUNT(cr) FROM CoffeeRequest cr WHERE cr.status = 'PENDING'")
    long countPending();
    
    @Query("SELECT COUNT(cr) FROM CoffeeRequest cr WHERE cr.status = 'ACCEPTED'")
    long countAccepted();
    
    @Query("SELECT COUNT(cr) FROM CoffeeRequest cr WHERE cr.status = 'DECLINED'")
    long countDeclined();
    
    @Query("SELECT cr FROM CoffeeRequest cr JOIN FETCH cr.sender JOIN FETCH cr.receiver ORDER BY cr.createdAt DESC")
    List<CoffeeRequest> findAllWithUsers();
    
    @Query("SELECT cr FROM CoffeeRequest cr JOIN FETCH cr.sender JOIN FETCH cr.receiver " +
           "WHERE (:search IS NULL OR LOWER(cr.sender.firstName) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(cr.receiver.firstName) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(cr.message) LIKE LOWER(CONCAT('%',:search,'%'))) " +
           "AND (:status IS NULL OR cr.status = :status) " +
           "ORDER BY cr.createdAt DESC")
    Page<CoffeeRequest> searchRequests(@Param("search") String search,
                                        @Param("status") RequestStatus status,
                                        Pageable pageable);
}
