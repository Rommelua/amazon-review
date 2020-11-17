package com.amazon.review.controller;

import com.amazon.review.model.FileItem;
import com.amazon.review.service.CsvFileService;
import com.amazon.review.service.CsvFileServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    private CsvFileServiceImpl service;

    @GetMapping("/test")
    public String test() {
        service.loadFile();
        List<FileItem> fileItems = service.readTransactions();
        return "test";
    }
}
