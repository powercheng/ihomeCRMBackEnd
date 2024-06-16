package com.ihomeCabinet.crm.controller;

import cn.hutool.json.JSONObject;
import com.ihomeCabinet.crm.tools.Tool;
import com.ihomeCabinet.crm.tools.response.Result;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileController {

    // Change this to your desired directory

    @PostMapping("/upload/{id}/{folder}")
    public Result uploadFiles(@RequestParam("file") MultipartFile file, @PathVariable Integer id, @PathVariable String folder) throws IOException {
        JSONObject res = new JSONObject();
        if (file.isEmpty()) {
            return Result.fail("empty file");
        }


            String path = this.getClass().getClassLoader().getResource("").getPath() + "static";
            // Save the file to the upload directory
            File destFile = new File(path + File.separator + id + File.separator + folder + File.separator + file.getOriginalFilename());
            file.transferTo(destFile);
            return Result.ok(res);

    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
