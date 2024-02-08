package com.example.NatWestAssignment.Controller;

import com.example.NatWestAssignment.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> uploadCSVFile(@RequestParam("file")MultipartFile file){
        try{
            byte[] updatedCsv = studentService.processCSV(file);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition","attachment; filename =\"updated_students.csv\"")
                    .body(updatedCsv);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/student")
    public String getStatus(@RequestParam("rollNumber") String rollNumber){
        return studentService.getEligibilityStatus(rollNumber);
    }
}
