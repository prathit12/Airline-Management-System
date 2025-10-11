package com.example.airline.repository;

import com.example.airline.entity.User;
import com.example.airline.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    
    Optional<UserSession> findBySessionToken(String sessionToken);
    
    List<UserSession> findByUser(User user);
    
    List<UserSession> findByUserId(Long userId);
    
    @Query("SELECT us FROM UserSession us WHERE us.sessionToken = :token AND us.expiresAt > :now")
    Optional<UserSession> findValidSession(@Param("token") String token, @Param("now") LocalDateTime now);
    
    @Query("SELECT us FROM UserSession us WHERE us.user.id = :userId AND us.expiresAt > :now")
    List<UserSession> findActiveSessions(@Param("userId") Long userId, @Param("now") LocalDateTime now);
    
    @Query("SELECT us FROM UserSession us WHERE us.expiresAt < :now")
    List<UserSession> findExpiredSessions(@Param("now") LocalDateTime now);
    
    @Query("SELECT us FROM UserSession us WHERE us.user.id = :userId ORDER BY us.createdAt DESC")
    List<UserSession> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    
    @Query("SELECT us FROM UserSession us WHERE us.ipAddress = :ipAddress AND us.expiresAt > :now")
    List<UserSession> findActiveSessionsByIpAddress(@Param("ipAddress") String ipAddress, @Param("now") LocalDateTime now);
    
    @Query("SELECT COUNT(us) FROM UserSession us WHERE us.user.id = :userId AND us.expiresAt > :now")
    long countActiveSessionsByUserId(@Param("userId") Long userId, @Param("now") LocalDateTime now);
    
    @Modifying
    @Query("UPDATE UserSession us SET us.lastAccessed = :accessTime WHERE us.sessionToken = :token")
    void updateLastAccessed(@Param("token") String token, @Param("accessTime") LocalDateTime accessTime);
    
    @Modifying
    @Query("UPDATE UserSession us SET us.expiresAt = :newExpiry WHERE us.sessionToken = :token")
    void extendSession(@Param("token") String token, @Param("newExpiry") LocalDateTime newExpiry);
    
    @Modifying
    @Query("DELETE FROM UserSession us WHERE us.user.id = :userId")
    void deleteAllUserSessions(@Param("userId") Long userId);
    
    @Modifying
    @Query("DELETE FROM UserSession us WHERE us.expiresAt < :cutoffDate")
    void deleteExpiredSessions(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    @Modifying
    @Query("DELETE FROM UserSession us WHERE us.sessionToken = :token")
    void deleteBySessionToken(@Param("token") String token);
    
    Boolean existsBySessionToken(String sessionToken);
}