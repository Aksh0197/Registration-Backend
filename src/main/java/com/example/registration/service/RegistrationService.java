package com.example.registration.service;

import com.example.registration.model.Course;
import com.example.registration.model.Registration;
import com.example.registration.model.Student;
import com.example.registration.repository.CourseRepository;
import com.example.registration.repository.RegistrationRepository;
import com.example.registration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // ✅ Get all registrations
    public List<Registration> listAll() {
        return registrationRepository.findAll();
    }

    // ✅ Register a student to a course
    public Registration register(Long studentId, Long courseId) {
        if (registrationRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalArgumentException("This student is already registered for this course.");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setCourse(course);
        registration.setRegisteredAt(LocalDateTime.now());

        return registrationRepository.save(registration);
    }

    // ✅ Update course of existing registration
    public Registration update(Long registrationId, Long newCourseId) {
        Registration existing = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NoSuchElementException("Registration not found"));

        Course newCourse = courseRepository.findById(newCourseId)
                .orElseThrow(() -> new NoSuchElementException("New course not found"));

        // Optional: Check if already registered
        if (registrationRepository.existsByStudentIdAndCourseId(existing.getStudent().getId(), newCourseId)) {
            throw new IllegalArgumentException("Student already registered in this course.");
        }

        existing.setCourse(newCourse);
        return registrationRepository.save(existing);
    }

    // ✅ Delete registration
    public void delete(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }

    // ✅ Filter by student
    public List<Registration> filterByStudent(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }

    // ✅ Filter by course
    public List<Registration> filterByCourse(Long courseId) {
        return registrationRepository.findByCourseId(courseId);
    }
}