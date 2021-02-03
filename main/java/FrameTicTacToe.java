import javax.swing.*;
import java.awt.*;

public class FrameTicTacToe extends JFrame {

    private JLabel nickNamelabel = new JLabel("Please insert your NickName: ");
    private JTextField nickNameTextField = new JTextField(10);
    private JLabel ipNumberLabel = new JLabel("Server IP: ");
    private JTextField ipTextField = new JTextField(10);
    private JLabel portLabel = new JLabel("Server PORT: ");
    private JTextField portTextField = new JTextField(3);

    private JPanel clinetOptionPanel = new JPanel();
    private JLabel connectingToServerLabel = new JLabel("connecting to server ...");
    private JButton connectingButton = new JButton("connect");
    private JTextField showConnectingResult = new JTextField(10);

    private JPanel gamePanel = new JPanel();
    private JLabel resultGameLabel = new JLabel("result your game: ");

    public FrameTicTacToe() {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        clinetOptionPanel.setLayout(layout);
        clinetOptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        this.add(clinetOptionPanel,new GBC(0,0,1,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(3));

        clinetOptionPanel.add(nickNamelabel,new GBC(0,0,3,1).setWeight(10,10).setAnchor(GBC.CENTER).setInsets(3));
        clinetOptionPanel.add(nickNameTextField,new GBC(3,0,1,1).setWeight(10,10).setFill(GBC.HORIZONTAL).setInsets(3,3,3,15));

        clinetOptionPanel.add(ipNumberLabel,new GBC(0,1,1,1).setWeight(10,10).setInsets(3).setAnchor(GBC.CENTER));
        clinetOptionPanel.add(ipTextField,new GBC(1,1,1,1).setWeight(10,10).setFill(GBC.HORIZONTAL).setInsets(3));
        clinetOptionPanel.add(portLabel,new GBC(2,1,1,1).setWeight(10,10).setInsets(3).setAnchor(GBC.CENTER));
        clinetOptionPanel.add(portTextField,new GBC(3,1,1,1).setWeight(10,10).setFill(GBC.HORIZONTAL).setInsets(3,3,3,15));

        clinetOptionPanel.add(connectingToServerLabel,new GBC(0,2,2,1).setWeight(10,10).setAnchor(GBC.CENTER).setInsets(3));
        clinetOptionPanel.add(connectingButton,new GBC(2,2,2,1).setWeight(10,10).setFill(GBC.HORIZONTAL).setInsets(3,3,3,15));
        clinetOptionPanel.add(showConnectingResult,new GBC(0,3,4,1).setWeight(10,0).setFill(GBC.HORIZONTAL).setInsets(3));

        this.add(gamePanel,new GBC(0,1,1,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(5));
        this.addButtonIntoGamePanel();

        this.add(resultGameLabel,new GBC(0,2,1,1).setWeight(10,10).setInsets(3).setAnchor(GBC.CENTER));

    }

    public void addButtonIntoGamePanel(){
        GridLayout gridLayout = new GridLayout(3,3);
        gamePanel.setLayout(gridLayout);
        int size = gridLayout.getColumns() * gridLayout.getRows();

        ButtonTicTacToe buttonTicTacToe = null;
        for(int x = 0 ; x < size ; x++){
            buttonTicTacToe = new ButtonTicTacToe();
            gamePanel.add(buttonTicTacToe);
            buttonTicTacToe.setPreferredSize(new Dimension(150,150));
            buttonTicTacToe.setBackground(Color.ORANGE);
        }
    }
}


