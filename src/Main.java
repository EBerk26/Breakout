import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
public class Main implements Runnable,KeyListener {
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
    public Image Background;
    Brick[] bricks = new Brick[70];
    int bricknumber = 0;
    public Image paddleImage;
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
    boolean isColliding = false;
    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }
    public Main(){
        setUpGraphics();
        Background = Toolkit.getDefaultToolkit().getImage("Solid Black.png");
        paddleImage = Toolkit.getDefaultToolkit().getImage("Solid White.png");
        paddle = new Paddle(WIDTH/2-Paddle.width/2,600);
        ball = new Ball(WIDTH/2,400);
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
        while(true){
            moveThings();
            render();
            pause(3);
        }
    }
    public void moveThings(){
        if(ball.rectangle.intersects(paddle.rectangle)&&!isColliding&&ball.ypos+Paddle.height/2-1+ball.size/2<=paddle.ypos){
            ball.dy=-ball.dy;
            isColliding = true;
        }
        if(isColliding&&!ball.rectangle.intersects(paddle.rectangle)){
            isColliding = false;
        }
        ball.move();
        if(rightkeydown && leftkeydown) {
            //don't do anything if both keys are pressed
        } else if(rightkeydown) {
            paddle.moveright();
        } else if(leftkeydown){
            paddle.moveleft();
        }
        for(int x =0;x<=69;x++){
            if(ball.rectangle.intersects(bricks[x].rectangle)){
                bricks[x].isAlive = false;
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
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(Background,0,0,WIDTH,HEIGHT,null);
        g.drawImage(paddleImage,paddle.xpos,paddle.ypos,Paddle.width,Paddle.height,null);
        g.setColor(Color.WHITE);
        g.fillOval(ball.xpos,ball.ypos, ball.size, ball.size);
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