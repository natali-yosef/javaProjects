package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.*;

public class Shell {


    private enum RenderType {
        CONSOLE,
        HTML;}

    private static final String MIN_RES_MESSEGE = "min resolution : %d%n";
    private static final String MAX_RES_MESSEGE = "max resolution : %d%n";
    private static final String CHAR_SPLIT = " ";
    private static final String RES_MESSEGE = "Width set to %d%n";
    private static final String ERROR_MESSEGE = "Couldn't find command, try again.";
    private static final int FROM_INDEX_CHAR = 0;
    private static final int TILL_INDEX_CHAR = 2;
    private static final String KEY_WORD_SPACE = "space";
    private static final String KEY_WORD_ALL = "all";
    private static final String EXIT_WORD = "exit";
    private static final String PREFIX = ">>> ";
    private static final String PRINT_CHARS_COMMAND = "chars";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String RES_COMMAND = "res";
    private static final String RENDER_COMMAND = "render";
    private static final String CONSOLE_COMMAND = "console";
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final String KEY_WORD_UP_RES = "up";
    private static final String KEY_WORD_DOWN_RES = "down";
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";

    private Image img;
    private final Set<Character> charSet = new HashSet<>();
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private final BrightnessImgCharMatcher charMatcher;
    private final AsciiOutput output;
    private RenderType renderType;


    /**
     * constructor for Shell.
     * @param img - image to render.
     */
    public Shell(Image img) {

        this.img = img;
//        Collections.addAll(charSet, 'a', 'b', 'c', 'd');
        minCharsInRow = Math.max(1, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(img, FONT_NAME);
        output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
        renderType = RenderType.HTML;
        addChars("0-9");


    }

    /**
     * runs the commands.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(PREFIX);
        String cmd = scanner.nextLine().trim();
        String[] words = cmd.split(CHAR_SPLIT);
        while (!words[0].equals(EXIT_WORD) || words.length != 1) {
            if (!words[0].equals("")) {
                try {
                    activateCommand(words);
                }
                catch (ExceptionInInitializerError e){
                    System.out.println(ERROR_MESSEGE);
                }
            }
            System.out.print(PREFIX);
            cmd = scanner.nextLine().trim();
            words = cmd.split(CHAR_SPLIT);
        }
    }

    /**
     * activate the user command, trows exception if not in the correct format.
     * @param words - user array input.
     */
    private void activateCommand(String[] words) {
        String param = "";
        if (words.length > 1) {
            param = words[1];
        }
        switch (words[0]) {
            // 'chars'
            case PRINT_CHARS_COMMAND:
                if(words.length != 1){
                    throw new ExceptionInInitializerError();
                }
                printCharsSet();
                break;
            case ADD_COMMAND:
                // 'add'
                if (words.length != 2){
                    throw new ExceptionInInitializerError();
                }
                addChars(param);
                break;
            case REMOVE_COMMAND:
                // 'remove'
                if (words.length != 2){
                    throw new ExceptionInInitializerError();
                }
                removeChars(param);
                break;
            case RES_COMMAND:
                // 'res'
                if (words.length != 2){
                    throw new ExceptionInInitializerError();
                }
                resChange(param);
                break;
            case RENDER_COMMAND:
                // 'render'
                //check if the input is valid and if the charSet is not empty
                if(words.length != 1 || charSet.isEmpty()){
                    throw new ExceptionInInitializerError();
                }
                render();
                break;
            case CONSOLE_COMMAND:
                // 'console'
                if(words.length != 1){
                    throw new ExceptionInInitializerError();
                }
                console();
                break;
            default:
                throw new ExceptionInInitializerError();
        }
    }

    /**
     * print every char in chars set in one row with spaces
     */
    private void printCharsSet() {
        charSet.stream().sorted().forEach(c -> System.out.print(c + CHAR_SPLIT));
        System.out.println();
    }

    /**
     * reads the parameters only if 'space', 'all', single char or 'char-char'
     * @param param - represent chars to add or remove
     * @return chars array with two values
     */
    private static char[] parseCharRange(String param) {

        // if param is 'all'
        if (param.equalsIgnoreCase(KEY_WORD_ALL)) {
            return new char[]{' ', '~'};
        }

        // if param is 'space'
        if (param.equalsIgnoreCase(KEY_WORD_SPACE)) {
            return new char[]{' ', ' '};
        }

        // if param is single char
        if (param.length() == 1) {
            return new char[]{param.charAt(0), param.charAt(0)};
        }

        // if the param is 'char-char'
        if (param.length() == 3 && param.charAt(1) == '-') {
            return new char[]{param.charAt(FROM_INDEX_CHAR), param.charAt(TILL_INDEX_CHAR)};
        }
        return null;

    }

    /**
     * adding the chars to the chars set, trows exception if the param is not in the correct format.
     * @param s - param string from the user
     */
    private void addChars(String s) {
        char[] range = parseCharRange(s);
        if (range != null) {
            int firstChar = Math.min(range[0], range[1]);
            int secondChar = Math.max(range[0], range[1]);
            for (int i = firstChar; i <= secondChar; i++) {
                charSet.add((char) i);
            }
        }
        else {
            throw new ExceptionInInitializerError();
        }

    }

    /**
     * removing the chars to the chars set, trows exception if the param is not in the correct format.
     * @param s - param string from the user
     */
    private void removeChars(String s) {
        char[] range = parseCharRange(s);
        if (range != null) {
            int firstChar = Math.min(range[0], range[1]);
            int secondChar = Math.max(range[0], range[1]);
            for (int i = firstChar; i <= secondChar; i++) {
                charSet.remove((char) i);
            }
        }
        else {
            throw new ExceptionInInitializerError();
        }
    }

    /**
     * update the resolution, throws exception if the user input is not in the correct format.
     * @param s - user input.
     */
    private void resChange(String s) {
        // if not 'up' or 'down'
        if(!s.equals(KEY_WORD_UP_RES) && !s.equals(KEY_WORD_DOWN_RES)){
            throw new ExceptionInInitializerError();
        }
        if (s.equals(KEY_WORD_UP_RES)) {
            if(charsInRow * 2 <= maxCharsInRow){
                charsInRow *= 2;
            }
            else {
                System.out.printf(MAX_RES_MESSEGE,maxCharsInRow);
                return;
            }
        }
        if (s.equals(KEY_WORD_DOWN_RES)) {
            if(minCharsInRow <= charsInRow/2){
                charsInRow/=2;
            }
            else {
                System.out.printf(MIN_RES_MESSEGE,minCharsInRow);
                return;
            }
        }
        System.out.printf(RES_MESSEGE,charsInRow);


    }

    /**
     * render the image to console if renderType is 'console', else render to html file.
     */
    private void render(){
        char[][] imageCharArray;
        imageCharArray = charMatcher.chooseChars(charsInRow,charSet.toArray(new Character[0]));
        switch (renderType){
            // no other options, controlled by the writer.
            case HTML:
                output.output(imageCharArray);
                break;
            case CONSOLE:
                printImageToConsole(imageCharArray);
                break;
        }
    }

    /**
     * changing the renderType to console.
     */
    private void console(){
        renderType = RenderType.CONSOLE;
    }

    /**
     * printing image to console.
     * @param imageCharArray - array to print.
     */
    private void printImageToConsole(char[][] imageCharArray) {
        for (char[] chars : imageCharArray) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }




}
