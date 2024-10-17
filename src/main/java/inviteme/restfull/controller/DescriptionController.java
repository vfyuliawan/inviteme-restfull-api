package inviteme.restfull.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.model.request.DescriptionAddRequest;
import inviteme.restfull.model.request.DescriptionsInquiryRequest;
import inviteme.restfull.model.response.DescriptionsInquiryResponse;
import inviteme.restfull.model.response.DescriptionsResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.DescriptionsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DescriptionController {

    @Autowired
    private DescriptionsService descriptionsService;

    @GetMapping(path = "/descriptions/inquiry")
    public WebResponse<DescriptionsInquiryResponse> descInquiry(
        @RequestParam(required = false) String title,
            @RequestParam(required = true) int currentPage,
            @RequestParam(required = true) int pageSize
    )
            throws IOException {
        try {

            DescriptionsInquiryRequest request = new DescriptionsInquiryRequest();
            request.setQuotes(title);
            request.setSize(pageSize);
            request.setCurrentPage(currentPage);

            DescriptionsInquiryResponse descriptionsInquiry = descriptionsService.descriptionsInquiry(request);
            return WebResponse.<DescriptionsInquiryResponse>builder().code("00").message("success")
                    .result(descriptionsInquiry).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/descriptions/create")
    public WebResponse<DescriptionsResponse> createDesc(@RequestBody DescriptionAddRequest request)
            throws IOException {
        try {
            log.info("reqCreate: {}", request.getQuotes());
            DescriptionsResponse description = descriptionsService.addDescription(request);
            return WebResponse.<DescriptionsResponse>builder().code("00").message("success").result(description)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping(path = "/descriptions/delete")
    public WebResponse<Boolean> deleteDesc(@RequestParam String id) throws IOException{
        try {
            boolean deleteDescription = descriptionsService.deleteDescription(id);

            if (deleteDescription) {
              return  WebResponse.<Boolean>builder().message("Success").result(true).code("00").build();
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Gagal Dihapus");

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @PatchMapping(path = "/descriptions/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> updateDesc(@RequestBody DescriptionAddRequest reqBody, @RequestParam(required = false) String descId) throws IOException{
        try {
            log.error("desc id {}", descId);
            String editDescription = descriptionsService.editDescription(reqBody, descId);
            return WebResponse.<String>builder().code("00").message("Success").messageError("Success").result(editDescription).build();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }


}
