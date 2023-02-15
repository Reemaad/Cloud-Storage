package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) {
        String result = null;

        if (fileService.isFileNameAlreadyUploaded(file.getOriginalFilename(), authentication.getName())) {
            result = "uploadError";
        } else {
            int rowNumber = fileService.insertFile(file, authentication.getName());
            if (rowNumber < 0) {
                result = "error";
            } else {
                result = "success";
            }
        }
        return "redirect:result?" + result + "=true";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam("fileId") Integer fileId) {
        String result = null;

        boolean isFileDeletedSuccessfully = fileService.deleteFile(fileId);

        if (isFileDeletedSuccessfully) {
            result = "success";
        } else {
            result = "error";
        }

        return "redirect:result?" + result + "=true";
    }

    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("fileId") Integer fileId, HttpServletResponse response) {
        File file = fileService.getFile(fileId);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + file.getFileName();
        response.setHeader(headerKey, headerValue);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(file.getFileData());
            outputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
