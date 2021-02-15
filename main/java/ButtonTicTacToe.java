import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class ButtonTicTacToe extends JButton {

    private String figura = null;

    private Rectangle dim = null;
    private double width = 0;
    private double height = 0;
    private double middleX = 0;
    private double middleY = 0;

    private Ellipse2D circle = new Ellipse2D.Double();

    private Line2D line2D_Horizontal = new Line2D.Double();
    private Line2D line2D_Vertical = new Line2D.Double();
    private Line2D line2D_CrossI = new Line2D.Double();
    private Line2D line2D_CrossII = new Line2D.Double();
    private Insets border = this.getBorder().getBorderInsets(this);
    private BasicStroke basicStroke = new BasicStroke(2);

    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;

        g2.setStroke(basicStroke);

        dim = g2.getClipBounds();
        width = dim.getWidth();
        height = dim.getHeight();
        middleX = dim.getWidth()/2;
        middleY = dim.getHeight()/2;
        border = this.getBorder().getBorderInsets(this);

        line2D_Horizontal.setLine(0+border.right,middleY,width,middleY);
        line2D_Vertical.setLine(middleX,0+border.top,middleX,height);
        line2D_CrossI.setLine(0+border.left,0+border.top,width,height+border.bottom);
        line2D_CrossII.setLine(0+border.left,height,width,0+border.top);

        circle.setFrame(0,0,width,height);

        if(figura != null){

            if(figura.equals("Cross")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                //figura = null;
            }else if(figura.equals("Circle")){
                g2.draw(circle);
                //figura = null;
            }else if(figura.equals("Cross+Horizontal")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_Horizontal);
            }else if(figura.equals("Circle+Horizontal")){
                g2.draw(circle);
                g2.draw(line2D_Horizontal);
            }else if(figura.equals("Cross+Vertical")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_Vertical);
            }else if(figura.equals("Circle+Vertical")){
                g2.draw(circle);
                g2.draw(line2D_Vertical);
            }else if(figura.equals("Cross+Cross I")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_CrossI);
            }else if(figura.equals("Circle+Cross I")){
                g2.draw(circle);
                g2.draw(line2D_CrossI);
            }else if(figura.equals("Cross+Cross II")){
                g2.draw(new Ellipse2D.Double(0,0,this.getWidth()-10,this.getHeight()-10));
                g2.draw(line2D_CrossII);
            }else if(figura.equals("Circle+Cross II")){
                g2.draw(circle);
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
