package inviteme.restfull.enumiration;

public enum ApiEnum {
    MAIN_URL("http://localhost:8080"),
    UPLOAD_DIR("uploads/"),
    UPLOAD_URL("https://olive-hornet-950635.hostingersite.com/upload.php"),
    UPLOAD_URL_UAT("https://olive-hornet-950635.hostingersite.com/uploaduat.php"),
    BASE_URL_STORAGE("https://olive-hornet-950635.hostingersite.com/");


    private final String value;

    ApiEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getUploadDir() {
        return UPLOAD_DIR.getValue();
    }

    public static String getUploadURL() {
        return UPLOAD_URL.getValue();
    }

    public static String getUploadURLUAT() {
        return UPLOAD_URL_UAT.getValue();
    }

    public static String getURLStorage() {
        return BASE_URL_STORAGE.getValue();
    }


    public static String getMainUrl() {
        return MAIN_URL.getValue();
    }
}
