import javax.swing.*;
import java.io.IOException;
import java.net.*;

public class Client {
    private String ipAddress = null;
    private String port = null;
    private String nickName = null;

    public Client(String ipAddress, String port, String nickName) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.nickName = nickName;

        Thread clientStartThread = new Thread(()->{
            connectToServer(ipAddress, Integer.valueOf(port));
        });
        clientStartThread.start();
    }

    private void connectToServer(String ipAddress,int port)  {
        String octets[] = ipAddress.split("\\.");
        byte bytes[] = new byte[]{(byte)Integer.parseInt(octets[0]),(byte)Integer.parseInt(octets[1]),(byte)Integer.parseInt(octets[2]),(byte)Integer.parseInt(octets[3])};
        try {
            InetAddress inet = InetAddress.getByAddress(bytes);
            Socket client = new Socket();
            FrameTicTacToe.getShowConnectionresult().setText("waiting ...");
            try {
                client.connect(new InetSocketAddress(inet, port));
                FrameTicTacToe.getShowConnectionresult().setText("");
                FrameTicTacToe.getShowConnectionresult().setText("connected");
            } catch (ConnectException connExc){
                JOptionPane.showMessageDialog(null,"Client connection: "+connExc.getMessage());
                FrameTicTacToe.getShowConnectionresult().setText("");
            }
        }catch(UnknownHostException unknowHostException){
            JOptionPane.showMessageDialog(null,"Unknown Host :"+unknowHostException.getMessage());
        }catch(IOException ioExc){
            JOptionPane.showMessageDialog(null,"IO Client Exception: "+ioExc.getMessage());
        }
    }
}
