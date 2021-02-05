import javafx.geometry.Bounds;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class ButtonTicTacToe extends JButton {
    private String figura = null;

    private int width = this.getWidth();
    private int height = this.getHeight();
    private Rectangle bounds = this.getBounds();
    private int X = bounds.x;
    private int Y = bounds.y;

    private Ellipse2D circle = new Ellipse2D.Double(X,Y,width,height);

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        if(figura != null){

            if(figura.equals("Cross")){
                g2.draw(circle);
            }else {

            }

        }else{
            return;
        }
    }

    public void rysujFigure(String figura){
        figura = "Cross";
        repaint();
    }

    class Cross{
        private Line2D oneLine = new Line2D.Double();
        private Line2D twoLine = new Line2D.Double();


    }
}
