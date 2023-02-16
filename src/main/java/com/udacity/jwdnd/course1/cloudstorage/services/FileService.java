package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public boolean isFileNameAlreadyUploaded(String fileName, String username) {
        User user = userMapper.getUser(username);
        return fileMapper.getFileByFileNameAndUserId(fileName, user.getUserId()) != null;
    }

    public List<File> getUserFiles(String username) {
        User user = userMapper.getUser(username);
        return fileMapper.getUserFiles(user.getUserId());
    }

    public int insertFile(MultipartFile file, String username) {
        User user = userMapper.getUser(username);
        try {
            return fileMapper.insertFile(new File(null, file.getOriginalFilename(), file.getContentType(), Long.toString(file.getSize()), user.getUserId(), file.getBytes()));
        } catch (IOException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    public boolean deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

}
