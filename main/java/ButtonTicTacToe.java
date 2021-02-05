import javafx.geometry.Bounds;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class ButtonTicTacToe extends JButton {
    private String figura = null;

    private Rectangle bounds = this.getBounds();
    private double width = bounds.getWidth();
    private double height = bounds.getHeight();
    private int X = bounds.x;
    private int Y = bounds.y;

    private Ellipse2D circle = new Ellipse2D.Double(X,Y,width,height);

    public void paintComponent(Graphics g){
        System.out.println("bo: "+bounds.getX()+" "+bounds.getWidth()+" "+bounds.getMinX()+" "+bounds.getCenterX());
        System.out.println("this: "+this.getWidth()+" "+this.getHeight()+" "+this.getMinimumSize()+" "+this.getMaximumSize()+" "+this.getPreferredSize());
        Graphics2D g2 = (Graphics2D)g;

        System.out.println("figura = "+figura);
        if(figura != null){

            if(figura.equals("Cross")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
            }else {

            }

        }else{
            return;
        }
    }

    public void rysujFigure(String figura){
        this.figura = "Cross";
        repaint();
    }

    class Cross{
        private Line2D oneLine = new Line2D.Double();
        private Line2D twoLine = new Line2D.Double();


    }
}
