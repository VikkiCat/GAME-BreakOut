package henyk.game;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
    int x;
    int y;
    int imageWidth;
    int imageHeight;

    Image image;

    public int getX() {
        return x;
    }
    protected void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    protected void setY(int y) {
        this.y = y;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Image getImage() {
        return image;
    }

    Rectangle getRectangle(){
        return new Rectangle(x,y,image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions(){
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

    }








}
