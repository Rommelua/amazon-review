package com.amazon.review.service;

import com.amazon.review.model.FileItem;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.User;

public interface FileItemParser {
    Product getProduct(FileItem fileItem);

    Review getReview(FileItem fileItem);

    User getUser(FileItem fileItem);
}
