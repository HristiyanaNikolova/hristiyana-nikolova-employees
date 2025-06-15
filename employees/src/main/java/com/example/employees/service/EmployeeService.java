package com.example.employees.service;

import com.example.employees.dto.EmployeePairDTO;
import com.example.employees.entity.Employee;
import com.example.employees.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<EmployeePairDTO> findEmployeePairsWithMaxOverlap() {
        List<Employee> allRecords = repository.findAll();

        Map<String, Long> pairDurationMap = new HashMap<>();
        List<EmployeePairDTO> results = new ArrayList<>();

        for (int i = 0; i < allRecords.size(); i++) {
            Employee ep1 = allRecords.get(i);

            for (int j = i + 1; j < allRecords.size(); j++) {
                Employee ep2 = allRecords.get(j);


                if (!ep1.getProjectId().equals(ep2.getProjectId())) {
                    continue;
                }

                LocalDate start = ep1.getDateFrom().isAfter(ep2.getDateFrom()) ? ep1.getDateFrom() : ep2.getDateFrom();
                LocalDate end = ep1.getDateTo().isBefore(ep2.getDateTo()) ? ep1.getDateTo() : ep2.getDateTo();

                long days = ChronoUnit.DAYS.between(start, end);
                if (days > 0) {
                    Long empA = Math.min(ep1.getEmpId(), ep2.getEmpId());
                    Long empB = Math.max(ep1.getEmpId(), ep2.getEmpId());
                    String key = empA + "-" + empB;

                    pairDurationMap.put(key, pairDurationMap.getOrDefault(key, 0L) + days);
                    results.add(new EmployeePairDTO(empA, empB, days));
                }
            }
        }

        String bestPair = pairDurationMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        long maxDays = pairDurationMap.getOrDefault(bestPair, 0L);

        if (bestPair != null) {
            String[] ids = bestPair.split("-");
            return List.of(new EmployeePairDTO(
                    Long.parseLong(ids[0]), Long.parseLong(ids[1]), maxDays));
        }

        return List.of();
    }
}
