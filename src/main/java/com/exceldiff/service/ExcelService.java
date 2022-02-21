package com.exceldiff.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Service
public interface ExcelService {

  ByteArrayInputStream generateDifferenceReport(MultipartFile file1, MultipartFile file2);
}
