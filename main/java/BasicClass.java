import javax.swing.*;
import java.awt.*;

public class BasicClass {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrameTicTacToe frame = new FrameTicTacToe();
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
