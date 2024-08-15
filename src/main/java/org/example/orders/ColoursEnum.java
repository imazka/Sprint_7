package org.example.orders;

public enum ColoursEnum {

    BLACK_GREY(new String[]{"BLACK", "GREY"}),
    GREY_ONLY(new String[]{"GREY"}),
    BLACK_ONLY(new String[]{"BLACK"}),
    NONE(new String[]{});

    private final String[] colours;

    ColoursEnum(String[] colours) {
        this.colours = colours;
    }

    public String[] getColours() {
        return colours;
    }

}
