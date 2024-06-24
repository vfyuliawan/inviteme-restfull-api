package inviteme.restfull.enumiration;

public enum ApiEnum {
    MAIN_URL("http://localhost:8080"),
    UPLOAD_DIR("uploads/");

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


    public static String getMainUrl() {
        return MAIN_URL.getValue();
    }
}
