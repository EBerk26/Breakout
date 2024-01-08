import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
public class Main implements Runnable,KeyListener {
    public static int RandInt(int LowerBound, int UpperBound){
        return(int)(Math.random()*(UpperBound-LowerBound+1)+LowerBound);
    }
    boolean bricksIntersected = false;
    Font font = new Font("Verdana", Font.BOLD, 14);
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            rightkeydown = true;
            paddle.moveright();
        }
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            leftkeydown = true;
            paddle.moveleft();
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            rightkeydown = false;
        }
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            leftkeydown = false;
        }
    }
    public void keyTyped(KeyEvent e){

    }
    public boolean screenisflipped = false;
    public Image Shovel;
    boolean killBall = false;
    public Image LawnMower;
    public Image Sky;
    public Image Flowers;
    public Image PaddleColor;
    int lives = 10;
    int level = 1;
    long StartTime = 0;
    public int Score = 0;
    boolean updateScore = true;
    long StartTime2;
    Brick[] bricks = new Brick[70];
    int bricknumber = 0;
    boolean rightkeydown=false;
    boolean leftkeydown=false;
    final int WIDTH = 1028;
    final int HEIGHT = 700;
    public JFrame JFrame;
    public Canvas Canvas;
    public JPanel JPanel;
    public BufferStrategy BufferStrategy;
    public Paddle paddle;
    public Ball ball;
    Ball bonusBall = new Ball(0,0);
    boolean isCollidingwithpaddle = false;
    public Image skullEmoji;
    public boolean rowCleared(int prow){
        for(int x = (prow-1)*14;x<=(prow-1)*14+13;x++){
            if (bricks[x].isAlive){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }
    public Main(){
        Sky = Toolkit.getDefaultToolkit().getImage("Sky.jpg");
        LawnMower = Toolkit.getDefaultToolkit().getImage("Lawn Mower.png");
        Shovel = Toolkit.getDefaultToolkit().getImage("Shovel.png");
        Flowers = Toolkit.getDefaultToolkit().getImage("Flowers.jpeg");
        PaddleColor = Toolkit.getDefaultToolkit().getImage("Dark Green.png");
        StartTime2 = System.currentTimeMillis();
        setUpGraphics();
        paddle = new Paddle(WIDTH/2-Paddle.width/2,600);
        ball = new Ball(WIDTH/2-Ball.size/2,600-Ball.size);
        skullEmoji = Toolkit.getDefaultToolkit().getImage("SkullEmoji.png");
        for (int x = 0; x<=69;x++){
            bricks[x] = new Brick();
        }
        for(int a = 1;a<=5;a++){
            for (int b =1; b<=14; b++){
                bricks[bricknumber].xpos = 6*b+Brick.width*(b-1);
                bricks[bricknumber].ypos = 94+6*a+Brick.height*(a-1);
                bricknumber++;
            }
        }
        bricks[RandInt(0,70)].hiddenBall = true;
        /*if(Math.random()<=0.2){
            bricks[RandInt(0,70)].screenflip = true;
        }*/
        //bricks[65].screenflip = true;
        //bricks[65].hiddenBall = true; //this code makes the hidden ball be in the first block
    }
    public void run(){
        FirstRender();
        bonusBall.isAlive = false;
        bonusBall.dy = 0;
        while(true) {
            if(System.currentTimeMillis()>=StartTime2+1000) {
                while (true) {
                    moveThings();
                    render();
                    if(!screenisflipped) {
                        killBall = rowCleared(bonusBall.row);
                    }
                    if(screenisflipped){
                        killBall = rowCleared(bonusBall.row);
                    }
                    if(killBall){
                        bonusBall.isAlive = false;
                        bonusBall.isMoving = false;
                        bonusBall.xpos = 10000;
                    }
                    pause(3);
                    if (System.currentTimeMillis() > StartTime + 2000 && !ball.isMoving) {
                        if (Math.random() >= 0.5) {
                            ball.dx = level;
                        } else {
                            ball.dx = -level;
                        }
                        ball.isMoving = true;
                    }
                    if (Score >= level * 7000) {
                        if(!screenisflipped) {
                            level++;
                            ball.isMoving = false;
                            paddle.xpos = WIDTH / 2 - Paddle.width / 2;
                            ball.xpos = WIDTH / 2 - Ball.size / 2;
                            ball.ypos = 600 - Ball.size;
                            ball.refreshRectangle();
                            StartTime = System.currentTimeMillis();
                            ball.dy = -level;
                            ball.dx = level;
                            for (int b = 0; b <= 69; b++) {
                                bricks[b].isAlive = true;
                                bricks[b].hiddenBall = false;
                            }
                            if (Math.random() <= 0.2 + level / 20) {
                                bricks[RandInt(0, 70)].screenflip = true;
                            }
                            bonusBall.xpos = 0;
                            bonusBall.ypos = 0;
                            bonusBall.isAlive = false;
                            bonusBall.isMoving = false;
                            bricks[RandInt(0, 70)].hiddenBall = true;
                        }
                        if(screenisflipped){
                            level++;
                            ball.isMoving = false;
                            paddle.xpos = WIDTH / 2 - Paddle.width / 2;
                            ball.xpos = WIDTH / 2 - Ball.size / 2;
                            ball.ypos = 700-(600 - Ball.size);
                            ball.refreshRectangle();
                            StartTime = System.currentTimeMillis();
                            ball.dy = level;
                            ball.dx = level;
                            for (int b = 0; b <= 69; b++) {
                                bricks[b].isAlive = true;
                                bricks[b].hiddenBall = false;
                                bricks[b].screenflip = false;
                            }
                            if (Math.random() <= 0.2 + level / 20) {
                                bricks[RandInt(0, 70)].screenflip = true;
                            }
                            bonusBall.xpos = 0;
                            bonusBall.ypos = 0;
                            bonusBall.isAlive = false;
                            bonusBall.isMoving = false;
                            bricks[RandInt(0, 70)].hiddenBall = true;
                        }
                    }
                }
            }
        }
    }
    public void moveThings() {
        /*paddle.xpos = ball.xpos+ball.size/2-paddle.width/2;
        paddle.refreshRectangle();*/
        //automatic play
        if(!screenisflipped) {
            if (ball.rectangle.intersects(paddle.rectangle) && !isCollidingwithpaddle && ball.ypos + Paddle.height / 2 - 1 + Ball.size / 2 <= paddle.ypos) {
                ball.dy = -ball.dy;
                isCollidingwithpaddle = true;
            }
        }
        if (screenisflipped){
            if (ball.rectangle.intersects(paddle.rectangle) && !isCollidingwithpaddle && ball.ypos - Paddle.height / 2 + 1 >= paddle.ypos) {
                ball.dy = -ball.dy;
                isCollidingwithpaddle = true;
            }
        }
        if (isCollidingwithpaddle && !ball.rectangle.intersects(paddle.rectangle)) {
            isCollidingwithpaddle = false;
        }
        if(!screenisflipped){
            ball.move();
        }
        if(screenisflipped){
            ball.flippedmove();
        }
        if(bonusBall.isMoving){
            bonusBall.wrap();
        }
        for(int x =0;x<=69;x++){
            if(ball.rectangle.intersects(bricks[x].rectangle)&&bricks[x].isAlive){
                System.out.println(x);
                if((ball.ypos>=bricks[x].ypos+Brick.height-Math.abs(ball.dy)||ball.ypos+Ball.size-Math.abs(ball.dy)<=bricks[x].ypos)&&!bricksIntersected){
                    ball.dy=-ball.dy;
                }
                if((ball.xpos>=bricks[x].xpos+Brick.width-Math.abs(ball.dx)||ball.xpos+Ball.size-Math.abs(ball.dx)<=bricks[x].xpos)&&!bricksIntersected){
                    ball.dx=-ball.dx;
                }
                bricks[x].isAlive = false;
                if(updateScore){
                    Score+=100;
                }
                bricksIntersected = true;
                if(bricks[x].hiddenBall){
                    bonusBall.xpos = bricks[x].xpos+Brick.width/2-Ball.size/2;
                    bonusBall.ypos = bricks[x].ypos+Brick.height/2-Ball.size/2;
                    if(!screenisflipped) {
                        bonusBall.row = (bonusBall.ypos - Brick.height / 2 + Ball.size / 2 - 44) / 56;
                    }
                    if(screenisflipped){
                        bonusBall.row = 6-(((bricks[x].ypos+Brick.height/2)-350)/31+1);
                        System.out.println("row: "+bonusBall.row);
                    }
                    bonusBall.isAlive = true;
                    bonusBall.isMoving = true;
                }
                if(bricks[x].screenflip && !screenisflipped){
                    ball.isMoving = false;
                    bonusBall.isMoving = false;
                    paddle.ypos = 700-paddle.ypos;
                    paddle.refreshRectangle();
                    paddle.dx = -3;
                    for(int a = 0; a<=69; a++){
                        bricks[a].ypos = 700 - (bricks[a].ypos+Brick.height/2)-Brick.height/2;
                        bricks[a].refreshRectangle();
                    }
                    ball.ypos = 700-ball.ypos;
                    ball.refreshRectangle();
                    ball.dx = -ball.dx;
                    ball.dy = -ball.dy;
                    bonusBall.ypos = 700-bonusBall.ypos;
                    bonusBall.refreshRectangle();
                    bonusBall.dx = -bonusBall.dx;
                    bonusBall.dy = -bonusBall.dy;
                    screenisflipped = true;
                    pause(1000);
                    ball.isMoving = true;
                    bonusBall.isMoving = true;
                }
            }
            if (bonusBall.rectangle.intersects(bricks[x].rectangle)&&bricks[x].isAlive&& bonusBall.isAlive){
                bricks[x].isAlive = false;
                if(updateScore){
                    Score+=100;
                }
            }
        }
        bricksIntersected = false;
        if (rightkeydown && leftkeydown) {
            //don't do anything if both keys are pressed
        } else if (rightkeydown) {
            paddle.moveright();
        } else if (leftkeydown) {
            paddle.moveleft();
        }
        if(!screenisflipped){
            if (ball.ypos>=700-Ball.size) {
                lives--;
                ball.xpos = (int) (Math.random() * (WIDTH - Ball.size));
                ball.ypos = 600 - Ball.size;
                ball.dy = -level;
                StartTime = System.currentTimeMillis();
            }
        }
        if(screenisflipped){
            if(ball.ypos<=0){
                lives--;
                ball.xpos = (int) (Math.random()*(WIDTH-Ball.size));
                ball.ypos = 700 - (600 - Ball.size);
                ball.dy = level;
                StartTime =System.currentTimeMillis();
            }
        }
    }
    public void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    private void setUpGraphics() {
        JFrame = new JFrame("Application Template");
        JPanel = (JPanel) JFrame.getContentPane();
        JPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JPanel.setLayout(null);
        Canvas = new Canvas();
        Canvas.setBounds(0, 0, WIDTH, HEIGHT);
        Canvas.setIgnoreRepaint(true);
        JPanel.add(Canvas);
        JFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.pack();
        JFrame.setResizable(false);
        JFrame.setVisible(true);
        Canvas.createBufferStrategy(2);
        BufferStrategy = Canvas.getBufferStrategy();
        Canvas.requestFocus();
        Canvas.addKeyListener(this);
    }
    private void render(){
        Graphics2D g = (Graphics2D) BufferStrategy.getDrawGraphics();
        if(lives == 0){
            ball.isMoving = false;
            ball.xpos = WIDTH/2;
            ball.ypos = HEIGHT/2;
            updateScore = false;
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.BLACK);
            font = new Font("Verdana", Font.BOLD, 49);
            g.setFont(font);
            g.drawString("YOU DIED. YOUR SCORE WAS "+Score,+WIDTH/2-510+74/2-4-30,600);
            g.drawImage(skullEmoji,WIDTH/2-480/2,100,480,400,null);
            BufferStrategy.show();
        } else {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.drawImage(Sky,0,0,WIDTH,HEIGHT,null);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Score: " + Score, 0, 30);
            g.drawString("Lives: " + lives, 0, 60);
            g.drawImage(PaddleColor,paddle.xpos,paddle.ypos,Paddle.width,Paddle.height,null);
            g.drawImage(Shovel,ball.xpos,ball.ypos,Ball.size,Ball.size,null);
            for (int x = 0; x <= 69; x++) {
                if (bricks[x].isAlive) {
                    g.drawImage(Flowers,bricks[x].xpos,bricks[x].ypos,Brick.width,Brick.height,null);
                    bricks[x].refreshRectangle();
                }
            }
            if (bonusBall.isAlive){
                g.drawImage(LawnMower,bonusBall.xpos,bonusBall.ypos,ball.size,ball.size,null);
            }
            g.dispose();
            BufferStrategy.show();
        }
    }
    private void FirstRender(){
        Graphics2D g = (Graphics2D) BufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(Sky,0,0,WIDTH,HEIGHT,null);
        g.drawImage(PaddleColor,paddle.xpos,paddle.ypos,Paddle.width,Paddle.height,null);
        g.drawImage(Shovel,ball.xpos,ball.ypos,Ball.size,Ball.size,null);
        for(int x =0; x<=69; x++){
            g.drawImage(Flowers,bricks[x].xpos,bricks[x].ypos,Brick.width,Brick.height,null);
            bricks[x].refreshRectangle();
        }
        g.dispose();
        BufferStrategy.show();
    }
}