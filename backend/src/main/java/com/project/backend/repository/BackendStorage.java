package com.project.backend.repository;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public boolean saveBlob(InputStream file, List<String> path) {
        StringBuilder sb = new StringBuilder();
        path.forEach(p -> sb.append(p).append("/"));
        try {
            storage.bucket()
                   .create(sb.toString(), 
                           file, 
                           BlobWriteOption.doesNotExist());
            return true;
        } catch (Exception e) {
            exceptionLog.log(e);
            return false;
        }
    }
    public boolean saveBlob(InputStream file, String ...path) {
        StringBuilder sb = new StringBuilder();
        for (String p : path) {
            sb.append(p).append("/");
        }
        try {
            storage.bucket()
                   .create(sb.toString(), 
                           file, 
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
    public boolean deleteBlob(String ...path) {
        List<String> list = List.of(path);
        Blob blob = this.getBlob(list);
        if (blob == null) {
            return false;
        }
        blob.delete();
        return true;
    }
    @Nullable
    public Blob getBlob(List<String> path) {
        StringBuilder sb = new StringBuilder();
        path.forEach(p -> sb.append(p).append("/"));
        return storage.bucket()
                      .get(sb.toString());
    }
    @Nullable
    public Blob getBlob(InputStream file, String ...path) {
        StringBuilder sb = new StringBuilder();
        for (String p : path) {
            sb.append(p).append("/");
        }
        Blob blob = storage.bucket()
                           .get(sb.toString());
        return blob;
    }
    public boolean updateBlob(InputStream file, List<String> path) {
        StringBuilder sb = new StringBuilder();
        path.forEach(p -> sb.append(p).append("/"));
        try {
            storage.bucket()
                   .create(sb.toString(), 
                           file); 
            return true;
        } catch (Exception e) {
            exceptionLog.log(e);
            return false;
        }
    }
    public boolean updateBlob(InputStream file, String ...path) {
        StringBuilder sb = new StringBuilder();
        for (String p : path) {
            sb.append(p).append("/");
        }
        try {
            storage.bucket()
                   .create(sb.toString(), 
                           file); 
            return true;
        } catch (Exception e) {
            exceptionLog.log(e);
            return false;
        }
    }
}
