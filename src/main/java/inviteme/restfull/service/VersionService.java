package inviteme.restfull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import inviteme.restfull.entiity.Version;
import inviteme.restfull.model.request.VersionUpdateRequest;
import inviteme.restfull.model.response.VersionResponse;
import inviteme.restfull.repository.VersionRepository;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private ValidationService validationService;

    public VersionResponse cekVersion() {
        Version version = versionRepository.findById("001")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Version Not Found"));
        return VersionResponse.builder()
                .versionId(version.getId())
                .currentVersion(version.getVersionActive())
                .build();
    }

    public VersionResponse editVersion(VersionUpdateRequest request) {
        validationService.validated(request);
        Version version = versionRepository.findById("001")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Version Not Found"));
        version.setVersionActive(request.getNextVersion());

        Version versionUpdate = versionRepository.save(version);

        return VersionResponse.builder()
                .versionId(versionUpdate.getId())
                .currentVersion(versionUpdate.getVersionActive())
                .build();
    }
}
