package ascii_art;
import image.Image;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("USAGE: java asciiArt ");
            return;
        }
        Image img = Image.fromFile(args[0]);
        if (img == null) {
            Logger.getGlobal().severe("Failed to open image file " + args[0]);
            return;
        }
        new Shell(img).run();
    }
}










//package ascii_art;
//
//import ascii_art.img_to_char.BrightnessImgCharMatcher;
//import ascii_art.img_to_char.CharRenderer;
//import ascii_output.AsciiOutput;
//import ascii_output.HtmlAsciiOutput;
//import image.Image;
//
//import java.util.Arrays;
//
//public class Driver {
//    public static void main(String[] args) {
//        assert true;
//        Character[] charSet = {'#', 'M', 'n', 'e', 'J', 'c', '_', 'W', '@', 'E', 'B', 'g',
//                'A', 'G', 'w', 'y', ',', '.', '`', ' ', 'i', 'o', '+', '*', '"'};
//        Image img = Image.fromFile("dino.png");
//        BrightnessImgCharMatcher c = new BrightnessImgCharMatcher(img,"Ariel");
//        char[][] chars = c.chooseChars(1024,charSet);
//        AsciiOutput asciiOutput = new HtmlAsciiOutput("out2.html","Ariel");
//        asciiOutput.output(chars);
//    }
//}
