package inviteme.restfull.model.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {

    @NotBlank
    private String title;

    private HeroRequest hero;

    private HomeRequest home;

    private CoverRequest cover;

    private ThemeRequest theme;

    private InfoAcaraRequest infoAcara;

    private BraidInfoRequest braidInfo;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HeroRequest {
        @NotBlank
        private String title;

        private MultipartFile img;

        private LocalDateTime date;

        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HomeRequest {
        @NotBlank
        private String title;

        @NotBlank
        private String quotes;

        private MultipartFile img;

        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CoverRequest {
        @NotBlank
        private String title;

        private MultipartFile img;

        private LocalDateTime date;

        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ThemeRequest {
        @NotBlank
        private String slug;

        @NotBlank
        private String alamat;

        @NotBlank
        private String embeded;

        @NotBlank
        private String theme;

        @NotBlank
        private String music;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoAkadRequest {
        @NotBlank
        private String titleAkad;

        @NotBlank
        private String mapAkad;

        @NotBlank
        private String lokasiAkad;

        @NotBlank
        private LocalDateTime dateAkad;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoResepsiRequest {
        @NotBlank
        private String titleResepsi;

        @NotBlank
        private String mapResepsi;

        @NotBlank
        private String lokasiResepsi;

        @NotBlank
        private LocalDateTime dateResepsi;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoAcaraRequest {
        private InfoAkadRequest akad;
        private InfoResepsiRequest resepsi;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MaleFemaleInfoRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String mom;
        @NotBlank
        private String dad;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BraidInfoRequest {
        private MaleFemaleInfoRequest male;
        private MaleFemaleInfoRequest female;
        private Boolean isShow;
    }
}
