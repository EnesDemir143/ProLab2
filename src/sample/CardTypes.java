package sample;

public enum CardTypes {
    OBUS("Obus.jpeg"),
    SIHA("Siha.jpeg"),
    UCAK("Ucak.jpeg"),
    FIRKATEYN("Firkateyn.jpeg"),
    SIDA("Sida.jpeg"),
    KFS("Kfs.jpeg");

    private final String imagePath;

    CardTypes(String imagePath) {
        this.imagePath = "/sample/" + imagePath;
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
