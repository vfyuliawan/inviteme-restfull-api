package inviteme.restfull.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import inviteme.restfull.entiity.User;
import inviteme.restfull.enumiration.ApiEnum;
import inviteme.restfull.model.response.GetImageStorage;
import inviteme.restfull.model.response.ResponseImageStorage;
import inviteme.restfull.utility.Base64Utils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.HashMap;
import java.io.*;

@Slf4j
@Service
public class ImageUploadService {

    @Autowired
    private Base64Utils base64Utils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GetUserService getUserService;

    public GetImageStorage uploadImagetoStorage(String base64String) throws IOException {
        User user = getUserService.getUserLogin();
        MultipartFile thisImage = base64Utils.base64toFile3(base64String, user.getUsername());
        ;
        ResponseImageStorage responseUpload = base64Utils.uploadImage(thisImage, user.getUsername());
        String filename = responseUpload.getMessage().split(" ")[2];
        String fileDir = "image/" + user.getUsername() + "/" + filename;
        return GetImageStorage.builder().file(thisImage).fileDir(fileDir)
                .imageUrl(ApiEnum.getBaseURLStorage() + fileDir).message(responseUpload.getMessage()).build();
    }

    public ResponseImageStorage deleteImageStorage(String fileUrl) throws IOException {
        String directory = fileUrl.replace(ApiEnum.getBaseURLStorage(), "");
        String deleteUrl = ApiEnum.getDeleteURLUAT() + "?file=" + directory;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    deleteUrl,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readValue(response.getBody(), ResponseImageStorage.class);
            } else {
                throw new RuntimeException("Failed to delete the file. Status code: " + response.getStatusCode()+" message : "+response.getBody().toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete the file , message:"+e.toString());
        }
    }

}
