public class ImageData implements java.io.Serializable {

  protected int width;
  protected int height;
  protected int[] pixelData;

  public ImageData(int width, int height, int[] pixels) {
    this.width = width;
    this.height = height;
    pixelData = pixels;
    pixelData = (int[])(pixels.clone());
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int[] getPixelData() {
    return pixelData;
  }

}
