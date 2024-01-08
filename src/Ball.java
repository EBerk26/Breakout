import java.awt.*;
public class Ball {
    public int ypos;
    public int xpos;
    public int dx;
    public int dy;
    public static int size =30;
    public Rectangle rectangle;
    boolean isMoving = true;
    boolean isAlive;
    int row = 1;
    public void refreshRectangle(){
        rectangle = new Rectangle (xpos,ypos,size,size);
    }
    public Ball(int pxpos, int pypos){
        isAlive = true;
        xpos = pxpos;
        ypos = pypos;
        dx = 1;
        dy =-1;
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
    public void flippedmove(){
        if(isMoving) {
            xpos += dx;
            ypos += dy;
            if (xpos + size >= 1028 || xpos <= 0) {
                dx = -dx;
            }
            if (ypos >= 700-size) {
                dy = -dy;
            }
            if (ypos <= 0) {
                isMoving = false;
            }
            refreshRectangle();
        }
    }
    public void wrap(){
        if(isMoving) {
            xpos += dx;
            if (xpos>= 1029){
                xpos = -size;
            }
            if (xpos<= -size-1){
                xpos = 1028;
            }
            refreshRectangle();
        }
    }
}