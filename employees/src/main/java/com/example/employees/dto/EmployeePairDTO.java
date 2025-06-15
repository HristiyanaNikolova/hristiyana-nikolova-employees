package com.example.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeePairDTO {
    private Long empId1;
    private Long empId2;
    private long daysWorkedTogether;
}
