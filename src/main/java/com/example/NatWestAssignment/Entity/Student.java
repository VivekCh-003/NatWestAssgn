package com.example.NatWestAssignment.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
public class Student {

    @Id
    private String rollNumber;
    private String name;
    private int science;
    private int maths;
    private int english;
    private int computer;
    private String eligibilityStatus;
}
