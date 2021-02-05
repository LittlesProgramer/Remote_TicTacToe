import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            ServerFrame frame = null;
            try {
                frame = new ServerFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.setTitle("Server Game TicTacToe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class ServerFrame extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("<html><h3><i>Starting Server</i></h3></html>");
    private JButton startServer = new JButton("Server Start");
    private JTextField startServerInfoView = new JTextField(35);
    private JLabel loggedPlayersPanel = new JLabel("<html><h1>Info Panel Players</br></h1><hr></html>");
    private JTextArea playerInfoView = new JTextArea(10,35);

    public ServerFrame() throws IOException {
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        panel.setLayout(gridBagLayout);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        this.add(panel,new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(10,10).setInsets(5));
        panel.add(label,new GBC(0,0,1,1).setWeight(10,10).setInsets(3).setAnchor(GBC.CENTER));
        panel.add(startServer,new GBC(1,0,1,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(3));
        panel.add(startServerInfoView,new GBC(0,1,2,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(5));

        this.add(loggedPlayersPanel,new GBC(0,1,1,1).setWeight(10,0).setInsets(3).setAnchor(GBC.CENTER));
        this.add(playerInfoView,new GBC(0,2,1,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(5));
        playerInfoView.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        pack();

        startServer.addActionListener((actionEvent)->{
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(12345);
                System.out.println("waiting ...");
                Socket socket = serverSocket.accept();
                System.out.println("connect");
                PrintWriter pr = new PrintWriter(socket.getOutputStream());
                pr.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                pr.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

class GBC extends GridBagConstraints{
    public GBC(int gridx,int gridy){
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GBC(int gridx,int gridy,int gridwidth,int gridheight){
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    public GBC setAnchor(int anchor){
        this.anchor = anchor;
        return this;
    }

    public GBC setFill(int fill){
        this.fill = fill;
        return this;
    }

    public GBC setWeight(double weightx,double weighty){
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    public GBC setInsets(int distance){
        this.insets = new Insets(distance,distance,distance,distance);
        return this;
    }

    public GBC setInsets(int top,int left,int bottom,int right){
        this.insets = new Insets(top,left,bottom,right);
        return this;
    }
}
