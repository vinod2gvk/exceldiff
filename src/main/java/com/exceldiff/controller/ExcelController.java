package com.exceldiff.controller;

import com.exceldiff.service.ExcelService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/excel")
public class ExcelController {

  @Autowired
  ExcelService excelServiceImpl;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @PostMapping("/upload") // //new annotation since 4.3
  public void singleFileUpload(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                 HttpServletResponse response) throws IOException {
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=result.xlsx");
    IOUtils.copy(excelServiceImpl.generateDifferenceReport(file1,file2), response.getOutputStream());
  }
}
