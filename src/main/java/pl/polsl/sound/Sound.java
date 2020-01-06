package pl.polsl.sound;

public enum Sound {

    FAIL("fail.mp3", false),
    SUCCESS("success.mp3", false),
    BACKGROUND("background.mp3", true),
    CONSUME("consume.mp3", false);;

    private final String fileName;
    private final boolean looped;

    Sound(String fileName, boolean looped){
        this.fileName = fileName;
        this.looped = looped;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isLooped() {
        return looped;
    }
}
