package inviteme.restfull.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import inviteme.restfull.entiity.User;
import inviteme.restfull.enumiration.ApiEnum;
import inviteme.restfull.model.response.GetImageStorage;
import inviteme.restfull.model.response.ResponseImageStorage;
import inviteme.restfull.utility.Base64Utils;
import inviteme.restfull.utility.FileMultipartFile;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import java.nio.file.Files;


import java.util.Base64;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;

@Slf4j
@Service
public class ImageUploadService {

    @Autowired
    private Base64Utils base64Utils;

    @Autowired
    private GetUserService getUserService;

    public MultipartFile base64ToMultipartFile(String base64String) throws IOException {

        String username = getUserService.getUserLogin().getUsername();

        // Split the base64 string into header and content
        String[] base64Array = base64String.split(",");

        if (base64Array.length != 2) {
            throw new IllegalArgumentException("Invalid base64 format");
        }

        String base64Header = base64Array[0].trim();
        String base64Content = base64Array[1].replaceAll("\\s", "").trim(); // Clean the base64 content

        log.info("Base64 header: " + base64Header);

        // Decode base64 to bytes
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);

        // Convert to MultipartFile
        return new Base64MultipartFile(decodedBytes, base64Header, username);
    }

    public File saveBase64File(String base64File, String fileName) throws IOException {
        // Decode the base64 string to bytes (strip out the data URI scheme if present)
        byte[] decodedBytes = Base64.getDecoder().decode(base64File.split(",")[1]);

        // Ensure the upload folder exists (you can replace ApiEnum.getUploadDir() with
        // your actual path)
        File folder = new File(ApiEnum.getUploadDir());
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Create a new file in the upload folder with a clean path
        File outputFile = new File(folder, StringUtils.cleanPath(fileName));

        // Write the decoded bytes to the file
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decodedBytes);
        }

        // Return the saved file object
        return outputFile;
    }

    public MultipartFile saveBase64File2(String base64File, String fileName) throws IOException {
        // Decode the base64 string to bytes (strip out the data URI scheme if present)

        log.info(base64File.split(",")[0]);
        byte[] decodedBytes = Base64.getDecoder().decode(base64File.split(",")[1]);

        File folder = new File(ApiEnum.getUploadDir());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File outputFile = new File(folder, StringUtils.cleanPath(fileName));

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decodedBytes);
        }
        String contentType = Files.probeContentType(outputFile.toPath());

        return new FileMultipartFile(outputFile, contentType);
    }

    public GetImageStorage uploadImagetoStorage(String base64String) throws IOException {
        User user = getUserService.getUserLogin();
        MultipartFile thisImage = base64Utils.base64toFile3(base64String, user.getUsername());;
        ResponseImageStorage responseUpload = base64Utils.uploadImage(thisImage, user.getUsername());
        String filename = responseUpload.getMessage().split(" ")[2];
        String fileDir = "image/"+user.getUsername()+"/"+filename;
        return GetImageStorage.builder().file(thisImage).fileDir(fileDir).imageUrl(ApiEnum.getURLStorage()+fileDir).message(responseUpload.getMessage()).build();
    }

}
