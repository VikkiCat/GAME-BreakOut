package henyk.game;

import javax.swing.ImageIcon;
public class Ball extends Sprite {

    //Direction for x and y
    private int xdir;
    private int ydir;

    public Ball(){
        initBall();
    }

    private void initBall(){
        xdir = 1;
        ydir = -1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage(){
        var ii = new ImageIcon("src/henyk/resources/ball.png");
        image = ii.getImage();
    }

    public void  move(){
        x += xdir;
        y += ydir;

        if (x == 0){
            setXDir(1);
        }
        if (x == Commons.WIDTH - imageWidth){
            setXDir(-1);
        }
        if (y == 0){
            setYDir(1);
        }
    }

    private void resetState(){
        x = Commons.INNIT_BALL_X;
        y = Commons.INNIT_BALL_Y;
    }

    public void setXDir(int x){
        xdir = x;
    }

    public void setYDir(int y){
        ydir = y;
    }

    public int getXdir(){
        return xdir;
    }

    public int getYdir(){
        return  ydir;
    }

}
