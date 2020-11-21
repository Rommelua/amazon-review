package com.amazon.review.service;

import com.amazon.review.exception.CsvFileException;
import com.amazon.review.model.FileItem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CsvFileServiceImpl implements CsvFileService {

    @Autowired
    private CsvParser parser;

    @Value("${csv.file.url}")
    private String fileUrl;

    @Value("${csv.file.path}")
    private String filePath;

    @Override
    public List<FileItem> getFileItems() {
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            return reader.lines()
                    .skip(1)
                    .map(parser::getFileItem)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CsvFileException("Can't read file " + fileUrl, e);
        }
    }

    @Override
    public void loadFile() {
        try (ReadableByteChannel readableByteChannel
                     = Channels.newChannel(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new CsvFileException("Can't read file from url " + fileUrl, e);
        }
    }

    public static void main(String[] args) {
        String fileUrl = "D:\\Java\\Prodjects\\MateAcademy\\amazon-review\\src\\main\\resources\\Reviews.csv";
        CsvParser parser = new CsvParser();
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(new FileInputStream(fileUrl)))) {
            List<String> lines = reader.lines()
                    .skip(1)
                    .collect(Collectors.toList());
            List<FileItem> items = new ArrayList<>(lines.size());
            for (int i = 0; i < lines.size(); i++) {
                String s = lines.get(i);
                try {
                    items.add(parser.getFileItem(s));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(s);
                }
            }
            System.out.println();
        } catch (Exception e) {
            throw new CsvFileException("Can't read file " + fileUrl, e);
        }
    }
}
