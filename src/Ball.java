import java.awt.*;
public class Ball {
    public int ypos;
    public int xpos;
    public int dx;
    public int dy;
    public int size;
    public Rectangle rectangle;
    boolean isMoving = true;
    private void refreshRectangle(){
        rectangle = new Rectangle(xpos,ypos,size,size);
    }
    public Ball(int pxpos, int pypos){
        xpos = pxpos;
        ypos = pypos;
        dx = 1;
        dy =1;
        size = 30;
        refreshRectangle();    }
    public void move(){
        if(isMoving) {
            xpos += dx;
            ypos += dy;
            if (xpos + size >= 1028 || xpos <= 0) {
                dx = -dx;
            }
            if (ypos <= 0) {
                dy = -dy;
            }
            if (ypos >= 700 - size) {
                isMoving = false;
            }
            refreshRectangle();
        }
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