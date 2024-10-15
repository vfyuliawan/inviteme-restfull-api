package inviteme.restfull.model.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {

    private String id;
    private String title;
    private LocalDateTime countdown;
    private LocalDateTime publishDate;
    private HeroResponse hero;
    private HomeResponse home;
    private CoverResponse cover;
    private ThemeResponse theme;
    private InfoAcaraResponse infoAcara;
    private BraidInfoResponse braidInfo;
    private StoryResponse story;
    private GaleryResponse galery;
    private GiftResponse gift;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HeroResponse {
        private String title;
        private String img;
        private LocalDateTime date;
        private Boolean isShow;
    }

    

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HomeResponse {
        private String title;
        private String quotes;
        private String img;
        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CoverResponse {

        private String title;

        private String img;

        private LocalDateTime date;

        private Boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ThemeResponse {

        private String slug;

        private String alamat;

        private String embeded;

        private ThemeExampleResponseV2 theme;

        private String music;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoAkadResponse {

        private String titleAkad;

        private String mapAkad;

        private String lokasiAkad;

        private LocalDateTime dateAkad;

        private String imageAkad;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoResepsiResponse {

        private String titleResepsi;

        private String mapResepsi;

        private String lokasiResepsi;

        private LocalDateTime dateResepsi;

        private String imageResepsi;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InfoAcaraResponse {
        private InfoAkadResponse akad;
        private InfoResepsiResponse resepsi;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MaleFemaleInfoResponse {
        private String name;
        private String image;
        private String mom;
        private String dad;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BraidInfoResponse {
        private MaleFemaleInfoResponse male;
        private MaleFemaleInfoResponse female;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoryResponse {
        private List<StoriesResponse> stories;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StoriesResponse {
        private String title;
        private String text;
        private String image;
        private LocalDateTime date;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GaleryResponse {
        private List<String> galeries;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GaleriesResponse {
        private String image;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GiftResponse {
        private List<GiftsResponse> gifts;
        private Boolean isShow;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GiftsResponse {
        private String image;
        private String name;
        private String noRek;
    }

}