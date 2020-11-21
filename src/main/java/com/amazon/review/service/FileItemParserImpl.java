package com.amazon.review.service;

import com.amazon.review.model.FileItem;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import org.springframework.stereotype.Service;

@Service
public class FileItemParserImpl implements FileItemParser {

    public static final String DEFAULT_PASSWORD = "1111";

    @Override
    public Product getProduct(FileItem fileItem) {
        return new Product(fileItem.getProductId());
    }

    @Override
    public Review getReview(FileItem fileItem) {
        Review review = new Review();
        review.setHelpfulnessNumerator(fileItem.getHelpfulnessNumerator());
        review.setHelpfulnessDenominator(fileItem.getHelpfulnessDenominator());
        review.setScore(fileItem.getScore());
        review.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(fileItem.getTime()),
                TimeZone.getDefault().toZoneId()));
        review.setSummary(fileItem.getSummary());
        review.setText(fileItem.getText());
        return review;
    }

    @Override
    public User getUser(FileItem fileItem) {
        return new User(fileItem.getUserId(), fileItem.getProfileName(), DEFAULT_PASSWORD);
    }
}
