package com.example.superjjj.util;

public enum TileColor {
    BACKGROUND_2("#eee4da"),
    BACKGROUND_4("#ede0c8"),
    BACKGROUND_8("#f2b179"),
    BACKGROUND_16("#f59563"),
    BACKGROUND_32("#f67c5f"),
    BACKGROUND_64("#f65e3b"),
    BACKGROUND_128("#FF8C00"),
    BACKGROUND_256("#FFD700"),
    BACKGROUND_512("#9ACD32"),
    BACKGROUND_1024("#483D8B"),
    BACKGROUND_2048("#4B0082"),
    BACKGROUND_BIGGER_THAN_2048("#B2000000");

    private String colorCode;

    TileColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public static String getColorByNumber(int number) {
        switch (number) {
            case 2:
                return BACKGROUND_2.getColorCode();
            case 4:
                return BACKGROUND_4.getColorCode();
            case 8:
                return BACKGROUND_8.getColorCode();
            case 16:
                return BACKGROUND_16.getColorCode();
            case 32:
                return BACKGROUND_32.getColorCode();
            case 64:
                return BACKGROUND_64.getColorCode();
            case 128:
                return BACKGROUND_128.getColorCode();
            case 256:
                return BACKGROUND_256.getColorCode();
            case 512:
                return BACKGROUND_512.getColorCode();
            case 1024:
                return BACKGROUND_1024.getColorCode();
            case 2048:
                return BACKGROUND_2048.getColorCode();
            default:
                return BACKGROUND_BIGGER_THAN_2048.getColorCode();
        }
    }
}

