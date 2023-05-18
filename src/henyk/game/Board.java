//All the game logic happens here
package henyk.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {
    private Timer timer;
    private String message = "Game over!";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;

    public Board(){
        initBoard();
    }

    private void initBoard(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        gameInit();
    }

    private void gameInit(){
        bricks = new Brick[Commons.N_OF_BRICKS];
        ball = new Ball();
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 5 ; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j*40+30, i*10+50);
                k++;
            }
        }

        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame){
            drawObjects(g2d);
        } else {
            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();

    }

    private void drawObjects(Graphics2D graphics2D){
        graphics2D.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        graphics2D.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getImageWidth(), paddle.getImageHeight(),this);

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed()){
                graphics2D.drawImage(bricks[i].getImage(),bricks[i].getX(), bricks[i].getY(),
                        bricks[i].getImageWidth(), bricks[i].getImageHeight(), this);
            }
        }

    }


    private void gameFinished(Graphics2D graphics2D){
        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics =this.getFontMetrics(font);

        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);
        graphics2D.drawString(message, (Commons.WIDTH -
                fontMetrics.stringWidth(message)) / 2, Commons.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }


    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }

        private void doGameCycle(){
            ball.move();
            paddle.move();
            checkColision();
            repaint();
        }

        public void stopGame(){
            inGame = false;
            timer.stop();
        }

        private void checkColision(){
            if (ball.getRectangle().getMaxY() > Commons.BOTTOM_EDGE){
                stopGame();
            }

            for (int i = 0, j = 0; i <Commons.N_OF_BRICKS ; i++) {
                if (bricks[i].isDestroyed()){
                    j++;
                }

            if (j == Commons.N_OF_BRICKS){
                message = "Congratulation";
                stopGame();
            }
            }

            if (ball.getRectangle().intersects(paddle.getRectangle())){
                int paddlePos = (int) paddle.getRectangle().getMinX();
                int ballPos = (int) ball.getRectangle().getMinX();

                int first = paddlePos + 8;
                int second = paddlePos + 16;
                int third = paddlePos + 24;
                int fourth = paddlePos + 32;

                if (ballPos < first){
                    ball.setXDir(-1);
                    ball.setYDir(-1);
                }
                if (ballPos >= first && ballPos < second) {
                    ball.setXDir(-1);
                    ball.setYDir(-1 * ball.getYdir());
                }
                if (ballPos >= second && ballPos < third) {
                    ball.setXDir(0);
                    ball.setYDir(-1);
                }
                if (ballPos >= third && ballPos < fourth) {
                    ball.setXDir(-1);
                    ball.setYDir(-1 * ball.getYdir());
                }
                if (ballPos > fourth) {
                    ball.setXDir(1);
                    ball.setYDir(-1);
                }
            }

            for (int i = 0; i < Commons.N_OF_BRICKS; i++) {
                if (ball.getRectangle().intersects(bricks[i].getRectangle()));
                int ballLeft = (int) ball.getRectangle().getMinX();
                int ballHeight = (int) ball.getRectangle().getHeight();
                int ballWidth = (int) ball.getRectangle().getWidth();
                int ballTop = (int) ball.getRectangle().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop );
                var pointLeft = new Point(ballLeft -1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (bricks[i].isDestroyed()){
                    if (bricks[i].getRectangle().contains(pointRight)){
                        ball.setXDir(-1);
                    } else if (bricks[i].getRectangle().contains(pointLeft)) {
                        ball.setXDir(1);
                    }

                    if (bricks[i].getRectangle().contains(pointTop)){
                        ball.setYDir(1);
                    } else if (bricks[i].getRectangle().contains(pointBottom)) {
                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }

            }
        }


    }









}


