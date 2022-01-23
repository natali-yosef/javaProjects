package ascii_art.img_to_char;

public class SingleChar {
    private final char sc;
    private double brightnessChar = -1;
    private int pixelForBrightness;
    private String fontForBrightness;
    private double normalizedBrightnessChar = -1;
    private double minForNormalize;
    private double maxForNormalize;


    /**
     * constructor for single char.
     * @param c - the char.
     */
    public SingleChar(char c){
        this.sc = c;
    }

    /**
     * char getter.
     * @return - the char.
     */
    public char getChar(){
        return this.sc;
    }

    /**
     * another getter with no parameters.
     * @return Normalized Brightness Char.
     */
    public double getNormalizedBrightnessChar(){
        return this.normalizedBrightnessChar;
    }

    /**
     * Normalized Brightness Char getter.
     * @return Normalized Brightness Char
     */
    public double getNormalizedBrightnessChar(double min, double max){
        if(min != minForNormalize || max != maxForNormalize){
            this.minForNormalize = min;
            this.maxForNormalize = max;
            this.normalizedBrightnessChar = normalizingBrightness(min, max);
        }
        return normalizedBrightnessChar;
    }

    /**
     * brightness char getter.
     * @return - brightness char.
     */
    public double getBrightnessChar(int pixels, String fontName){

        if(pixels != pixelForBrightness || !fontName.equals(fontForBrightness)){
            this.pixelForBrightness = pixels;
            this.fontForBrightness = fontName;
            this.brightnessChar = calculateCharBrightness(pixels,fontName);
        }
        return brightnessChar;
    }

    /**
     * calculate Char Brightness.
     * @return the char brightness.
     */
    private double calculateCharBrightness(int pixels, String fontName){
        int numWhite;
        boolean[][] charBoolImage = CharRenderer.getImg(sc, pixels, fontName);
        numWhite = sumWhitePix(charBoolImage);
        return (double) numWhite / (double) (pixels * pixels);

    }


    /**
     * counting the number of white pixels.
     * @param charBoolImage - the image representation with booleans.
     * @return - number of white pixels.
     */
    private int sumWhitePix(boolean[][] charBoolImage){
        int numWhites = 0;

        for (boolean[] booleans : charBoolImage) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    numWhites++;
                }
            }
        }
        return numWhites;
    }


    /**
     * normalizing Brightness.
     * @return normalized char Brightness.
     */
    private double normalizingBrightness(double min, double max) {
        if(max <= min){
            return 0;
        }
        return (brightnessChar - min)/(max - min);
    }
}
