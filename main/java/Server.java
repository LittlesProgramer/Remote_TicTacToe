import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            ServerFrame frame = null;
            frame = new ServerFrame();
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
    private Map<Socket,String> socket_NickName_Map = new LinkedHashMap<>();
    private Map<Thread,Map<Socket,String>> threadMapMap = new LinkedHashMap<>();

    public ServerFrame() {

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
            try {
                ServerSocket server = new ServerSocket(12345);
                System.out.println("waiting ...");
                Thread t = new Thread(()->{
                    try {

                        for(int x = 0 ; x < 2 ; x++){
                            Socket socket = server.accept();
                            System.out.println("connected...");
                            Scanner sc = new Scanner(socket.getInputStream());
                            String nickName = sc.nextLine();
                            System.out.println("nicki = "+nickName);
                            Thread connectClinet = new Thread(new ConnectedClinet(socket,nickName));
                            socket_NickName_Map.put(socket,nickName);
                            threadMapMap.put(connectClinet,socket_NickName_Map);
                        }

                        for(int x = 0 ; x < 2 ; x++){
                            if(x == 0){
                                PrintWriter pr = new PrintWriter(((Socket)(socket_NickName_Map.keySet().toArray()[x])).getOutputStream());
                                pr.println("Start");
                                pr.flush();
                            }else{
                                PrintWriter pr = new PrintWriter(((Socket)(socket_NickName_Map.keySet().toArray()[x])).getOutputStream());
                                pr.println("Czekaj");
                                pr.flush();
                            }
                        }

                        for(Map.Entry<Thread,Map<Socket,String>> el: threadMapMap.entrySet()){
                            el.getKey().start();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    class ConnectedClinet implements Runnable{
        private Socket socket = null;
        private InputStream in = null;
        private OutputStream out = null;
        private Scanner sc = null;
        private PrintWriter pr = null;
        private String nickName = null;

        public ConnectedClinet(Socket socket,String nickName) throws IOException {

            this.socket = socket;
            this.nickName = nickName;

            in = socket.getInputStream();
            out = socket.getOutputStream();

            sc = new Scanner(in);
            pr = new PrintWriter(out);

        }

        @Override
        public void run() {
            while(true) {

                if (sc.hasNextLine()) {
                    String move = sc.nextLine();

                    for (Map.Entry<Socket, String> el : socket_NickName_Map.entrySet()) {
                        if (!el.getValue().equals(nickName)) {
                            PrintWriter pr = null;
                            try {
                                pr = new PrintWriter(el.getKey().getOutputStream());
                                pr.println(move);
                                pr.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
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
