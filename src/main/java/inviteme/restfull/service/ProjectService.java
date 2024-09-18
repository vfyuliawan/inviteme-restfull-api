package inviteme.restfull.service;

import inviteme.restfull.entiity.Acara;
import inviteme.restfull.entiity.BraidInfo;
import inviteme.restfull.entiity.Cover;
import inviteme.restfull.entiity.Galeries;
import inviteme.restfull.entiity.Galery;
import inviteme.restfull.entiity.Gift;
import inviteme.restfull.entiity.Gifts;
import inviteme.restfull.entiity.Hero;
import inviteme.restfull.entiity.Home;
import inviteme.restfull.entiity.Projects;
import inviteme.restfull.entiity.Stories;
import inviteme.restfull.entiity.Story;
import inviteme.restfull.entiity.Theme;
import inviteme.restfull.entiity.User;
import inviteme.restfull.enumiration.ApiEnum;
import inviteme.restfull.model.request.ProjectInquiryRequest;
import inviteme.restfull.model.request.ProjectRequest;
import inviteme.restfull.model.request.ProjectRequest.GaleryRequest;
import inviteme.restfull.model.request.ProjectRequest.GiftRequest;
import inviteme.restfull.model.request.ProjectRequest.GiftsRequest;
import inviteme.restfull.model.request.ProjectRequest.StoriestRequest;
import inviteme.restfull.model.request.ProjectRequest.StoryRequest;
import inviteme.restfull.model.response.GetImageStorage;
import inviteme.restfull.model.response.GetProjectResponse;
import inviteme.restfull.model.response.PagingResponse;
import inviteme.restfull.model.response.ProjectInquiryResponse;
import inviteme.restfull.model.response.ProjectResponse;
import inviteme.restfull.model.response.ProjectResponse.BraidInfoResponse;
import inviteme.restfull.model.response.ProjectResponse.GaleryResponse;
import inviteme.restfull.model.response.ProjectResponse.GiftResponse;
import inviteme.restfull.model.response.ProjectResponse.GiftsResponse;
import inviteme.restfull.model.response.ProjectResponse.InfoAcaraResponse;
import inviteme.restfull.model.response.ProjectResponse.InfoAkadResponse;
import inviteme.restfull.model.response.ProjectResponse.InfoResepsiResponse;
import inviteme.restfull.model.response.ProjectResponse.MaleFemaleInfoResponse;
import inviteme.restfull.model.response.ProjectResponse.StoriesResponse;
import inviteme.restfull.model.response.ProjectResponse.StoryResponse;
import inviteme.restfull.model.response.ProjectResponse.ThemeResponse;
import inviteme.restfull.repository.BraidInfoRepository;
import inviteme.restfull.repository.CoverRepository;
import inviteme.restfull.repository.GaleriesRepository;
import inviteme.restfull.repository.GaleryRepository;
import inviteme.restfull.repository.GiftRepository;
import inviteme.restfull.repository.GiftsRepository;
import inviteme.restfull.repository.HeroRepository;
import inviteme.restfull.repository.HomeRepository;
import inviteme.restfull.repository.InfoAcaraRepository;
import inviteme.restfull.repository.ProjectRepositry;
import inviteme.restfull.repository.StoriesRepository;
import inviteme.restfull.repository.StoryRepository;
import inviteme.restfull.repository.ThemeRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ProjectService {

        @Autowired
        private ProjectRepositry projectRepository;

        @Autowired
        private HeroRepository heroRepository;

        @Autowired
        private HomeRepository homeRepository;

        @Autowired
        private CoverRepository coverRepository;

        @Autowired
        private ValidationService validationService;

        @Autowired
        private ThemeRepository themeRepository;

        @Autowired
        private InfoAcaraRepository infoAcaraRepository;

        @Autowired
        private BraidInfoRepository braidInfoRepository;

        @Autowired
        private StoryRepository storyRepository;

        @Autowired
        private StoriesRepository storiesRepository;

        @Autowired
        private GetUserService getUserService;

        @Autowired
        private GaleryRepository galeryRepository;

        @Autowired
        private GaleriesRepository galeriesRepository;

        @Autowired
        private GiftRepository giftRepository;

        @Autowired
        private GiftsRepository giftsRepository;

        @Autowired
        private ImageUploadService imageUploadService;

        @Transactional
        public ProjectResponse createProject(
                        ProjectRequest request,
                        MultipartFile heroImage,
                        MultipartFile homeImage,
                        MultipartFile coverImage,
                        MultipartFile akadImage,
                        MultipartFile resepsiImage,
                        MultipartFile maleInfoImage,
                        MultipartFile femaleInfoImage) throws IOException {
                validationService.validated(request);

                User user = getUserService.getUserLogin();

                // Create and save Projects entity
                Projects projects = new Projects();
                projects.setId(UUID.randomUUID().toString());
                projects.setUser(user);
                projects.setTitle(request.getTitle());
                projectRepository.save(projects);

                // Create and save Hero entity
                Hero hero = storeHero(projects, request, heroImage, user.getUsername());
                Home home = storeHome(projects, request, homeImage, user.getUsername());
                Cover cover = storeCover(projects, request, coverImage, user.getUsername());
                Theme theme = storTheme(projects, request);
                Acara acara = storeAcara(projects, request, akadImage, resepsiImage, user.getUsername());
                BraidInfo braidInfo = storeBraidInfo(projects, request, homeImage, femaleInfoImage, user.getUsername());

                // Update Projects entity with the Hero reference
                projects.setHero(hero);
                projects.setHome(home);
                projects.setCover(cover);
                projects.setTheme(theme);
                projects.setAcara(acara);
                projects.setBraidInfo(braidInfo);
                projectRepository.save(projects);

                return toProjectResponse(projects, user.getUsername());
        }

        private Hero storeHero(Projects projects, ProjectRequest request, MultipartFile heroImage, String username)
                        throws IOException {
                Hero hero = new Hero();
                hero.setId(UUID.randomUUID().toString());
                hero.setTitle(request.getTitle());
                hero.setProject(projects);
                hero.setDate(request.getCountdown());
                hero.setIsShow(request.getHero().getIsShow());

                // Save image to the folder
                String imageName = saveImage(heroImage, username);
                hero.setImg(imageName);
                heroRepository.save(hero);

                return hero;
        }

        private Home storeHome(Projects projects, ProjectRequest request, MultipartFile homeImage, String username)
                        throws IOException {
                Home home = new Home();
                home.setId(UUID.randomUUID().toString());
                home.setTitle(request.getTitle());
                home.setQuotes(request.getHome().getQuotes());
                home.setProject(projects);
                home.setIsShow(request.getHome().getIsShow());

                String saveImage = saveImage(homeImage, username);
                home.setImg(saveImage);
                homeRepository.save(home);

                return home;
        }

        public Cover storeCover(Projects projects, ProjectRequest request, MultipartFile coverImage, String username)
                        throws IOException {
                Cover cover = new Cover();
                cover.setTitle(request.getTitle());
                cover.setDate(request.getCountdown());
                cover.setId(UUID.randomUUID().toString());
                cover.setIsShow(request.getCover().getIsShow());
                cover.setProject(projects);

                String saveImageCover = saveImage(coverImage, username);
                cover.setImg(saveImageCover);

                coverRepository.save(cover);

                return cover;

        }

        public Theme storTheme(Projects projects, ProjectRequest request)
                        throws IOException {
                Theme theme = new Theme();
                theme.setId(UUID.randomUUID().toString());
                theme.setProject(projects);
                theme.setSlug(request.getTheme().getSlug());
                theme.setAlamat(request.getTheme().getAlamat());
                theme.setEmbeded(request.getTheme().getEmbeded());
                theme.setTheme(request.getTheme().getTheme());
                theme.setMusic(request.getTheme().getMusic());

                Theme save = themeRepository.save(theme);
                return save;
        }

        public Acara storeAcara(Projects projects,
                        ProjectRequest request,
                        MultipartFile akadImage,
                        MultipartFile resepsiImage,
                        String username)
                        throws IOException {

                log.info("REQUEST ACARA {}", request.getInfoAcara().getAkad());
                Acara acara = new Acara();
                acara.setId(UUID.randomUUID().toString());
                acara.setProject(projects);
                acara.setTitleAkad(request.getInfoAcara().getAkad().getTitleAkad());
                acara.setLokasiAkad(request.getInfoAcara().getAkad().getLokasiAkad());
                acara.setMapAkad(request.getInfoAcara().getAkad().getMapAkad());
                acara.setDateAkad(request.getInfoAcara().getAkad().getDateAkad());

                acara.setTitleResepsi(request.getInfoAcara().getResepsi().getTitleResepsi());
                acara.setLokasiResepsi(request.getInfoAcara().getResepsi().getLokasiResepsi());
                acara.setMapResepsi(request.getInfoAcara().getResepsi().getMapResepsi());
                acara.setDateResepsi(request.getInfoAcara().getResepsi().getDateResepsi());

                String resepsiImg = saveImage(resepsiImage, username);
                String akadImg = saveImage(akadImage, username);

                acara.setImgResepsi(resepsiImg);
                acara.setImgAkad(akadImg);

                Acara save = infoAcaraRepository.save(acara);
                return save;
        }

        public BraidInfo storeBraidInfo(Projects projects,
                        ProjectRequest request,
                        MultipartFile maleImage,
                        MultipartFile femaleImage,
                        String username)
                        throws IOException {

                log.info("REQUEST ACARA {}", request.getBraidInfo());

                BraidInfo braidInfo = new BraidInfo();
                braidInfo.setId(UUID.randomUUID().toString());
                braidInfo.setProject(projects);
                braidInfo.setShow(request.getBraidInfo().getIsShow());

                braidInfo.setMaleName(request.getBraidInfo().getMale().getName());
                braidInfo.setMaleMom(request.getBraidInfo().getMale().getMom());
                braidInfo.setMaleDad(request.getBraidInfo().getMale().getDad());

                braidInfo.setFemaleName(request.getBraidInfo().getFemale().getName());
                braidInfo.setFemaleMom(request.getBraidInfo().getFemale().getMom());
                braidInfo.setFemaleDad(request.getBraidInfo().getFemale().getDad());

                String maleImg = saveImage(maleImage, username);
                String femaleImg = saveImage(femaleImage, username);

                braidInfo.setMaleImg(maleImg);
                braidInfo.setFemaleImg(femaleImg);

                BraidInfo save = braidInfoRepository.save(braidInfo);
                return save;
        }

        private String saveImage(MultipartFile image, String username) throws IOException {
                if (image.isEmpty()) {
                        throw new IllegalArgumentException("Image file is empty");
                }

                // Create the upload directory if it does not exist
                File uploadDir = new File(ApiEnum.getUploadDir());
                if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                }

                // Create user's directory inside uploadDir if it does not exist
                File userDir = new File(uploadDir, username);
                if (!userDir.exists()) {
                        userDir.mkdirs();
                }

                // Generate a unique file name
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(userDir.getAbsolutePath(), fileName);
                Files.write(filePath, image.getBytes());

                return fileName;
        }

        private ProjectResponse toProjectResponse(Projects projects, String username) {
                Hero hero = projects.getHero();
                ProjectResponse.HeroResponse heroResponse = ProjectResponse.HeroResponse.builder()
                                .title(hero.getTitle())
                                .date(hero.getDate())
                                .img(ApiEnum.getMainUrl() + "/api/images/" + username + "/" + hero.getImg())
                                .isShow(hero.getIsShow())
                                .build();

                Home home = projects.getHome();
                ProjectResponse.HomeResponse homeResponse = ProjectResponse.HomeResponse.builder()
                                .title(home.getTitle())
                                .quotes(home.getQuotes())
                                .img(ApiEnum.getMainUrl() + "/api/images/" + username + "/" + home.getImg())
                                .isShow(home.getIsShow())
                                .build();

                Cover cover = projects.getCover();
                ProjectResponse.CoverResponse coverResponse = ProjectResponse.CoverResponse.builder()
                                .title(cover.getTitle())
                                .date(cover.getDate())
                                .img(ApiEnum.getMainUrl() + "/api/images/" + username + "/" + cover.getImg())
                                .isShow(cover.getIsShow())
                                .build();

                ThemeResponse themeResponse = ProjectResponse.ThemeResponse.builder()
                                .slug(projects.getTheme().getSlug())
                                .alamat(projects.getTheme().getAlamat())
                                .theme(projects.getTheme().getTheme())
                                .music(projects.getTheme().getMusic())
                                .embeded(projects.getTheme().getEmbeded())
                                .build();

                InfoAkadResponse akad = InfoAkadResponse.builder()
                                .titleAkad(projects.getAcara().getTitleAkad())
                                .lokasiAkad(projects.getAcara().getLokasiAkad())
                                .dateAkad(projects.getAcara().getDateAkad())
                                .mapAkad(projects.getAcara().getMapAkad())
                                .imageAkad(ApiEnum.getMainUrl() + "/api/images/" + username + "/"
                                                + projects.getAcara().getImgAkad())
                                .build();

                InfoResepsiResponse resepsi = InfoResepsiResponse.builder()
                                .titleResepsi(projects.getAcara().getTitleResepsi())
                                .lokasiResepsi(projects.getAcara().getLokasiResepsi())
                                .dateResepsi(projects.getAcara().getDateResepsi())
                                .mapResepsi(projects.getAcara().getMapResepsi())
                                .imageResepsi(
                                                ApiEnum.getMainUrl() + "/api/images/" + username + "/"
                                                                + projects.getAcara().getImgResepsi())
                                .build();

                InfoAcaraResponse infoAcaraResponse = ProjectResponse.InfoAcaraResponse.builder()
                                .akad(akad)
                                .resepsi(resepsi)
                                .build();

                MaleFemaleInfoResponse male = MaleFemaleInfoResponse.builder()
                                .name(projects.getBraidInfo().getMaleName())
                                .mom(projects.getBraidInfo().getMaleMom())
                                .dad(projects.getBraidInfo().getMaleDad())
                                .image(projects.getBraidInfo().getMaleImg())
                                .build();

                MaleFemaleInfoResponse female = MaleFemaleInfoResponse.builder()
                                .name(projects.getBraidInfo().getFemaleName())
                                .mom(projects.getBraidInfo().getFemaleMom())
                                .dad(projects.getBraidInfo().getFemaleDad())
                                .image(projects.getBraidInfo().getFemaleImg())
                                .build();

                BraidInfoResponse braidInfoResponse = ProjectResponse.BraidInfoResponse.builder()
                                .male(male)
                                .female(female)
                                .isShow(projects.getBraidInfo().isShow())
                                .build();

                return ProjectResponse.builder()
                                .title(projects.getTitle())
                                .hero(heroResponse)
                                .home(homeResponse)
                                .cover(coverResponse)
                                .theme(themeResponse)
                                .infoAcara(infoAcaraResponse)
                                .braidInfo(braidInfoResponse)
                                .build();
        }

        @Transactional
        public ProjectResponse createNewProject(
                        ProjectRequest request) throws IOException {
                validationService.validated(request);
                User user = getUserService.getUserLogin();

                // Create and save Projects entity
                Projects projects = new Projects();
                projects.setId(UUID.randomUUID().toString());
                projects.setPublishDate(LocalDateTime.now());
                projects.setUser(user);
                projects.setTitle(request.getTitle());
                projects.setCountdown(request.getCountdown());
                projectRepository.save(projects);

                // Create and save entity
                Hero hero = storeNewHero(projects, request, user.getUsername());
                Home home = storeNewHome(projects, request, user.getUsername());
                Cover cover = storeNewCover(projects, request, user.getUsername());
                Theme theme = storeNewTheme(projects, request);
                Acara acara = storeNewAcara(projects, request, user.getUsername());
                BraidInfo braidInfo = storeNewBraidInfo(projects, request, user.getUsername());
                Story story = storeNewStory(projects, request, user.getUsername());
                Galery galery = storeNewGalery(projects, request, user.getUsername());
                Gift gift = storeNewGift(projects, request, user.getUsername());

                // Update Projects entity with the Hero reference
                projects.setHero(hero);
                projects.setHome(home);
                projects.setCover(cover);
                projects.setTheme(theme);
                projects.setAcara(acara);
                projects.setBraidInfo(braidInfo);
                projects.setStory(story);
                projects.setGalery(galery);
                projects.setGift(gift);
                projectRepository.save(projects);

                return toNewProjectResponse(projects);
        }

        private Hero storeNewHero(Projects projects, ProjectRequest request, String username)
                        throws IOException {
                Hero hero = new Hero();
                hero.setId(UUID.randomUUID().toString());
                hero.setTitle(request.getTitle());
                hero.setProject(projects);
                hero.setDate(request.getCountdown());
                hero.setIsShow(request.getHero().getIsShow());
                GetImageStorage heroImage = imageUploadService.uploadImagetoStorage(request.getHero().getImg());
                hero.setImg(heroImage.getImageUrl());
                heroRepository.save(hero);
                return hero;
        }

        private Home storeNewHome(Projects projects, ProjectRequest request, String username)
                        throws IOException {
                Home home = new Home();
                home.setId(UUID.randomUUID().toString());
                home.setTitle(request.getTitle());
                home.setQuotes(request.getHome().getQuotes());
                home.setProject(projects);
                home.setIsShow(request.getHome().getIsShow());
                GetImageStorage homeImage = imageUploadService.uploadImagetoStorage(request.getHome().getImg());
                home.setImg(homeImage.getImageUrl());
                homeRepository.save(home);
                return home;
        }

        public Cover storeNewCover(Projects projects, ProjectRequest request, String username)
                        throws IOException {
                Cover cover = new Cover();
                cover.setTitle(request.getTitle());
                cover.setDate(request.getCountdown());
                cover.setId(UUID.randomUUID().toString());
                cover.setIsShow(request.getCover().getIsShow());
                cover.setProject(projects);
                GetImageStorage coverImage = imageUploadService.uploadImagetoStorage(request.getCover().getImg());
                cover.setImg(coverImage.getImageUrl());
                coverRepository.save(cover);
                return cover;

        }

        public Theme storeNewTheme(Projects projects, ProjectRequest request)
                        throws IOException {
                Theme theme = new Theme();
                theme.setId(UUID.randomUUID().toString());
                theme.setProject(projects);
                theme.setSlug(request.getTheme().getSlug());
                theme.setAlamat(request.getTheme().getAlamat());
                theme.setEmbeded(request.getTheme().getEmbeded());
                theme.setTheme(request.getTheme().getTheme());
                theme.setMusic(request.getTheme().getMusic());

                Theme save = themeRepository.save(theme);
                return save;
        }

        public Acara storeNewAcara(Projects projects,
                        ProjectRequest request,
                        String username)
                        throws IOException {

                log.info("REQUEST ACARA {}", request.getInfoAcara().getAkad());
                Acara acara = new Acara();
                acara.setId(UUID.randomUUID().toString());
                acara.setProject(projects);
                acara.setTitleAkad(request.getInfoAcara().getAkad().getTitleAkad());
                acara.setLokasiAkad(request.getInfoAcara().getAkad().getLokasiAkad());
                acara.setMapAkad(request.getInfoAcara().getAkad().getMapAkad());
                acara.setDateAkad(request.getInfoAcara().getAkad().getDateAkad());
                GetImageStorage akadImage = imageUploadService
                                .uploadImagetoStorage(request.getInfoAcara().getAkad().getImgAkad());
                acara.setImgAkad(akadImage.getImageUrl());

                acara.setTitleResepsi(request.getInfoAcara().getResepsi().getTitleResepsi());
                acara.setLokasiResepsi(request.getInfoAcara().getResepsi().getLokasiResepsi());
                acara.setMapResepsi(request.getInfoAcara().getResepsi().getMapResepsi());
                acara.setDateResepsi(request.getInfoAcara().getResepsi().getDateResepsi());
                GetImageStorage resepsiImage = imageUploadService
                                .uploadImagetoStorage(request.getInfoAcara().getResepsi().getImgResepsi());
                acara.setImgResepsi(resepsiImage.getImageUrl());
                Acara save = infoAcaraRepository.save(acara);
                return save;
        }

        public BraidInfo storeNewBraidInfo(Projects projects,
                        ProjectRequest request,
                        String username)
                        throws IOException {

                log.info("REQUEST ACARA {}", request.getBraidInfo());

                BraidInfo braidInfo = new BraidInfo();
                braidInfo.setId(UUID.randomUUID().toString());
                braidInfo.setProject(projects);
                braidInfo.setShow(request.getBraidInfo().getIsShow());

                braidInfo.setMaleName(request.getBraidInfo().getMale().getName());
                braidInfo.setMaleMom(request.getBraidInfo().getMale().getMom());
                braidInfo.setMaleDad(request.getBraidInfo().getMale().getDad());
                GetImageStorage malePhoto = imageUploadService
                                .uploadImagetoStorage(request.getBraidInfo().getMale().getPhoto());
                braidInfo.setMaleImg(malePhoto.getImageUrl());

                braidInfo.setFemaleName(request.getBraidInfo().getFemale().getName());
                braidInfo.setFemaleMom(request.getBraidInfo().getFemale().getMom());
                braidInfo.setFemaleDad(request.getBraidInfo().getFemale().getDad());
                GetImageStorage femaleImage = imageUploadService
                                .uploadImagetoStorage(request.getBraidInfo().getFemale().getPhoto());
                braidInfo.setFemaleImg(femaleImage.getImageUrl());

                BraidInfo save = braidInfoRepository.save(braidInfo);
                return save;
        }

        public Story storeNewStory(Projects projects,
                        ProjectRequest request,
                        String username)
                        throws IOException {

                log.info("REQUEST ACARA {}", request.getStory());

                Story story = new Story();
                story.setId(UUID.randomUUID().toString());
                story.setProject(projects);
                story.setShow(request.getStory().getIsShow());
                Story savedStory = storyRepository.save(story);

                List<Stories> listStories = request.getStory().getStories().stream().map(item -> {
                        Stories stories = new Stories();
                        stories.setId(UUID.randomUUID().toString());
                        stories.setStory(savedStory);
                        stories.setTitle(item.getTitle());
                        stories.setText(item.getText());
                        try {
                                GetImageStorage listStoryImage = imageUploadService
                                                .uploadImagetoStorage(item.getImage());
                                stories.setImg(listStoryImage.getImageUrl());
                        } catch (Exception e) {
                                stories.setImg("");
                                throw new RuntimeException(e.toString());

                        }
                        stories.setDate(item.getDate());
                        return stories;
                }).collect(Collectors.toList());

                storiesRepository.saveAll(listStories);

                savedStory.setStories(listStories);
                Story storyResponse = storyRepository.save(savedStory);

                return storyResponse;
        }

        public Galery storeNewGalery(Projects projects,
                        ProjectRequest request,
                        String username)
                        throws IOException {

                log.info("REQUEST ACARA {}", request.getGalery());

                Galery galery = new Galery();
                galery.setId(UUID.randomUUID().toString());
                galery.setProject(projects);
                galery.setShow(request.getGalery().getIsShow());
                Galery savedGalery = galeryRepository.save(galery);

                List<Galeries> listGaleries = request.getGalery().getGaleries().stream().map(item -> {
                        Galeries galeries = new Galeries();
                        galeries.setId(UUID.randomUUID().toString());
                        galeries.setGalery(savedGalery);
                        try {
                                GetImageStorage itemGaleryImage = imageUploadService.uploadImagetoStorage(item);
                                galeries.setImg(itemGaleryImage.getImageUrl());
                        } catch (Exception e) {
                                galeries.setImg("");
                                throw new RuntimeException(e.toString());
                        }
                        return galeries;
                }).collect(Collectors.toList());

                galeriesRepository.saveAll(listGaleries);

                savedGalery.setGaleries(listGaleries);
                Galery galeryResponse = galeryRepository.save(savedGalery);

                return galeryResponse;
        }

        public Gift storeNewGift(Projects projects,
                        ProjectRequest request,
                        String username)
                        throws IOException {
                Gift gift = new Gift();
                gift.setId(UUID.randomUUID().toString());
                gift.setProject(projects);
                gift.setShow(request.getGift().getIsShow());
                Gift saveGift = giftRepository.save(gift);

                List<Gifts> listGifts = request.getGift().getGifts().stream().map(item -> {
                        Gifts gifts = new Gifts();
                        gifts.setId(UUID.randomUUID().toString());
                        gifts.setGift(saveGift);
                        gifts.setImage(item.getImage());
                        gifts.setName(item.getName());
                        gifts.setNorek(item.getNoRek());
                        return gifts;
                }).collect(Collectors.toList());

                List<Gifts> saveAllGifts = giftsRepository.saveAll(listGifts);
                saveGift.setGifts(saveAllGifts);
                Gift giftReesponse = giftRepository.save(saveGift);
                return giftReesponse;
        }

        private ProjectResponse toNewProjectResponse(Projects projects) {
                Hero hero = projects.getHero();
                ProjectResponse.HeroResponse heroResponse = ProjectResponse.HeroResponse.builder()
                                .title(hero.getTitle())
                                .date(hero.getDate())
                                .img(hero.getImg())
                                .isShow(hero.getIsShow())
                                .build();

                Home home = projects.getHome();
                ProjectResponse.HomeResponse homeResponse = ProjectResponse.HomeResponse.builder()
                                .title(home.getTitle())
                                .quotes(home.getQuotes())
                                .img(home.getImg())
                                .isShow(home.getIsShow())
                                .build();

                Cover cover = projects.getCover();
                ProjectResponse.CoverResponse coverResponse = ProjectResponse.CoverResponse.builder()
                                .title(cover.getTitle())
                                .date(cover.getDate())
                                .img(cover.getImg())
                                .isShow(cover.getIsShow())
                                .build();

                ThemeResponse themeResponse = ProjectResponse.ThemeResponse.builder()
                                .slug(projects.getTheme().getSlug())
                                .alamat(projects.getTheme().getAlamat())
                                .theme(projects.getTheme().getTheme())
                                .music(projects.getTheme().getMusic())
                                .embeded(projects.getTheme().getEmbeded())
                                .build();

                InfoAkadResponse akad = InfoAkadResponse.builder()
                                .titleAkad(projects.getAcara().getTitleAkad())
                                .lokasiAkad(projects.getAcara().getLokasiAkad())
                                .dateAkad(projects.getAcara().getDateAkad())
                                .mapAkad(projects.getAcara().getMapAkad())
                                .imageAkad(projects.getAcara().getImgAkad())
                                .build();

                InfoResepsiResponse resepsi = InfoResepsiResponse.builder()
                                .titleResepsi(projects.getAcara().getTitleResepsi())
                                .lokasiResepsi(projects.getAcara().getLokasiResepsi())
                                .dateResepsi(projects.getAcara().getDateResepsi())
                                .mapResepsi(projects.getAcara().getMapResepsi())
                                .imageResepsi(
                                                projects.getAcara().getImgResepsi())
                                .build();

                InfoAcaraResponse infoAcaraResponse = ProjectResponse.InfoAcaraResponse.builder()
                                .akad(akad)
                                .resepsi(resepsi)
                                .build();

                MaleFemaleInfoResponse male = MaleFemaleInfoResponse.builder()
                                .name(projects.getBraidInfo().getMaleName())
                                .mom(projects.getBraidInfo().getMaleMom())
                                .dad(projects.getBraidInfo().getMaleDad())
                                .image(projects.getBraidInfo().getMaleImg())
                                .build();

                MaleFemaleInfoResponse female = MaleFemaleInfoResponse.builder()
                                .name(projects.getBraidInfo().getFemaleName())
                                .mom(projects.getBraidInfo().getFemaleMom())
                                .dad(projects.getBraidInfo().getFemaleDad())
                                .image(projects.getBraidInfo().getFemaleImg())
                                .build();

                BraidInfoResponse braidInfoResponse = ProjectResponse.BraidInfoResponse.builder()
                                .male(male)
                                .female(female)
                                .isShow(projects.getBraidInfo().isShow())
                                .build();

                List<StoriesResponse> listStories = projects.getStory().getStories().stream().map(item -> {
                        StoriesResponse storiesResponse = StoriesResponse.builder()
                                        .title(item.getTitle())
                                        .text(item.getText())
                                        .date(item.getDate())
                                        .image(item.getImg())
                                        .build();

                        return storiesResponse;
                }).collect(Collectors.toList());

                StoryResponse storyResponse = ProjectResponse.StoryResponse.builder()
                                .isShow(projects.getStory().isShow())
                                .stories(listStories)
                                .build();

                List<String> listGaleries = projects.getGalery().getGaleries().stream().map(item -> {
                        String img = item.getImg();
                        return img;
                }).collect(Collectors.toList());

                GaleryResponse galeryResponse = GaleryResponse.builder()
                                .isShow(projects.getGalery().isShow())
                                .galeries(listGaleries)
                                .build();

                List<GiftsResponse> listGifts = projects.getGift().getGifts().stream().map(item -> {
                        GiftsResponse giftsResponse = GiftsResponse.builder()
                                        .image(item.getImage())
                                        .name(item.getName())
                                        .noRek(item.getNorek())
                                        .build();
                        return giftsResponse;
                }).collect(Collectors.toList());

                GiftResponse giftResponse = GiftResponse.builder()
                                .gifts(listGifts)
                                .build();

                return ProjectResponse.builder()
                                .title(projects.getTitle() != null ? projects.getTitle() : "")
                                .countdown(projects.getCountdown())
                                .publishDate(projects.getPublishDate())
                                .hero(heroResponse != null ? heroResponse : null)
                                .home(homeResponse != null ? homeResponse : null)
                                .cover(coverResponse != null ? coverResponse : null)
                                .theme(themeResponse != null ? themeResponse : null)
                                .infoAcara(infoAcaraResponse != null ? infoAcaraResponse : null)
                                .braidInfo(braidInfoResponse != null ? braidInfoResponse : null)
                                .story(storyResponse != null ? storyResponse : null)
                                .galery(galeryResponse != null ? galeryResponse : null)
                                .gift(giftResponse != null ? giftResponse : null)
                                .build();
        }

        @Transactional(readOnly = true)
        public ProjectInquiryResponse projectInquiry(ProjectInquiryRequest request) {
                User user = getUserService.getUserLogin();

                Page<Projects> projectPage;
                validationService.validated(request);

                if (request.getTitle() == null || request.getTitle().isEmpty()) {
                        projectPage = projectRepository.findByUserOrderByPublishDateDesc(
                                        user, PageRequest.of(request.getCurrentPage(), request.getSize()));
                } else {
                        projectPage = projectRepository.findByTitleContainingAndUserOrderByPublishDateDesc(
                                        request.getTitle(), user,
                                        PageRequest.of(request.getCurrentPage(), request.getSize()));
                }

                List<GetProjectResponse> listProject = projectPage.getContent().stream().map(item -> {
                        ThemeResponse themeResponse = new ThemeResponse();
                        themeResponse.setAlamat(item.getTheme().getAlamat());
                        themeResponse.setEmbeded(item.getTheme().getEmbeded());
                        themeResponse.setMusic(item.getTheme().getMusic());
                        themeResponse.setSlug(item.getTheme().getSlug());
                        themeResponse.setTheme(item.getTheme().getTheme());

                        return GetProjectResponse.builder()
                                        .title(item.getTitle())
                                        .id(item.getId())
                                        .username(item.getUser().getUsername())
                                        .date(item.getCountdown())
                                        .publishDate(item.getPublishDate())
                                        .theme(themeResponse)
                                        .build();
                }).collect(Collectors.toList());

                // Create the paging response
                PagingResponse pagingResponse = PagingResponse.builder()
                                .currentPage(projectPage.getNumber())
                                .size(projectPage.getSize())
                                .totalPage(projectPage.getTotalPages())
                                .build();

                // Create the final response
                return ProjectInquiryResponse.builder()
                                .paging(pagingResponse)
                                .projects(listProject)
                                .build();
        }

        @Transactional(readOnly = true)
        public ProjectInquiryResponse getMyProject(ProjectInquiryRequest request) {

                try {
                        User userLogin = getUserService.getUserLogin();

                        Page<Projects> projectPage = projectRepository
                                        .findByTitleContainingAndUserOrderByPublishDateDesc(request.getTitle(),
                                                        userLogin,
                                                        PageRequest.of(request.getCurrentPage(), request.getSize()));
                        List<GetProjectResponse> listProject = projectPage.getContent().stream().map(item -> {
                                ThemeResponse themeResponse = new ThemeResponse();
                                themeResponse.setAlamat(item.getTheme().getAlamat());
                                themeResponse.setEmbeded(item.getTheme().getEmbeded());
                                themeResponse.setMusic(item.getTheme().getMusic());
                                themeResponse.setSlug(item.getTheme().getSlug());
                                themeResponse.setTheme(item.getTheme().getTheme());

                                return GetProjectResponse.builder()
                                                .title(item.getTitle())
                                                .id(item.getId())
                                                .username(item.getUser().getUsername())
                                                .publishDate(item.getPublishDate())
                                                .date(item.getCountdown())
                                                .theme(themeResponse)
                                                .build();
                        }).collect(Collectors.toList());

                        PagingResponse pagingResponse = PagingResponse.builder()
                                        .currentPage(projectPage.getNumber())
                                        .size(projectPage.getSize())
                                        .totalPage(projectPage.getTotalPages())
                                        .build();

                        // Create the final response
                        return ProjectInquiryResponse.builder()
                                        .paging(pagingResponse)
                                        .projects(listProject)
                                        .build();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                }

        }

        @Transactional(readOnly = true)
        public ProjectResponse getProjectById(String id) {
                try {
                        Projects project = projectRepository.findById(id).orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
                        return toNewProjectResponse(project);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        @Transactional(readOnly = true)
        public ProjectResponse getProjectBySlugAndTheme(String slug) {
                try {
                        Projects project = projectRepository.findByThemeSlug(slug).orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
                        return toNewProjectResponse(project);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }

        }

        public ProjectResponse updateProject(
                        String projectId,
                        ProjectRequest request) throws IOException {
                try {
                        validationService.validated(request);
                        User user = getUserService.getUserLogin();

                        Projects projects = projectRepository.findById(projectId)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                        "Project Id Not Found"));
                        if (!projects.getUser().getToken().equals(user.getToken())) {
                                throw new SecurityException("User not authorized to update this project");
                        }
                        projects.setTitle(request.getTitle());
                        projects.setCountdown(request.getCountdown());

                        Hero hero = updateHero(projects, request);
                        Home home = updateHome(projects, request);
                        Cover cover = updateCover(projects, request);
                        Theme theme = updateTheme(projects, request);
                        Acara acara = updateAcara(projects, request);
                        BraidInfo braidInfo = updateBraidInfo(projects, request);
                        Story story = updateStory(projects, request);
                        Galery galery = updateGalery(projects, request);
                        Gift gift = updateGift(projects, request);

                        // Update Projects entity with the updated references
                        projects.setHero(hero);
                        projects.setHome(home);
                        projects.setCover(cover);
                        projects.setTheme(theme);
                        projects.setAcara(acara);
                        projects.setBraidInfo(braidInfo);
                        projects.setStory(story);
                        projects.setGalery(galery);
                        projects.setGift(gift);
                        projectRepository.save(projects);

                        return toNewProjectResponse(projects);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }

        }

        private Hero updateHero(Projects projects, ProjectRequest request) {
                Hero hero = heroRepository.findByProject(projects).orElseThrow(null);
                if (Objects.nonNull(request.getHero().getIsShow())) {
                        hero.setIsShow(request.getHero().getIsShow());
                }
                if (Objects.nonNull(request.getHero().getImg())) {
                        hero.setImg(request.getHero().getImg());
                }
                Hero save = heroRepository.save(hero);
                return save;
        }

        private Home updateHome(Projects projects, ProjectRequest request) {
                Home home = homeRepository.findByProject(projects).orElseThrow(null);
                if (Objects.nonNull(request.getHome().getImg())) {
                        home.setImg(request.getHome().getImg());
                }
                if (Objects.nonNull(request.getHome().getIsShow())) {
                        home.setIsShow(request.getHome().getIsShow());
                }
                if (Objects.nonNull(request.getHome().getQuotes())) {
                        home.setQuotes(request.getHome().getQuotes());
                }
                Home save = homeRepository.save(home);
                return save;
        }

        private Cover updateCover(Projects projects, ProjectRequest request) {
                Cover cover = coverRepository.findByProject(projects).orElseThrow(null);
                if (Objects.nonNull(request.getCover().getImg())) {
                        cover.setImg(request.getCover().getImg());
                }
                if (Objects.nonNull(request.getCover().getIsShow())) {
                        cover.setIsShow(request.getCover().getIsShow());
                }
                Cover save = coverRepository.save(cover);
                return save;
        }

        private Theme updateTheme(Projects projects, ProjectRequest request) {
                Theme theme = themeRepository.findByProject(projects).orElseThrow(null);
                if (Objects.nonNull(request.getTheme().getAlamat())) {
                        theme.setAlamat(request.getTheme().getAlamat());
                }
                if (Objects.nonNull(request.getTheme().getEmbeded())) {
                        theme.setEmbeded(request.getTheme().getEmbeded());
                }
                if (Objects.nonNull(request.getTheme().getMusic())) {
                        theme.setMusic(request.getTheme().getMusic());
                }
                if (Objects.nonNull(request.getTheme().getSlug())) {
                        theme.setSlug(request.getTheme().getSlug());
                }
                if (Objects.nonNull(request.getTheme().getTheme())) {
                        theme.setTheme(request.getTheme().getTheme());
                }

                Theme save = themeRepository.save(theme);
                return save;
        }

        private Acara updateAcara(Projects projects, ProjectRequest request) {
                Acara acara = infoAcaraRepository.findByProject(projects).orElseThrow(null);
                if (Objects.nonNull(request.getInfoAcara().getAkad().getTitleAkad())) {
                        acara.setTitleAkad(request.getInfoAcara().getAkad().getTitleAkad());
                }
                if (Objects.nonNull(request.getInfoAcara().getAkad().getDateAkad())) {
                        acara.setDateAkad(request.getInfoAcara().getAkad().getDateAkad());
                }
                if (Objects.nonNull(request.getInfoAcara().getAkad().getImgAkad())) {
                        acara.setImgAkad(request.getInfoAcara().getAkad().getImgAkad());
                }
                if (Objects.nonNull(request.getInfoAcara().getAkad().getMapAkad())) {
                        acara.setMapAkad(request.getInfoAcara().getAkad().getMapAkad());
                }
                if (Objects.nonNull(request.getInfoAcara().getAkad().getLokasiAkad())) {
                        acara.setLokasiAkad(request.getInfoAcara().getAkad().getLokasiAkad());
                }

                if (Objects.nonNull(request.getInfoAcara().getResepsi().getTitleResepsi())) {
                        acara.setTitleResepsi(request.getInfoAcara().getResepsi().getTitleResepsi());
                }
                if (Objects.nonNull(request.getInfoAcara().getResepsi().getDateResepsi())) {
                        acara.setDateResepsi(request.getInfoAcara().getResepsi().getDateResepsi());
                }
                if (Objects.nonNull(request.getInfoAcara().getResepsi().getImgResepsi())) {
                        acara.setImgResepsi(request.getInfoAcara().getResepsi().getImgResepsi());
                }
                if (Objects.nonNull(request.getInfoAcara().getResepsi().getMapResepsi())) {
                        acara.setMapResepsi(request.getInfoAcara().getResepsi().getMapResepsi());
                }
                if (Objects.nonNull(request.getInfoAcara().getResepsi().getLokasiResepsi())) {
                        acara.setLokasiResepsi(request.getInfoAcara().getResepsi().getLokasiResepsi());
                }
                Acara save = infoAcaraRepository.save(acara);
                return save;

        }

        private BraidInfo updateBraidInfo(Projects projects, ProjectRequest request) {
                BraidInfo braidInfo = braidInfoRepository.findByProject(projects).orElseThrow(null);
                if (Objects.nonNull(request.getBraidInfo().getIsShow())) {
                        braidInfo.setShow(request.getBraidInfo().getIsShow());
                }
                if (Objects.nonNull(request.getBraidInfo().getMale().getDad())) {
                        braidInfo.setMaleDad(request.getBraidInfo().getMale().getDad());
                }
                if (Objects.nonNull(request.getBraidInfo().getMale().getMom())) {
                        braidInfo.setMaleMom(request.getBraidInfo().getMale().getMom());
                }
                if (Objects.nonNull(request.getBraidInfo().getMale().getName())) {
                        braidInfo.setMaleName(request.getBraidInfo().getMale().getName());
                }
                if (Objects.nonNull(request.getBraidInfo().getMale().getPhoto())) {
                        braidInfo.setMaleImg(request.getBraidInfo().getMale().getPhoto());
                }

                if (Objects.nonNull(request.getBraidInfo().getFemale().getDad())) {
                        braidInfo.setFemaleDad(request.getBraidInfo().getFemale().getDad());
                }
                if (Objects.nonNull(request.getBraidInfo().getFemale().getMom())) {
                        braidInfo.setFemaleMom(request.getBraidInfo().getFemale().getMom());
                }
                if (Objects.nonNull(request.getBraidInfo().getFemale().getName())) {
                        braidInfo.setFemaleName(request.getBraidInfo().getFemale().getName());
                }
                if (Objects.nonNull(request.getBraidInfo().getFemale().getPhoto())) {
                        braidInfo.setFemaleImg(request.getBraidInfo().getFemale().getPhoto());
                }
                BraidInfo save = braidInfoRepository.save(braidInfo);
                return save;
        }

        private Story updateStory(Projects projects, ProjectRequest request) {
                Story story = storyRepository.findByProject(projects)
                                .orElseThrow(() -> new IllegalArgumentException("Story not found for the project"));
                // Update Story fields
                if (Objects.nonNull(request.getStory().getIsShow())) {
                        story.setShow(request.getStory().getIsShow());
                }
                // Update related Stories entities
                List<Stories> existingStories = storiesRepository.findByStory(story);
                StoryRequest storyRequest = request.getStory();
                List<StoriestRequest> storyDetailsRequests = storyRequest.getStories();
                // Collect the updated or new Stories
                List<Stories> updatedStories = IntStream.range(0, storyDetailsRequests.size())
                                .mapToObj(i -> {
                                        StoriestRequest storyDetailsRequest = storyDetailsRequests.get(i);
                                        Stories stories;

                                        if (i < existingStories.size()) {
                                                stories = existingStories.get(i);
                                        } else {
                                                stories = new Stories();
                                                stories.setId(UUID.randomUUID().toString());
                                                stories.setStory(story);
                                                stories.setDate(storyDetailsRequest.getDate());
                                                stories.setText(storyDetailsRequest.getText());
                                                stories.setImg(storyDetailsRequest.getImage());
                                                stories.setTitle(storyDetailsRequest.getTitle());
                                        }

                                        updateStoryDetails(stories, storyDetailsRequest);
                                        return stories;
                                })
                                .collect(Collectors.toList());
                // Save all updated or new Stories
                storiesRepository.saveAll(updatedStories);
                // Remove any extra existing Stories
                if (storyDetailsRequests.size() < existingStories.size()) {
                        List<Stories> extraStories = existingStories.subList(storyDetailsRequests.size(),
                                        existingStories.size());
                        storiesRepository.deleteAll(extraStories);
                }
                return storyRepository.save(story);
        }

        private void updateStoryDetails(Stories stories, StoriestRequest storyDetailsRequest) {
                if (Objects.nonNull(storyDetailsRequest.getTitle())) {
                        stories.setTitle(storyDetailsRequest.getTitle());
                }
                if (Objects.nonNull(storyDetailsRequest.getText())) {
                        stories.setText(storyDetailsRequest.getText());
                }
                if (Objects.nonNull(storyDetailsRequest.getImage())) {
                        stories.setImg(storyDetailsRequest.getImage());
                }
                if (Objects.nonNull(storyDetailsRequest.getDate())) {
                        stories.setDate(storyDetailsRequest.getDate());
                }
        }

        private Galery updateGalery(Projects projects, ProjectRequest request) {
                Galery galery = galeryRepository.findByProject(projects)
                                .orElseThrow(() -> new IllegalArgumentException("Galery not found for the project"));

                // Update Galery fields
                if (Objects.nonNull(request.getGalery().getIsShow())) {
                        galery.setShow(request.getGalery().getIsShow());
                }

                List<Galeries> existingGaleries = galeriesRepository.findByGalery(galery);
                GaleryRequest galeryRequest = request.getGalery();

                for (int i = 0; i < galeryRequest.getGaleries().size(); i++) {
                        String galerieString = galeryRequest.getGaleries().get(i);

                        if (i < existingGaleries.size()) {
                                Galeries galeries = existingGaleries.get(i);
                                updateGaleries(galeries, galerieString);
                                galeriesRepository.save(galeries);
                        } else {
                                Galeries newGaleries = new Galeries();
                                newGaleries.setId(UUID.randomUUID().toString());
                                newGaleries.setGalery(galery);
                                updateGaleries(newGaleries, galerieString);
                                galeriesRepository.save(newGaleries);
                        }
                }

                // Remove any extra existing Galeries
                if (galeryRequest.getGaleries().size() < existingGaleries.size()) {
                        for (int i = galeryRequest.getGaleries().size(); i < existingGaleries.size(); i++) {
                                galeriesRepository.delete(existingGaleries.get(i));
                        }
                }

                return galeryRepository.save(galery);
        }

        private void updateGaleries(Galeries galeries, String item) {
                if (item != null) {
                        galeries.setImg(item);
                }
        }

        private Gift updateGift(Projects projects, ProjectRequest request) {
                Gift gift = giftRepository.findByProject(projects).orElseThrow();
                GiftRequest giftRequest = request.getGift();
                if (Objects.nonNull(giftRequest.getIsShow())) {
                        gift.setShow(giftRequest.getIsShow());
                }

                List<Gifts> existingGifts = giftsRepository.findByGift(gift);
                for (int i = 0; i < giftRequest.getGifts().size(); i++) {
                        GiftsRequest giftsRequest = giftRequest.getGifts().get(i);

                        if (i < existingGifts.size()) {
                                Gifts gifts = existingGifts.get(i);
                                updateGifts(gifts, giftsRequest);
                                giftsRepository.save(gifts);
                        } else {
                                Gifts newGifts = new Gifts();
                                newGifts.setId(UUID.randomUUID().toString());
                                newGifts.setGift(gift);
                                updateGifts(newGifts, giftsRequest);
                                giftsRepository.save(newGifts);
                        }
                }
                // Handle removal of extra stories if necessary
                if (giftRequest.getGifts().size() < existingGifts.size()) {
                        for (int i = giftRequest.getGifts().size(); i < existingGifts.size(); i++) {
                                giftsRepository.delete(existingGifts.get(i));
                        }
                }
                return giftRepository.save(gift);
        }

        private void updateGifts(Gifts gifts, GiftsRequest giftsRequest) {
                if (Objects.nonNull(giftsRequest.getImage())) {
                        gifts.setImage(giftsRequest.getImage());
                }
                if (Objects.nonNull(giftsRequest.getName())) {
                        gifts.setName(giftsRequest.getName());
                }
                if (Objects.nonNull(giftsRequest.getNoRek())) {
                        gifts.setNorek(giftsRequest.getNoRek());
                }
        }

        public String deleteProject(String id) throws Exception {
                try {
                        if (projectRepository.existsById(id)) {
                                projectRepository.deleteById(id);
                                return "Success";
                        } else {
                                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
                        }
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }

        }

}
