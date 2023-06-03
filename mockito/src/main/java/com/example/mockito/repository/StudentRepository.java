package com.example.mockito.repository;

import com.example.mockito.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
