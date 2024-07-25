package inviteme.restfull.controller;

import org.springframework.web.bind.annotation.RestController;

import inviteme.restfull.model.request.UserUpdateRequest;
import inviteme.restfull.model.request.VersionUpdateRequest;
import inviteme.restfull.model.response.VersionResponse;
import inviteme.restfull.model.response.WebResponse;
import inviteme.restfull.service.VersionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")

public class VersionController {

    @Autowired
    private VersionService versionService;

    @GetMapping(path = "/version/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<VersionResponse> getVersion() {
        VersionResponse cekVersion = versionService.cekVersion();
        return WebResponse.<VersionResponse>builder()
                .code("00")
                .message("success")
                .result(cekVersion)
                .build();
    }

    @PatchMapping(path = "/version/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<VersionResponse> updateVersion(@RequestBody VersionUpdateRequest request) {
        VersionResponse editVersion = versionService.editVersion(request);
        return WebResponse.<VersionResponse>builder()
                .message("success")
                .code("00")
                .result(editVersion)            
                .build();
    }

}
