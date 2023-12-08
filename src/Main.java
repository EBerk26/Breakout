import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
public class Main implements Runnable,KeyListener {
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

    int lives = 10;
    int level = 1;
    long StartTime =0;
    public int Score = 6900;
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
    boolean isCollidingwithpaddle = false;
    public Image skullEmoji;
    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }
    public Main(){
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

    }
    public void run(){
        FirstRender();
        while(true) {
            if(System.currentTimeMillis()>=StartTime2+1000) {
                while (true) {
                    moveThings();
                    render();
                    pause(3);
                    if (System.currentTimeMillis() > StartTime + 2000 && !ball.isMoving) {
                        if (Math.random() >= 0.5) {
                            ball.dx = level;
                        } else {
                            ball.dx = -level;
                        }
                        ball.isMoving = true;
                    }
                    if (Score == level * 7000) {
                        level++;
                        ball.isMoving = false;
                        paddle.xpos = WIDTH/2-Paddle.width/2;
                        ball.xpos = WIDTH / 2 - Ball.size / 2;
                        ball.ypos = 600 - Ball.size;
                        StartTime = System.currentTimeMillis();
                        ball.dy = level; //this was a really weird interaction - it should be -level as the ball is going up buy for some reason this is backwards.
                        ball.dx = level;
                        for (int b = 0; b <= 69; b++) {
                            bricks[b].isAlive = true;
                        }
                    }
                }
            }
        }
    }
    public void moveThings() {
        if (ball.rectangle.intersects(paddle.rectangle) && !isCollidingwithpaddle && ball.ypos + Paddle.height / 2 - 1 + Ball.size / 2 <= paddle.ypos) {
            ball.dy = -ball.dy;
            isCollidingwithpaddle = true;
        }
        if (isCollidingwithpaddle && !ball.rectangle.intersects(paddle.rectangle)) {
            isCollidingwithpaddle = false;
        }
        ball.move();
        for(int x =0;x<=69;x++){
            if(ball.rectangle.intersects(bricks[x].rectangle)&&bricks[x].isAlive){
                if(ball.ypos>=bricks[x].ypos+Brick.height-Math.abs(ball.dy)||ball.ypos+Ball.size-Math.abs(ball.dy)<=bricks[x].ypos){
                    ball.dy=-ball.dy;
                }
                if(ball.xpos>=bricks[x].xpos+Brick.width-Math.abs(ball.dx)||ball.xpos+Ball.size-Math.abs(ball.dx)<=bricks[x].xpos){
                    ball.dx=-ball.dx;
                }
                bricks[x].isAlive = false;
                if(updateScore){
                    Score+=100;
                }
            }
        }
        if (rightkeydown && leftkeydown) {
            //don't do anything if both keys are pressed
        } else if (rightkeydown) {
            paddle.moveright();
        } else if (leftkeydown) {
            paddle.moveleft();
        }
        if (ball.ypos>=700-Ball.size) {
            lives--;
            ball.xpos = (int) (Math.random()*(WIDTH-Ball.size));
            ball.ypos = 600-Ball.size;
            ball.dy=-level;
            StartTime = System.currentTimeMillis();
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
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Score: " + Score, 0, 30);
            g.drawString("Lives: " + lives, 0, 60);
            g.setColor(Color.WHITE);
            g.fillRect(paddle.xpos, paddle.ypos, Paddle.width, Paddle.height);
            g.fillOval(ball.xpos, ball.ypos, Ball.size, Ball.size);
            for (int x = 0; x <= 69; x++) {
                if (bricks[x].isAlive) {
                    g.fillRect(bricks[x].xpos, bricks[x].ypos, Brick.width, Brick.height);
                    bricks[x].refreshRectangle();
                }
            }
            g.dispose();
            BufferStrategy.show();
        }
    }
    private void FirstRender(){
        Graphics2D g = (Graphics2D) BufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(paddle.xpos,paddle.ypos,Paddle.width,Paddle.height);
        g.fillOval(ball.xpos,ball.ypos, Ball.size, Ball.size);
        for(int x =0; x<=69; x++){
            if(bricks[x].isAlive) {
                g.fillRect(bricks[x].xpos, bricks[x].ypos, Brick.width, Brick.height);
                bricks[x].refreshRectangle();
            }
        }
        g.dispose();
        BufferStrategy.show();
    }
}
