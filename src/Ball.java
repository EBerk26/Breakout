import java.awt.*;
public class Ball {
    public int ypos;
    public int xpos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle rectangle;
    public Ball(int pxpos, int pypos){
        xpos = pxpos;
        ypos = pypos;
        dx = 2;
        dy =4;
        width = 30;
        height = 30;
        rectangle = new Rectangle(xpos,ypos,width,height);
    }
    public void move(){
        xpos+=dx;
        ypos+=dy;
    }
    public void bounce(String bouncedirection){
        if (bouncedirection.equals("side collision")){
            dx=-dx;
        }
        if (bouncedirection.equals("vertical collision")){
            dy=-dy;
        }
    }
}