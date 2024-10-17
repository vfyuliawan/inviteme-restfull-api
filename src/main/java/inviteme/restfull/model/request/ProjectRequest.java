package inviteme.restfull.model.request;

import java.util.List;

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

    private LocalDateTime countdown;

    private HeroRequest hero;

    private HomeRequest home;

    private CoverRequest cover;

    private ThemeRequest theme;

    private InfoAcaraRequest infoAcara;

    private BraidInfoRequest braidInfo;

    private StoryRequest story;

    private GaleryRequest galery;

    private GiftRequest gift;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HeroRequest {

        private String img;

        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HomeRequest {
        private String quotes;

        private String img;

        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CoverRequest {

        private String img;

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

        private String primaryColor;

        private String secondaryColor;

        private String textColor1;

        private String textColor2;




    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoAkadRequest {
        private String titleAkad;

        private String mapAkad;

        private String imgAkad;

        private String lokasiAkad;

        private LocalDateTime dateAkad;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoResepsiRequest {
        private String titleResepsi;

        private String mapResepsi;

        private String imgResepsi;

        private String lokasiResepsi;

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
        private String name;
        private String mom;
        private String dad;
        private String photo;
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryRequest {
        private List<StoriestRequest> stories;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoriestRequest {
        private String title;
        private String text;
        private String image;
        private LocalDateTime date;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GaleryRequest {
        private List<String> galeries;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GaleriesRequest {
        private String image;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GiftRequest {
        private List<GiftsRequest> gifts;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GiftsRequest {
        private String image;
        private String name;
        private String noRek;
    }
}
