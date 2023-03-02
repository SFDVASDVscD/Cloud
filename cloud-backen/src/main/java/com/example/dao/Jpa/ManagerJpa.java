package com.example.dao.Jpa;


import com.example.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ManagerJpa extends JpaRepository<Manager,Integer> {
    Optional<Manager> findUserByManagerName(String managerName);
}
