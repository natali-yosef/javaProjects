package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class BrightnessImgCharMatcher {
    private static final int PIXELS = 16;
    private static final double RED_DOUBLE = 0.2126;
    private static final double GREEN_DOUBLE = 0.7152;
    private static final double BLUE_DOUBLE = 0.0722;
    private static final double NUM_COLORS = 255;
    private final Image imgToConvert;
    private final String fontName;
    private final HashMap<Image,Double> cache = new HashMap<>();

    /**
     * constructor
     */
    public BrightnessImgCharMatcher(Image img, String fontName){
        this.imgToConvert = img;
        this.fontName = fontName;
    }

    /**
     * choosing what chars will represent the image.
     * @param numCharsInRow - row len.
     * @param charSet - the char set to pick from.
     * @return - the char representation, null if the charSet is empty.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        int numChars = charSet.length;
        if(numChars == 0){
            return null;
        }
        double brightnessLevel, maxBrightness=0, minBrightness = 0;
        SingleChar[] charsArray = new SingleChar[numChars];

        // updating the brightness
        for (int charIndex = 0; charIndex < numChars; charIndex++) {
            charsArray[charIndex] = new SingleChar(charSet[charIndex]);
            brightnessLevel = charsArray[charIndex].getBrightnessChar(PIXELS, fontName);
            if(maxBrightness<brightnessLevel){
                maxBrightness = brightnessLevel;
            }
            if(charIndex == 0 || brightnessLevel<minBrightness){
                minBrightness = brightnessLevel;
            }
        }

        // normalize all chars in the array
        for (int charIndex = 0; charIndex < numChars; charIndex++) {
            charsArray[charIndex].getNormalizedBrightnessChar(minBrightness, maxBrightness);
        }

        return convertImageToAscii(charsArray, numCharsInRow);

    }

    /**
     * check the avg image brightness.
     * @param image - image to check brightness.
     * @return return avg of the image brightness.
     */
    private double avgBrightnessImage(Image image){
        double imageBrightnessSum, brightnessPixel, avgBrightnessImage;
        int numPixels;
        imageBrightnessSum = 0;
        numPixels = 0;
        for(Color pixel: image.pixels()){
            brightnessPixel = (pixel.getRed() * RED_DOUBLE) +
                    (pixel.getGreen() * GREEN_DOUBLE) +
                    (pixel.getBlue() * BLUE_DOUBLE);
            imageBrightnessSum += brightnessPixel;
            numPixels ++;
        }
        avgBrightnessImage = (imageBrightnessSum/numPixels)/NUM_COLORS;
        return avgBrightnessImage;

    }

    /**
     * matching every sub image to the closest char in the char array.
     * @param charsArray - singleChar array.
     * @param numCharsInRow - how many chars in row.
     * @return - chars arrays, every row represents image row and every column represents image column.
     */
    private char[][] convertImageToAscii(SingleChar[] charsArray,int numCharsInRow){
        double avgSub;
        int pixels, numRows,numCols, rowIndex = 0,colIndex = 0;
        char closestChar;
        char[][] asciiResult;

        pixels = imgToConvert.getWidth() / numCharsInRow;
        numRows = imgToConvert.getHeight()/pixels;
        numCols = imgToConvert.getWidth()/pixels;
        asciiResult = new char[numRows][numCols];

        for(Image subImage : imgToConvert.squareSubImagesOfSize(pixels)){
            if(numCols == colIndex){
                colIndex = 0;
                rowIndex++;
            }
            if(!cache.containsKey(subImage)){
                cache.put(subImage,avgBrightnessImage(subImage));
            }
            avgSub = cache.get(subImage);
            closestChar = getClosestChar(charsArray,avgSub);
            asciiResult[rowIndex][colIndex] = closestChar;
            colIndex++;
        }
    return asciiResult;
    }

    /**
     * method checks what is the char with the closest brightness to the avg.
     * @param charsArray - singleChar array.
     * @param avgSub - avg sub image brightness.
     * @return the char with the closest brightness to the avg brightness.
     */
    private char getClosestChar(SingleChar[] charsArray, double avgSub) {
        double minDistance = 2, distance;
        char minDistanceChar= ' ';
        for(SingleChar c : charsArray){
            distance = Math.abs(c.getNormalizedBrightnessChar() - avgSub);
            if(distance<minDistance){
                minDistance = distance;
                minDistanceChar = c.getChar();
            }
        }
        return minDistanceChar;
    }



}

