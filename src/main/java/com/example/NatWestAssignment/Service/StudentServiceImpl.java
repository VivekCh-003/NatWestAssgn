package com.example.NatWestAssignment.Service;

import com.example.NatWestAssignment.Entity.Student;
import com.example.NatWestAssignment.Repository.StudentRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepo studentRepo;


    @Override
    public byte[] processCSV(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader("rollNumber", "name", "science", "maths", "english", "computer").withTrim().parse(reader);
            List<CSVRecord> records = csvParser.getRecords();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {

                writer.write("rollNumber,name,science,maths,english,computer,eligibilityStatus\n");

                for (CSVRecord record : records) {
                    String rollNumber = record.get("rollNumber");
                    String studentName = record.get("name");
                    int science = Integer.parseInt(record.get("science"));
                    int maths = Integer.parseInt(record.get("maths"));
                    int english = Integer.parseInt(record.get("english"));
                    int computer = Integer.parseInt(record.get("computer"));

                    String eligibilityStatus = checkEligibility(science, maths, english, computer);

                    Student student = studentRepo.findById(rollNumber).orElse(null);
                    if (student == null) {
                        student = new Student();
                        student.setRollNumber(rollNumber);
                        student.setName(studentName);
                        student.setScience(science);
                        student.setMaths(maths);
                        student.setEnglish(english);
                        student.setComputer(computer);
                        student.setEligibilityStatus(eligibilityStatus);
                        studentRepo.save(student);
                    }

                    writer.write(String.format("%s,%s,%d,%d,%d,%d,%s\n", rollNumber, studentName, science, maths, english, computer, eligibilityStatus));
                }
            }

            return outputStream.toByteArray();
        }
    }


    @Override
    public String getEligibilityStatus(String rollNumber) {
        Optional<Student> optionalStudent = studentRepo.findById(rollNumber);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return student.getEligibilityStatus();
        } else {
            return "NA";
        }
    }

    private String checkEligibility(int science,int maths,int english,int computer){
        if (science > 85 && maths > 90 && english > 75 && computer > 95) {
            return "YES";
        } else {
            return "NO";
        }
    }
}
