package com.example.employees.service;

import com.example.employees.entity.Employee;
import com.example.employees.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CSVService {

    @Autowired
    private EmployeeRepository repository;

    public void clearPreviousResults() {
        repository.deleteAll();
    }

    public void processCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd][dd/MM/yyyy][MM-dd-yyyy]");
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("EmpID")) {//what if something else
                    continue;
                }
                String[] tokens = line.split(",\\s*");
                if (tokens.length < 4) {
                    continue;
                }
                Long empId = Long.parseLong(tokens[0]);
                Long projectId = Long.parseLong(tokens[1]);
                LocalDate from = LocalDate.parse(tokens[2], formatter);
                LocalDate to = tokens[3].equalsIgnoreCase("NULL") ?
                        LocalDate.now() : LocalDate.parse(tokens[3], formatter);
                if (from.isAfter(to)) {
                    continue;
                }

                Employee ep = new Employee(empId, projectId, from, to);
                repository.save(ep);
            }
        } catch (IOException e) {
            throw new RuntimeException("CSV parsing failed", e);
        }
    }
}
