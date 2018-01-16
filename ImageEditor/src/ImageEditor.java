//package pass
/******************************************************
*Image Editor Lab 1 CS240 Java
*Jonathan Wilson
*
*
*******************************************************/
import java.io.*;
import java.util.*;

public class ImageEditor {
  //Class Vars
  public static FileReader inputFileName;
  public static File outputFileName;

  //Class Functions
  public ImageEditor(FileReader inputFileName, File outputFileName){
    this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
  }

  public Image scan(FileReader inputFileName) throws FileNotFoundException {
    int width;
    int height;
    Scanner scanner = new Scanner(inputFileName).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
    //Constructing an Image
    scanner.next();//P3
    width = scanner.nextInt();//width
    height = scanner.nextInt();//height
    scanner.nextInt();//225
    Image image = new Image(width,height);

      for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
          int red = scanner.nextInt();
          int green = scanner.nextInt();
          int blue = scanner.nextInt();

          Pixel pixelEl = new Pixel(red, green, blue);
          image.pixel[row][col] = pixelEl;
        }
      }
      return image;
    }

    public static void main(String[] args)throws FileNotFoundException{
      String usage = "USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)";
      FileReader inputFileName = new FileReader(args[0]);
      File outputFileName = new File(args[1]);
      String trans = args[2];
      int blur = 0;
      if (trans.equals("motionblur")) {
            blur = Integer.parseInt(args[3]);
        }
      PrintWriter writer = new PrintWriter(outputFileName);
      ImageEditor e = new ImageEditor(inputFileName, outputFileName);
      //returns a Image pointer to the new object that was just returned in scan
      Image theImage = e.scan(inputFileName);
      switch (trans) {
           case "invert":
                    theImage.invert();
                    break;
           case "grayscale":
                    theImage.grayscale();
                    break;
           case "emboss":
                    theImage.emboss();
                    break;
          case "motionblur":
                    theImage.motionblur(blur);
                    break;
           default:
                    System.out.println("Invalid transformation: " + usage);
                    System.exit(1);
       }

      writer.print(theImage);
      writer.close();//MUST close to write
    }


}

/******************************************************
TODOS:
  Skiping comments?
  Error messages on command line?
  Usage statements
SPECS:
  Routines:
    invert
    grayscale
    emboss
    motionblur
  Command line:
    java ImageEditor inputFileName outputFileName {grayscale|invert|emboss|motionblur blurLength}
    java ImageEditor bike.ppm blurred.ppm motionblur 10 (10 only for the blur)
  For errors: output a usage satement.
  *Output must match the correct text file.
Tips:
  Input: Use regex delimeter (for scanner)
  output: use Stringbuilder do not make multiple calls to write

Links:
  Image convert - https://convertio.co/ppm-jpg/
*******************************************************/
/*

try {
      // Parse the string argument into an integer value.
      num = Integer.parseInt(args[0]);
  }
  catch (NumberFormatException nfe) {
      // The first argument isn't a valid integer.  Print
      // an error message, then exit with an error code.
      System.out.println("The first argument must be an integer.");
      System.exit(1);
  }



*/

