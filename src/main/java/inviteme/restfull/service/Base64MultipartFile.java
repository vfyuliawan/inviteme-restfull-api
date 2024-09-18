package inviteme.restfull.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Base64MultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;
    private final String username;

    public Base64MultipartFile(byte[] imgContent, String header, String username) {
        this.imgContent = imgContent;
        this.header = header;
        this.username = username;
    }

    @Override
    public String getName() {
        return "file-" + LocalDateTime.now();
    }

    public String getContentDir(){
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String formattedDate = formatter.format(currentDate);
        return "image/"+username+"/file-" + formattedDate + "." + header; 
    }

    @Override
    public String getOriginalFilename() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String formattedDate = formatter.format(currentDate);
        return "file-" + formattedDate + "." + header;
    }

    @Override
    public String getContentType() {
        return header.split(":")[1].split(";")[0]; // Extract content type from the base64 header
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream out = new FileOutputStream(dest)) {
            out.write(imgContent);
        }
    }
}
