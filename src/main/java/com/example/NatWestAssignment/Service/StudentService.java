package com.example.NatWestAssignment.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    byte[] processCSV(MultipartFile file) throws IOException;

    String getEligibilityStatus(String rollNumber);
}
