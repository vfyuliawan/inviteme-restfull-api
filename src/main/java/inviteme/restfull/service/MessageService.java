package inviteme.restfull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.Messages;
import inviteme.restfull.entiity.Projects;
import inviteme.restfull.model.request.GetMessageRequest;
import inviteme.restfull.model.request.MessagePostRequest;
import inviteme.restfull.model.response.MessageProjectResponse;
import inviteme.restfull.model.response.MessagesResponse;
import inviteme.restfull.repository.MessagesRepository;
import inviteme.restfull.repository.ProjectRepositry;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageService {
    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ProjectRepositry projectRepository;

    @Transactional(readOnly = true)
    public MessageProjectResponse getMessage(GetMessageRequest request) {
        validationService.validated(request);
        Projects projects = projectRepository.findById(request.getIdProjects()).orElseThrow();
        List<Messages> listMessages = messagesRepository.findByProject(projects);

        if (listMessages.size() < 1) {
            return MessageProjectResponse.builder()
                    .messagesRequest(Collections.emptyList())
                    .build();
        } else {
            List<MessagesResponse> messagesResponseList = 
            listMessages.stream().map(item -> MessagesResponse.builder()
            .name(item.getName())
            .messageId(item.getId())
            .text(item.getText())
            .present(item.getPresent())
            .build()).collect(Collectors.toList());

            return MessageProjectResponse.builder()
                    .messagesRequest(messagesResponseList)
                    .build();
        }

    }

    @Transactional
    public Boolean deleteMessage(String messageId) {
        validationService.validated(messageId);
        if (messagesRepository.existsById(messageId)) {
            messagesRepository.deleteById(messageId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
public MessagesResponse postMessage(String projectId, MessagePostRequest request) {
    validationService.validated(projectId);
    validationService.validated(request);

    Projects projectById = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Id Tidak Ditemukan"));

    log.info("projectId: {}", projectId);
    log.info("request: {}", request);

    Messages messages = new Messages();
    messages.setId(UUID.randomUUID().toString());
    messages.setProject(projectById);
    messages.setText(request.getText());
    messages.setName(request.getName());
    messages.setPresent(request.getPresent());
    Messages savedMessage = messagesRepository.save(messages);

    return MessagesResponse.builder()
            .messageId(savedMessage.getId())
            .name(savedMessage.getName())
            .text(savedMessage.getText())
            .present(savedMessage.getPresent())
            .build();
}


}
