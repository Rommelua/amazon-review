package com.amazon.review.service;

import com.amazon.review.model.FileItem;
import java.util.List;

public interface CsvFileService {
    List<FileItem> readTransactions();
}
