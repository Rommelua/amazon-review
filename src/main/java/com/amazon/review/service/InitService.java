package com.amazon.review.service;

import com.amazon.review.model.FileItem;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.Role;
import com.amazon.review.model.User;
import com.amazon.review.repository.ProductRepository;
import com.amazon.review.repository.ReviewRepository;
import com.amazon.review.repository.RoleRepository;
import com.amazon.review.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InitService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CsvFileService csvFileService;
    private final FileItemParser fileItemParser;
    private Role adminRole;
    private Role userRole;

    public InitService(ProductRepository productRepository, ReviewRepository reviewRepository,
                       RoleRepository roleRepository, UserRepository userRepository,
                       CsvFileService csvFileService, FileItemParser fileItemParser) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.csvFileService = csvFileService;
        this.fileItemParser = fileItemParser;
    }

    @PostConstruct
    public void init() {
        initRoles();
        csvFileService.loadFile();
        List<FileItem> fileItems = csvFileService.getFileItems();
        storeDataToDB(fileItems);
    }

    private void initRoles() {
        adminRole = new Role(Role.RoleName.ADMIN);
        adminRole = roleRepository.save(adminRole);
        userRole = new Role(Role.RoleName.USER);
        userRole = roleRepository.save(userRole);
    }

    private void storeDataToDB(List<FileItem> fileItems) {
        Set<String> userIds = new HashSet<>();
        Set<String> productIds = new HashSet<>();
        for (FileItem fileItem : fileItems) {
            User user = fileItemParser.getUser(fileItem);
            user.setRoles(Set.of(userRole));
            if (userIds.contains(user.getUserId())) {
                user = userRepository.findByUserId(user.getUserId());
            } else {
                user = userRepository.save(user);
                userIds.add(user.getUserId());
            }
            Product product = fileItemParser.getProduct(fileItem);
            if (productIds.contains(product.getProductId())) {
                product = productRepository.findByProductId(product.getProductId());
            } else {
                product = productRepository.save(product);
                productIds.add(product.getProductId());
            }
            Review review = fileItemParser.getReview(fileItem);
            review.setUser(user);
            review.setProduct(product);
            reviewRepository.save(review);
        }
    }
}
