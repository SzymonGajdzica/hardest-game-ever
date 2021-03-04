package pl.polsl.sound;

public enum Sound {

    FAIL("fail.wav", false),
    SUCCESS("success.wav", false),
    BACKGROUND("background.wav", true),
    CONSUME("consume.wav", false);

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
