package lv.rtu.domain;

enum FileConstants {

    TEMPORARY_DATA("tmp"),
    STADLY_DATA("data"),
    IMAGE_DATA("images"),
    AUDIO_DATA("audio");

    private FileConstants(final String data) {
        this.data= data;
    }

    private String data;

    public String getValue() {
        return data;
    }
}