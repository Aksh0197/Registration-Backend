package com.example.registration.controller;

import com.example.registration.model.Registration;
import com.example.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // ✅ Get all registrations
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.listAll();
    }

    // ✅ Register a student to a course
    @PostMapping
    public ResponseEntity<Registration> registerStudentToCourse(
            @RequestParam Long studentId,
            @RequestParam Long courseId
    ) {
        Registration registration = registrationService.register(studentId, courseId);
        return ResponseEntity.ok(registration);
    }

    // ✅ Delete a registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Filter registrations by student
    @GetMapping("/filter/student")
    public List<Registration> filterByStudent(@RequestParam Long studentId) {
        return registrationService.filterByStudent(studentId);
    }

    // ✅ Filter registrations by course
    @GetMapping("/filter/course")
    public List<Registration> filterByCourse(@RequestParam Long courseId) {
        return registrationService.filterByCourse(courseId);
    }

    // ✅ Update registration (optional)
    @PutMapping("/{id}")
    public ResponseEntity<Registration> updateCourseForRegistration(
            @PathVariable Long id,
            @RequestParam Long courseId
    ) {
        Registration updated = registrationService.update(id, courseId);
        return ResponseEntity.ok(updated);
    }
}
