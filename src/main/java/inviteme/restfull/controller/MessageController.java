package inviteme.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.fasterxml.jackson.databind.ObjectMapper;

import inviteme.restfull.model.request.GetMessageRequest;
import inviteme.restfull.model.request.MessagePostRequest;
import inviteme.restfull.model.response.MessageProjectResponse;
import inviteme.restfull.model.response.MessagesResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.MessageService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/message")

public class MessageController {

    @Autowired
    private MessageService messageService;

    // @Autowired
    // private ObjectMapper objectMapper;

    @GetMapping(path = "/getByProjectId", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MessageProjectResponse> getCurrentUser(@RequestParam String projectId) {
        GetMessageRequest request = GetMessageRequest.builder().idProjects(projectId).build();
        MessageProjectResponse messageProjectResponse = messageService.getMessage(request);

        return WebResponse.<MessageProjectResponse>builder()
                .result(messageProjectResponse)
                .message("success")
                .code("00")
                .build();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @DeleteMapping(path = "/deleteByIdMessage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> deleteMessage(@RequestParam String messageId) {
        Boolean messageProjectResponse = messageService.deleteMessage(messageId);

        if (messageProjectResponse) {
            WebResponse responseSuccess = WebResponse.<Boolean>builder()
                    .result(messageProjectResponse)
                    .message("Comment Berhasil di Hapus")
                    .code("00")
                    .build();

            return ResponseEntity.ok(responseSuccess);
        } else {
            WebResponse responseFailed = WebResponse.<Boolean>builder()
                    .result(messageProjectResponse)
                    .message("success")
                    .code("00")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseFailed);
        }

    }

    @PostMapping(path = "/postMessage", 
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<MessagesResponse> postMessage(
        @RequestParam String idProject,
            @RequestBody MessagePostRequest messageRequest) {
        log.info("Received raw request: {}", messageRequest);
        MessagesResponse messageProjectResponse = messageService.postMessage(idProject,
                messageRequest);
        return WebResponse.<MessagesResponse>builder()
                .result(messageProjectResponse)
                .code("00")
                .message("Success")
                .build();
    }

}
