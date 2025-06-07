package com.project.demo.controller;

import com.project.demo.framework.TenantContext;
import com.project.demo.framework.TenantIdentifierResolver;
import com.project.demo.service.EventService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
    @Autowired
    EventService eventService;

    @Autowired
    Executor executor;

    @PostMapping("/{tenantId}")
    public ResponseEntity<?> upload(
            @PathVariable String tenantId,
            @RequestParam("files") MultipartFile[] files) {

        for (MultipartFile file : files) {
            try {
                byte[] fileBytes = file.getBytes();
                String fileName = file.getOriginalFilename();
                executor.execute(() -> {
                    TenantContext.setTenant(tenantId);

                    try (InputStream in = new ByteArrayInputStream(fileBytes)) {
                        eventService.importCSV(fileName, in);
                    }
                    catch (Exception ex){
                        log.error("upload(): file: " + fileName, ex);
                    }
                    finally {
                        TenantContext.clear();
                    }
                });
            }
            catch (Exception ex){
                log.error("upload(): file: " + file.getName(), ex);
            }
        }
        return ResponseEntity.accepted().body("Processing...");
    }
}

