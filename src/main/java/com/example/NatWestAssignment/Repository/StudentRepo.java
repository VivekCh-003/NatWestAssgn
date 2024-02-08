package com.example.NatWestAssignment.Repository;

import com.example.NatWestAssignment.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,String> {
}
