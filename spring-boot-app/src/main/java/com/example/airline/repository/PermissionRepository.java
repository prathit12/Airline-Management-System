package com.example.airline.repository;

import com.example.airline.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    Optional<Permission> findByName(String name);
    
    Boolean existsByName(String name);
    
    List<Permission> findByResource(String resource);
    
    List<Permission> findByAction(String action);
    
    List<Permission> findByResourceAndAction(String resource, String action);
    
    @Query("SELECT p FROM Permission p WHERE p.resource IN :resources")
    List<Permission> findByResourceIn(@Param("resources") List<String> resources);
    
    @Query("SELECT DISTINCT p.resource FROM Permission p ORDER BY p.resource")
    List<String> findAllResources();
    
    @Query("SELECT DISTINCT p.action FROM Permission p ORDER BY p.action")
    List<String> findAllActions();
    
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.name = :roleName")
    List<Permission> findByRoleName(@Param("roleName") String roleName);
    
    @Query("SELECT p FROM Permission p WHERE p.name IN :permissionNames")
    Set<Permission> findByNameIn(@Param("permissionNames") List<String> permissionNames);
    
    @Query("SELECT p FROM Permission p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :pattern, '%')) " +
           "OR LOWER(p.resource) LIKE LOWER(CONCAT('%', :pattern, '%')) " +
           "OR LOWER(p.action) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Permission> findBySearchPattern(@Param("pattern") String pattern);
}