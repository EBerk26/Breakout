import java.awt.*;
public class Paddle {
    public int ypos;
    public int xpos;
    public int dx;
    public int width;
    public int height;
    public Rectangle rectangle;
    public Paddle(int pxpos, int pypos){
        xpos = pxpos;
        ypos = pypos;
        dx = 2;
        width = 100;
        height = 10;
        rectangle = new Rectangle(xpos,ypos,width,height);
    }
    public void moveright(){
        xpos+=dx;
    }
    public void moveleft(){
        xpos-=dx;
    }
}