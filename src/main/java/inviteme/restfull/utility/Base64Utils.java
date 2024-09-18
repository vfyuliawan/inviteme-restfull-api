package inviteme.restfull.utility;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import inviteme.restfull.enumiration.ApiEnum;
import inviteme.restfull.service.Base64MultipartFile;

import java.io.IOException;

import java.time.LocalDateTime;


@Service
public class Base64Utils {


   public byte[] decodeBase64String(String base64String){
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64String);
    }

    public MultipartFile base64toFile3(String base64String, String username) throws IOException {
        try {
            String base64Data = base64String;
            if (base64String.contains(",")) {
                base64Data = base64String.split(",")[1];
            }
            byte[] decodeBase64String = Base64.getDecoder().decode(base64Data);
            String fileFormat = base64String.split(",")[0].split("/")[1].split(";")[0].trim();
            String filePath = ApiEnum.getUploadDir() + "/" + LocalDateTime.now() + ".jpeg";
            MultipartFile writeBytesToFile = writeBytesToFile(decodeBase64String, filePath, fileFormat, username);

            return writeBytesToFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MultipartFile writeBytesToFile(byte[] bytes, String filePath , String header, String username) throws IOException{
        return new Base64MultipartFile(bytes, header, username);
    }

     public String uploadImage(MultipartFile image, String username) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("fileToUpload", image.getResource());
        body.add("username", username);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                ApiEnum.getUploadURLUAT(),
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return "File uploaded successfully!";
        } else {
            throw new RuntimeException("Failed to upload the file. Status code: " + response.getStatusCode());
        }
    }

}
