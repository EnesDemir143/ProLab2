package sample;

public enum CardTypes {
    TANK("card3.png"),
    HELICOPTER("card3.png"),
    FIGHTER_JET("card3.png"),
    BATTLESHIP("card3.png"),
    SUBMARINE("card3.png"),
    ARTILLERY("card3.png");

    private final String imagePath;

    CardTypes(String imagePath) {
        this.imagePath = "/sample/" + imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public static CardTypes fromString(String sinif) {
        return switch (sinif.toLowerCase()) {
            case "obus" -> TANK;
            case "kfc" -> HELICOPTER;
            case "siha" -> FIGHTER_JET;
            case "ucak" -> BATTLESHIP;
            case "firkateyn" -> SUBMARINE;
            case "sida" -> ARTILLERY;
            default -> throw new IllegalArgumentException("Geçersiz kart türü: " + sinif);
        };
    }
}
