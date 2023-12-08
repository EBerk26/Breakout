import java.awt.*;
public class Paddle {
    public int ypos;
    public int xpos;
    public int dx;
    static int width = 100;
    static int height = 10;
    public Rectangle rectangle;
    private void refreshRectangle(){
        rectangle = new Rectangle(xpos,ypos,width,height);
    }
    public Paddle(int pxpos, int pypos){
        xpos = pxpos;
        ypos = pypos;
        dx = 3;
        refreshRectangle();
    }
    public void moveright(){
        if(xpos<1028-dx-width) {
            xpos += dx;
        } else if(xpos>1028-dx-width){
            xpos=1028-width;
        }
        refreshRectangle();
    }
    public void moveleft(){
        if(xpos>dx) {
            xpos -= dx;
        } else if(xpos<dx){
            xpos=0;
        }
        refreshRectangle();
    }
}