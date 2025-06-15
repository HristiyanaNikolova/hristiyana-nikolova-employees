package com.example.employees.controller;

import com.example.employees.dto.EmployeePairDTO;
import com.example.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping("/results")
    public List<EmployeePairDTO> getLongestWorkingPair() {
        return service.findEmployeePairsWithMaxOverlap();
    }
}
