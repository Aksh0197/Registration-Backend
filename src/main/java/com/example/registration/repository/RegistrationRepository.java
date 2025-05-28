package com.example.registration.repository;

import com.example.registration.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Registration> findByStudentId(Long studentId);

    List<Registration> findByCourseId(Long courseId);
}
