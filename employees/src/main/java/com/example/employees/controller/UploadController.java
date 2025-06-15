package com.example.employees.controller;

import com.example.employees.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/upload")
@CrossOrigin(origins = "http://localhost:4200")
public class UploadController {

    @Autowired
    private CSVService csvService;

    @PostMapping
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        csvService.clearPreviousResults();
        csvService.processCSV(file);
        return ResponseEntity.ok("File uploaded and processed");
    }
}
