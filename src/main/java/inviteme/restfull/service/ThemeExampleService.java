package inviteme.restfull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inviteme.restfull.entiity.ThemeExample;
import inviteme.restfull.model.request.ThemeCreateRequest;
import inviteme.restfull.model.request.ThemeExampleInquiryRequest;
import inviteme.restfull.model.response.GetImageStorage;
import inviteme.restfull.model.response.PagingResponse;
import inviteme.restfull.model.response.ThemeExampleResponse;
import inviteme.restfull.model.response.ThemeExampleResponseInquiry;
import inviteme.restfull.repository.ThemeExampleRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThemeExampleService {
    @Autowired
    private ValidationService validationService;

    @Autowired
    private ThemeExampleRepository themeExampleRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Transactional(readOnly = true)
    public ThemeExampleResponseInquiry getThmeInquiry(ThemeExampleInquiryRequest request) throws IOException {
        validationService.validated(request);
        try {
            Page<ThemeExample> themeByName;
            if (request.getThemeName() == null || request.getThemeName().isEmpty()) {
                themeByName = themeExampleRepository.findAllByOrderByCreatedAtDesc(
                        PageRequest.of(request.getCurrentPage(), request.getSize()));
            } else {
                themeByName = themeExampleRepository.findByNameContaining(request.getThemeName(),
                        PageRequest.of(request.getCurrentPage(), request.getSize()));
            }
            List<ThemeExampleResponse> themeInquiry = themeByName.getContent().stream().map(item -> {
                ThemeExampleResponse theme = ThemeExampleResponse.builder().themeName(item.getName())
                        .primaryColor(item.getPrimaryColor()).secondaryColor(item.getSecondaryColor()).id(item.getId())
                        .bgImage(item.getBgimg()).fgImage(item.getFgimg())
                        .createdAt(item.getCreatedAt())
                        .build();
                return theme;
            }).collect(Collectors.toList());
            PagingResponse pageRespponse = PagingResponse.builder().currentPage(themeByName.getNumber())
                    .size(themeByName.getSize()).totalPage(themeByName.getTotalPages()).build();

            return ThemeExampleResponseInquiry
                    .builder()
                    .listTheme(themeInquiry)
                    .pageable(pageRespponse)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    public ThemeExampleResponse createTheme(ThemeCreateRequest request) throws IOException {
        validationService.validated(request);
        try {
            ThemeExample themeExample = new ThemeExample();
            themeExample.setId(UUID.randomUUID().toString());
            if (Objects.nonNull(request.getThemeName())) {
                themeExample.setName(request.getThemeName());
            }

            if (Objects.nonNull(request.getPrimaryColor())) {
                themeExample.setPrimaryColor(request.getPrimaryColor());
            }

            if (Objects.nonNull(request.getSecondaryColor())) {
                themeExample.setSecondaryColor(request.getSecondaryColor());
            }

            if (Objects.nonNull(request.getBgImage())) {
                GetImageStorage bgImageStorage = imageUploadService.uploadImagetoStorage(request.getBgImage());
                themeExample.setBgimg(bgImageStorage.getImageUrl());
            }

            if (Objects.nonNull(request.getFgImage())) {
                GetImageStorage fgImageStorage = imageUploadService.uploadImagetoStorage(request.getFgImage());
                themeExample.setFgimg(fgImageStorage.getImageUrl());
            }
            themeExample.setCreatedAt(LocalDateTime.now());
            ThemeExample saveTheme = themeExampleRepository.save(themeExample);
            ThemeExampleResponse response = ThemeExampleResponse.builder().id(saveTheme.getId())
                    .themeName(saveTheme.getName())
                    .primaryColor(saveTheme.getPrimaryColor())
                    .secondaryColor(saveTheme.getSecondaryColor())
                    .bgImage(saveTheme.getBgimg())
                    .fgImage(saveTheme.getFgimg())
                    .createdAt(saveTheme.getCreatedAt())
                    .build();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public Boolean deleteTheme(String id) throws IOException {
        try {
            if (themeExampleRepository.existsById(id)) {
                themeExampleRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

}
