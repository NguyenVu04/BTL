package com.project.backend.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket.BlobWriteOption;
import com.google.firebase.cloud.StorageClient;
import com.project.backend.exceptionhandler.ExceptionLog;

@Repository
public class BackendStorage {
    @Autowired
    private StorageClient storage;
    @Autowired
    private ExceptionLog exceptionLog;
    public boolean saveBlob(MultipartFile file, List<String> path) {
        StringBuilder sb = new StringBuilder();
        path.forEach(p -> sb.append(p).append("/"));
        sb.append(file.getOriginalFilename());
        try {
            storage.bucket()
                   .create(sb.toString(), 
                           file.getInputStream(), 
                           BlobWriteOption.doesNotExist());
            return true;
        } catch (Exception e) {
            exceptionLog.log(e);
            return false;
        }
    }
    public boolean deleteBlob(List<String> path) {
        Blob blob = this.getBlob(path);
        if (blob == null) {
            return false;
        } 
        blob.delete();
        return true;
    }
    @Nullable
    public Map.Entry<String, Resource> getFile(List<String> path) {
        StringBuilder sb = new StringBuilder();
        if (path == null || path.isEmpty()) {
            return null;
        }
        for (String p : path) {
            sb.append(p).append("/");
        }
        sb.deleteCharAt(sb.length() - 1);
        Blob blob = storage.bucket()
                           .get(sb.toString());
        try {
            return Map.entry(blob.getName(), new ByteArrayResource(blob.getContent()));
        } catch (Exception e) {
            exceptionLog.log(e, sb.toString());
            return null;
        }        
    }
    @Nullable
    public Blob getBlob(List<String> path) {
        StringBuilder sb = new StringBuilder();
        if (path == null || path.isEmpty()) {
            return null;
        }
        for (String p : path) {
            sb.append(p).append("/");
        }
        sb.deleteCharAt(sb.length() - 1);
        Blob blob = storage.bucket()
                           .get(sb.toString());
        return blob;
    }
    public boolean updateBlob(MultipartFile file, List<String> path) {
        StringBuilder sb = new StringBuilder();
        path.forEach(p -> sb.append(p).append("/"));
        sb.append(file.getOriginalFilename());
        try {
            storage.bucket()
                   .create(sb.toString(), 
                           file.getInputStream()); 
            return true;
        } catch (Exception e) {
            exceptionLog.log(e);
            return false;
        }
    }
}
