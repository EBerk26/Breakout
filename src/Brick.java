import java.awt.*;
public class Brick {
    public int ypos;
    public int xpos;
    public int width;
    public int height;
    public Rectangle rectangle;
    boolean isAlive;
    public Brick(int pxpos, int pypos){
        xpos = pxpos;
        ypos = pypos;
        width = 120;
        height = 60;
        rectangle = new Rectangle(xpos,ypos,width,height);
        isAlive = true;
    }
}