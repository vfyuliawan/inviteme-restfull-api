package inviteme.restfull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.Message;
import inviteme.restfull.entiity.Messages;
import inviteme.restfull.entiity.Projects;
import inviteme.restfull.model.request.GetMessageRequest;
import inviteme.restfull.model.request.MessagePostRequest;
import inviteme.restfull.model.response.MessageProjectResponse;
import inviteme.restfull.model.response.MessagesResponse;
import inviteme.restfull.repository.MessageProjectRepository;
import inviteme.restfull.repository.MessagesRepository;
import inviteme.restfull.repository.ProjectRepositry;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private MessageProjectRepository messageProjectRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ProjectRepositry projectRepositry;

    @Transactional(readOnly = true)
    public MessageProjectResponse getMessage(GetMessageRequest request) {
        validationService.validated(request);
        Optional<Message> optionalMessageProject = messageProjectRepository.findByProjectId(request.getIdProjects());

        if (!optionalMessageProject.isPresent()) {
            return MessageProjectResponse.builder()
                    .messagesRequest(Collections.emptyList())
                    .build();
        }

        Message messageProject = optionalMessageProject.get();
        List<Messages> listOfMessages = messagesRepository.findByMessage(messageProject);

        List<MessagesResponse> messagesResponseList = listOfMessages.stream()
                .map(item -> MessagesResponse.builder()
                        .messageId(item.getId())
                        .name(item.getName())
                        .text(item.getText())
                        .present(item.getPresent())
                        .build())
                .collect(Collectors.toList());
        return MessageProjectResponse.builder()
                .messagesRequest(messagesResponseList)
                .build();
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

    public Boolean postMessage(String projectId, MessagePostRequest request) {
        validationService.validated(projectId);
        // Message projectMessage = messageProjectRepository.findByProjectId(projectId)

        Projects projectById = projectRepositry.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Id Tidak Ditemukan"));

        Message projectMessage = new Message();
        projectMessage.setId(UUID.randomUUID().toString());
        projectMessage.setProject(projectById);
        projectMessage.setMessages(null);

        Message saveMessage = messageProjectRepository.save(projectMessage);

        Messages messages = new Messages();
        messages.setId(UUID.randomUUID().toString());
        messages.setMessage(saveMessage);
        messages.setText(request.getText());
        messages.setName(request.getName());
        messages.setPresent(request.getPresent());
        messagesRepository.save(messages);

        return true;

    }

}
