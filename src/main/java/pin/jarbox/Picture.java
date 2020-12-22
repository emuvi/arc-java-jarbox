package pin.jarbox;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class Picture implements Serializable {

  private static final long serialVersionUID = 8868642983815465655L;

  public transient BufferedImage image;

  public Picture() {
    this.image = null;
  }

  public Picture(BufferedImage image) {
    this.image = image;
  }

  private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException {
      s.defaultReadObject();
      image = ImageIO.read(s);
  }


  private void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
    BufferedImage toWrite = new BufferedImage(image.getWidth(), 
        image.getHeight(), BufferedImage.TYPE_INT_RGB);
    toWrite.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
    ImageIO.write(toWrite, "bmp", s);
  }



}
