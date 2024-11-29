package Graphics;

public enum CardTypes {
    OBUS("Obus.png"),
    SIHA("Siha.png"),
    UCAK("ucak.png"),
    FIRKATEYN("Firkateyn.png"),
    SIDA("Sida.png"),
    KFS("Kfs.png");

    private final String imagePath;

    CardTypes(String imagePath) {
        this.imagePath = "/Graphics/Photos/" + imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public static CardTypes fromString(String sinif) {
        return switch (sinif.toLowerCase()) {
            case "obus" -> OBUS;
            case "siha" -> SIHA;
            case "ucak" -> UCAK;
            case "kfs" -> KFS;
            case "firkateyn" -> FIRKATEYN;
            case "sida" -> SIDA;
            default -> throw new IllegalArgumentException("Geçersiz kart türü: " + sinif);
        };
    }
}
