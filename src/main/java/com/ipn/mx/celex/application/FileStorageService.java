package com.ipn.mx.celex.application;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storePdf(MultipartFile file);

    Resource loadAsResource(String storedFileName);

    void delete(String storedFileName);
}
