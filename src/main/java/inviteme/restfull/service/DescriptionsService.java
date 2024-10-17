package inviteme.restfull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import inviteme.restfull.entiity.Descriptions;
import inviteme.restfull.entiity.Role;
import inviteme.restfull.entiity.User;
import inviteme.restfull.model.request.DescriptionAddRequest;
import inviteme.restfull.model.request.DescriptionsInquiryRequest;
import inviteme.restfull.model.response.DescriptionsInquiryResponse;
import inviteme.restfull.model.response.DescriptionsResponse;
import inviteme.restfull.model.response.PagingResponse;
import inviteme.restfull.repository.DescriptionsRepository;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import java.util.stream.*;
import java.util.*;;

@Service
@Slf4j
public class DescriptionsService {
    @Autowired
    private DescriptionsRepository descriptionsRepository;

    @Autowired
    private GetUserService getUserService;


    @Autowired
    private ValidationService validationService;


    public DescriptionsInquiryResponse descriptionsInquiry(DescriptionsInquiryRequest request) throws IOException{
        validationService.validated(request);

        Page<Descriptions> descriptions;
        if (request.getQuotes() == null || request.getQuotes().isEmpty()) {
            descriptions = descriptionsRepository
                    .OrderByCreatedAtDesc(PageRequest.of(request.getCurrentPage(), request.getSize()));
        } else {
            descriptions = descriptionsRepository.findByQuotesContainingOrderByCreatedAt(request.getQuotes(),
                    PageRequest.of(request.getCurrentPage(), request.getSize()));
        }

        List<DescriptionsResponse> descriptionsResponsePage;
        if (descriptions.getContent().isEmpty()) {
            descriptionsResponsePage = Collections.emptyList();
        } else {
            descriptionsResponsePage = descriptions.getContent().stream().map(item -> {
                DescriptionsResponse descriptionsResponse = new DescriptionsResponse();
                descriptionsResponse.setId(item.getId());
                descriptionsResponse.setQuotesBy(item.getQuotesBy());
                descriptionsResponse.setQuotes(item.getQuotes());
                descriptionsResponse.setCreatedAt(item.getCreatedAt());
                return descriptionsResponse;
            }).collect(Collectors.toList());
        }
        PagingResponse pagingResponse = PagingResponse.builder().currentPage(descriptions.getNumber())
                .size(descriptions.getSize()).build();

        return DescriptionsInquiryResponse.builder().paging(pagingResponse).descriptions(descriptionsResponsePage)
                .build();
    }

    public DescriptionsResponse addDescription(DescriptionAddRequest request) {
        validationService.validated(request);
        log.error("requetstbody", request);
        Descriptions saveDescriptions = new Descriptions();
        saveDescriptions.setId(UUID.randomUUID().toString());
        saveDescriptions.setCreatedAt(LocalDateTime.now());
        saveDescriptions.setQuotes(request.getQuotes());
        saveDescriptions.setQuotesBy(request.getQuotesBy());
        Descriptions saveToResp = descriptionsRepository.save(saveDescriptions);

        return DescriptionsResponse.builder().id(saveToResp.getId()).quotesBy(saveToResp.getQuotesBy())
                .quotes(saveToResp.getQuotes()).createdAt(saveToResp.getCreatedAt()).build();

    }

    public boolean deleteDescription(String descId) {
        validationService.validated(descId);
        if (descriptionsRepository.existsById(descId)) {
            descriptionsRepository.deleteById(descId);
            return true;
        } else {
            return false;
        }
    }

    public String editDescription(DescriptionAddRequest request, String descId) throws IOException {
        validationService.validated(request);
        try {
            User userLogin = getUserService.getUserLogin();
            if (userLogin.getRole() == Role.ADMIN) {
                Descriptions descriptions = descriptionsRepository.findById(descId).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID Descriptions Not Found"));
                if (Objects.nonNull(request.getQuotes())) {
                    descriptions.setQuotes(request.getQuotes());
                } else if (Objects.nonNull(request.getQuotesBy())) {
                    descriptions.setQuotesBy(request.getQuotesBy());
                }
                Descriptions descriptionsRes = descriptionsRepository.save(descriptions);
                if (descriptionsRes.getId() != null) {
                    return "Success";
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empty ID");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Forbiden Access Only User");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
