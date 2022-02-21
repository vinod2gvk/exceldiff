package com.exceldiff.service.impl;

import com.exceldiff.helper.ExcelHelper;
import com.exceldiff.model.Employee;
import com.exceldiff.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {
  @Override
  public ByteArrayInputStream generateDifferenceReport(MultipartFile file1, MultipartFile file2) {
    List<Employee> infoFromHRRecord = getEmployees(file1);
    List<Employee> infoFromEmployeeRecord = getEmployees(file2);

    List<Employee> results = infoFromHRRecord.stream()
            .filter(x -> !infoFromEmployeeRecord.contains(x))
            .collect(Collectors.toList());

    return ExcelHelper.employeesToExcel(results);
  }
  private List<Employee> getEmployees(MultipartFile file) {
    try {
      return  ExcelHelper.excelToEmployees(file.getInputStream());
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }
}
