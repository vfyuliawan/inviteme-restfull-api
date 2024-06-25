package inviteme.restfull.service;

import inviteme.restfull.entiity.Acara;
import inviteme.restfull.entiity.Cover;
import inviteme.restfull.entiity.Hero;
import inviteme.restfull.entiity.Home;
import inviteme.restfull.entiity.Projects;
import inviteme.restfull.entiity.Theme;
import inviteme.restfull.entiity.User;
import inviteme.restfull.enumiration.ApiEnum;
import inviteme.restfull.model.request.ProjectRequest;
import inviteme.restfull.model.response.ProjectResponse;
import inviteme.restfull.model.response.ProjectResponse.InfoAcaraResponse;
import inviteme.restfull.model.response.ProjectResponse.InfoAkadResponse;
import inviteme.restfull.model.response.ProjectResponse.InfoResepsiResponse;
import inviteme.restfull.model.response.ProjectResponse.ThemeResponse;
import inviteme.restfull.repository.CoverRepository;
import inviteme.restfull.repository.HeroRepository;
import inviteme.restfull.repository.HomeRepository;
import inviteme.restfull.repository.InfoAcaraRepository;
import inviteme.restfull.repository.ProjectRepositry;
import inviteme.restfull.repository.ThemeRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepositry projectRepository;

    @Autowired
    private AuthService authService;

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

    @Transactional
    public ProjectResponse createProject(String token,
            ProjectRequest request,
            MultipartFile heroImage,
            MultipartFile homeImage,
            MultipartFile coverImage,
            MultipartFile akadImage,
            MultipartFile resepsiImage) throws IOException {
        validationService.validated(request);

        User user = authService.cekUserByToken(token);

        // Create and save Projects entity
        Projects projects = new Projects();
        projects.setId(UUID.randomUUID().toString());
        projects.setUser(user);
        projects.setTitle(request.getTitle());
        projects.setHero(null); // initially set to null
        projectRepository.save(projects);

        // Create and save Hero entity
        Hero hero = storeHero(projects, request, heroImage, user.getUsername());

        Home home = storeHome(projects, request, homeImage, user.getUsername());

        Cover cover = storeCover(projects, request, coverImage, user.getUsername());

        Theme theme = storTheme(projects, request);

        Acara acara = storeAcara(projects, request, akadImage, resepsiImage, user.getUsername());

        // Update Projects entity with the Hero reference
        projects.setHero(hero);
        projects.setHome(home);
        projects.setCover(cover);
        projects.setTheme(theme);
        projects.setAcara(acara);
        projectRepository.save(projects);

        return toProjectResponse(projects, user.getUsername());
    }

    private Hero storeHero(Projects projects, ProjectRequest request, MultipartFile heroImage, String username)
            throws IOException {
        Hero hero = new Hero();
        hero.setId(UUID.randomUUID().toString());
        hero.setTitle(request.getHero().getTitle());
        hero.setProject(projects);
        hero.setDate(request.getHero().getDate());
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
        cover.setTitle(request.getCover().getTitle());
        cover.setDate(request.getCover().getDate());
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
                .imageAkad(ApiEnum.getMainUrl() + "/api/images/" + username + "/" + projects.getAcara().getImgAkad())
                .build();

        InfoResepsiResponse resepsi = InfoResepsiResponse.builder()
                .titleResepsi(projects.getAcara().getTitleResepsi())
                .lokasiResepsi(projects.getAcara().getLokasiResepsi())
                .dateResepsi(projects.getAcara().getDateResepsi())
                .mapResepsi(projects.getAcara().getMapResepsi())
                .imageResepsi(ApiEnum.getMainUrl() + "/api/images/" + username + "/" + projects.getAcara().getImgResepsi())
                .build();

        InfoAcaraResponse infoAcaraResponse = ProjectResponse.InfoAcaraResponse.builder()
                .akad(akad)
                .resepsi(resepsi)
                .build();

        return ProjectResponse.builder()
                .title(projects.getTitle())
                .hero(heroResponse)
                .home(homeResponse)
                .cover(coverResponse)
                .theme(themeResponse)
                .infoAcara(infoAcaraResponse)
                .build();
    }
}
