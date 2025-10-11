package com.example.airline.repository;

import com.example.airline.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
    
    Boolean existsByName(String name);
    
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<Role> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT r FROM Role r JOIN r.permissions p WHERE p.name = :permissionName")
    List<Role> findByPermissionName(@Param("permissionName") String permissionName);
    
    @Query("SELECT DISTINCT r FROM Role r JOIN FETCH r.permissions WHERE r.name IN :roleNames")
    Set<Role> findByNameInWithPermissions(@Param("roleNames") List<String> roleNames);
    
    @Query("SELECT r FROM Role r WHERE r.name LIKE %:namePattern%")
    List<Role> findByNameContaining(@Param("namePattern") String namePattern);
    
    @Query("SELECT COUNT(r) FROM Role r")
    long countRoles();
}