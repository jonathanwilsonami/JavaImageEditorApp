//package passoff;

import java.io.*;
import java.util.*;


public class Image {

      private String id;
      private int width = 0;
      private int height = 0;
      private int colorNum;
      public Pixel[][] pixel;


    public Image(int width, int height) {

      this.id = "P3";
      this.width = width;
      this.height = height;
      this.colorNum = 255;
      this.pixel = new Pixel[height][width];

    }
    //Copy Constructor
    public Image(Image i) {

      id = i.id;
      width = i.width;
      height = i.height;
      colorNum = i.colorNum;
      pixel = i.pixel;

    }

    public String getId(){
      return id;
    }
    public int getWidth(){
      return width;
    }
    public int getHeight(){
      return height;
    }

    public void setWidth(int width){
      this.width = width;
    }
    public void setHeight(int height){
      this.height = height;
    }

    //Helper functions
    public int getMax(int redDiff, int greenDiff, int blueDiff){
      int maxDiff = redDiff;
      if(Math.abs(greenDiff) > Math.abs(maxDiff)){
        maxDiff = greenDiff;
      }
      if(Math.abs(blueDiff) > Math.abs(maxDiff)){
        maxDiff = blueDiff;
      }
      return maxDiff;
    }

    public void setAvg(int row, int startCol, int blur){
      int avRed = 0;
      int avBlue = 0;
      int avGreen = 0;
      int count =0;
      if(startCol == width - 1){
        return;
      }
      for (int col = 0; col < blur; col++){
        if(startCol + col < width){
          count++;
          avRed += pixel[row][col + startCol].getRed();
          avGreen += pixel[row][col + startCol].getGreen();
          avBlue += pixel[row][col + startCol].getBlue();
        }
      }
      if(count > 0) {
            avRed = avRed / count;
            avGreen = avGreen / count;
            avBlue = avBlue / count;
            pixel[row][startCol].setRed(avRed);
            pixel[row][startCol].setGreen(avGreen);
            pixel[row][startCol].setBlue(avBlue);
        }
    }

    public void invert() {
      for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
            pixel[row][col].setRed(Math.abs(255 - pixel[row][col].getRed()));
            pixel[row][col].setGreen(Math.abs(255 - pixel[row][col].getGreen()));
            pixel[row][col].setBlue(Math.abs(255 - pixel[row][col].getBlue()));

        }
      }
    }

    public void grayscale() {
      for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
          int avg = (pixel[row][col].getRed() + pixel[row][col].getBlue() + pixel[row][col].getGreen())/3;
          pixel[row][col].setRed(avg);
          pixel[row][col].setGreen(avg);
          pixel[row][col].setBlue(avg);

        }
      }
    }

    public void emboss() {
      for(int row = height -1; row >= 0; row--){
        for(int col = width -1; col >=0; col--){
          int v = 0;
          if(row == 0 || col == 0){
            v = 128;
          }
          else{
            int redDiff =  pixel[row][col].getRed() - pixel[row-1][col-1].getRed();
            int greenDiff = pixel[row][col].getGreen() - pixel[row-1][col-1].getGreen();
            int blueDiff = pixel[row][col].getBlue() - pixel[row-1][col-1].getBlue();

            int maxDifference = getMax(redDiff, greenDiff, blueDiff);
            v = maxDifference + 128;
            if (v < 0){v = 0;}
            if (v > 255){v = 255;}
        }
        //System.out.println(imageOriginal.pixel[row][col].getRed(v));
        pixel[row][col].setRed(v);
        pixel[row][col].setGreen(v);
        pixel[row][col].setBlue(v);
      }
    }
  }

  public void motionblur(int blur) {
    if(blur <=0){
      return;
    }
    if(blur > width){
      blur = width;
    }
    for(int row = 0; row < height; row++){
      for(int col = 0; col < width; col++){
        setAvg(row,col,blur);
      }
    }
  }

    public String toString(){
      StringBuilder result = new StringBuilder();
      for(int row = 0; row < height; row++){
        for(int col = 0; col < width; col++){
          result.append(pixel[row][col].getRed());
          result.append("\n");
          result.append(pixel[row][col].getGreen());
          result.append("\n");
          result.append(pixel[row][col].getBlue());
          result.append("\n");
        }
      }
      String info;
      info = id + "\n" + width + " " + height + "\n" + colorNum + "\n";
      return info + result;
    }
}

