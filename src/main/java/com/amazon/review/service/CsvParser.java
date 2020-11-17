package com.amazon.review.service;

import com.amazon.review.model.FileItem;
import org.springframework.stereotype.Service;

@Service
public class CsvParser {
    public FileItem getFileItem(String line) {
        int numberStart = line.split("\\d+,\\d+,\\d+,\\d{9,}")[0].length();
        String ids = line.substring(0, numberStart - 1);
        String[] fields = ids.split(",", 4);
        FileItem item = new FileItem();
        item.setId(Long.parseLong(fields[0]));
        item.setProductId(fields[1]);
        item.setUserId(fields[2]);
        item.setProfileName(fields[3]);

        String numbersAndText = line.substring(numberStart);
        fields = numbersAndText.split(",", 6);
        item.setHelpfulnessNumerator(Integer.parseInt(fields[0]));
        item.setHelpfulnessDenominator(Integer.parseInt(fields[1]));
        item.setScore(Integer.parseInt(fields[2]));
        item.setTime(Long.parseLong(fields[3]));
        item.setSummary(fields[4]);
        item.setText(fields[5]);
        return item;
    }
}
