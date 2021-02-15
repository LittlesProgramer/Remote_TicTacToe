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

    private Line2D line2D_Horizontal = new Line2D.Double(0,70,140,70);
    private Line2D line2D_Vertical = new Line2D.Double(70,0,70,140);
    private Line2D line2D_CrossI = new Line2D.Double(0,0,140,140);
    private Line2D line2D_CrossII = new Line2D.Double(0,140,140,0);

    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;

        if(figura != null){

            if(figura.equals("Cross")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                //figura = null;
            }else if(figura.equals("Circle")){
                g2.drawString("KOKOlino",50,50);
                //figura = null;
            }else if(figura.equals("Cross+Horizontal")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_Horizontal);
            }else if(figura.equals("Circle+Horizontal")){
                g2.drawString("KOKOlino",50,50);
                g2.draw(line2D_Horizontal);
            }else if(figura.equals("Cross+Vertical")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_Vertical);
            }else if(figura.equals("Circle+Vertical")){
                g2.drawString("KOKOlino",50,50);
                g2.draw(line2D_Vertical);
            }else if(figura.equals("Cross+Cross I")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_CrossI);
            }else if(figura.equals("Circle+Cross I")){
                g2.drawString("KOKOlino",50,50);
                g2.draw(line2D_CrossI);
            }else if(figura.equals("Cross+Cross II")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_CrossII);
            }else if(figura.equals("Circle+Cross II")){
                g2.drawString("KOKOlino",50,50);
                g2.draw(line2D_CrossII);
            }

        }
    }

    public void rysujFigure(String figura){
        this.figura = figura;
        repaint();
    }

    class Cross{
        private Line2D oneLine = new Line2D.Double();
        private Line2D twoLine = new Line2D.Double();
    }
}
