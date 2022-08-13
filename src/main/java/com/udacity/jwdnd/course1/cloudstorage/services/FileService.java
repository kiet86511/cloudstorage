package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;

@Service
public class FileService {

	@Autowired
	private FileMapper fileMapper;
	
	public int saveFile(MultipartFile file, Integer userId) throws IOException {
		File fileSelected = fileMapper.selectByFileName(file.getOriginalFilename(), userId);
		if(fileSelected == null) {
			File fileToSave = new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), userId, file.getBytes());
			return fileMapper.insert(fileToSave);
		}
		return 0;
	}
	
	public void deleteFile(Integer fileId) {
		fileMapper.deleteById(fileId);
	}
	
	public File viewFile(Integer fileId) {
		return fileMapper.selectById(fileId);
	}
	
	public List<File> getFileList(Integer userId) {
		return fileMapper.selectAll(userId);
	}
}
