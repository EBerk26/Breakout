import java.awt.*;
public class Brick {
    public int ypos = 0;
    public int xpos = 0;
    public static int width = 67;
    public static int height = 50;
    boolean isAlive = true;
    Rectangle rectangle = new Rectangle(xpos,ypos,width,height);
    boolean hiddenBall = false;
    boolean screenflip = false;
    public Brick(){

    }
    public void refreshRectangle(){
        rectangle = new Rectangle(xpos,ypos,width,height);
    }
}