import java.awt.*;
public class Paddle {
    public int ypos;
    public int xpos;
    public int dx;
    static int width = 100;
    static int height = 10;
    public Rectangle rectangle;
     void refreshRectangle(){
        rectangle = new Rectangle(xpos,ypos,width,height);
    }
    public Paddle(int pxpos, int pypos){
        xpos = pxpos;
        ypos = pypos;
        dx = 3;
        refreshRectangle();
    }
    public void moveright(){
        xpos += dx;
        if(xpos>1028-width){
            xpos = 1028-width;
        }
        if(xpos<0){
            xpos = 0;
        }
        refreshRectangle();
    }
    public void moveleft(){
        xpos -= dx;
        if(xpos>1028-width){
            xpos = 1028-width;
        }
        if(xpos<0){
            xpos = 0;
        }
        refreshRectangle();
    }
}