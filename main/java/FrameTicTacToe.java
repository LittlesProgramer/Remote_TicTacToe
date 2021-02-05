import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static JTextField showConnectingResult = new JTextField(10);

    private JPanel gamePanel = new JPanel();
    private static JLabel resultGameLabel = new JLabel("result your game: ");

    private static Map<ButtonTicTacToe,Integer> allButtonGameMap = new LinkedHashMap<>();//this map include all button and his number in the GamePanel

    public FrameTicTacToe() {
        //Layout configuration ...
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

        //functionality of program

        connectingButton.addActionListener((actionEvent)->{
            if(checkCorrect_Ip_Port_NickName(ipTextField.getText(),portTextField.getText(),nickNameTextField.getText())){

                Client client = new Client(ipTextField.getText(),portTextField.getText(),nickNameTextField.getText());

            }else{
                JOptionPane.showMessageDialog(null,"Please insert correct ip,port and nick values");
            }
        });

    }

    public void addButtonIntoGamePanel(){ //this method add all button in game panel
        GridLayout gridLayout = new GridLayout(3,3);
        gamePanel.setLayout(gridLayout);
        int size = gridLayout.getColumns() * gridLayout.getRows();

        ButtonTicTacToe buttonTicTacToe = null;
        for(int x = 0 ; x < size ; x++){
            buttonTicTacToe = new ButtonTicTacToe();
            gamePanel.add(buttonTicTacToe);
            buttonTicTacToe.setPreferredSize(new Dimension(150,150));
            buttonTicTacToe.setBackground(Color.ORANGE);
            buttonTicTacToe.setOpaque(false);
            allButtonGameMap.put(buttonTicTacToe,x);
        }
    }

    public boolean checkCorrect_Ip_Port_NickName(String ip,String port,String nick){ //this method checking correct inserted ip,port nad nick values
        boolean correctIp = true;
        boolean correctPort = true;
        boolean correctNick = true;

        Pattern pattern = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
        Matcher matcher = pattern.matcher(ip);

        //checking correct ip values
        if(matcher.matches()){
            String ipTab[] = ip.split("\\.");
            for(String el: ipTab){
                int Ip = Integer.valueOf(el);
                if(Ip < 0 || Ip > 255){
                    correctIp = false;
                    break;
                }
            }

        }else{
            correctIp = false;
        }

        //checking correct port values
        Pattern patternPort = Pattern.compile("\\d{1,5}");
        Matcher matcherPort = patternPort.matcher(port);

        if(!port.equals("")) {
            if (matcherPort.matches()) {
                int Port = Integer.valueOf(port);
                if (Port < 0 || Port > 65535) {
                    correctPort = false;
                }
            }
        }else{ correctPort = false; }

        //checking correct nick name
        if(nick.equals("")){
            correctNick = false;
        }

        if(correctIp && correctPort && correctNick){
            return true;
        }else{ return false; }
    }

    public static JTextField getShowConnectionresult(){ //this method return JTextField showConnectingResult
        return showConnectingResult;
    }

    public static Map<ButtonTicTacToe,Integer> getAllButtonGameMap(){ return allButtonGameMap; }
    public static JLabel getResultGameLabel(){ return resultGameLabel; }
}


