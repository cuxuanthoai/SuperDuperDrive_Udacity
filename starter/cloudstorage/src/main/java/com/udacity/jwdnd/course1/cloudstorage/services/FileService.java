package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileMapper files;

    public FileService(FileMapper mapper) {
        files = mapper;
    }

    public File fetch(File file) {
        return files.fetch(file);
    }

    public List<File> fetchAllByUserId(String UID) {
        return files.fetchAllByUserId(UID);
    }

    public void delete(File file) {
        files.remove(file);
    }

    public boolean isPresent(File file) {
        return files.locate(file) != null;
    }

    public void save(File file) {
        files.save(file);
    }

}
