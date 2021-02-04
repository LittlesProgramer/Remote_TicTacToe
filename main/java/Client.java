import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private String ipAddress = null;
    private int port = 0;

    public Client(String ipAddress, int port) throws IOException {
        this.ipAddress = ipAddress;
        this.port = port;


    }

    private void connectToServer(String ipAddress,int port) throws IOException {
        String octets[] = ipAddress.split("\\.");
        byte bytes[] = new byte[]{(byte)Integer.parseInt(octets[0]),(byte)Integer.parseInt(octets[1]),(byte)Integer.parseInt(octets[2]),(byte)Integer.parseInt(octets[3])};
        InetAddress inet = InetAddress.getByAddress(bytes);
        Socket client = new Socket();
        System.out.println("waiting ...");
        client.connect(new InetSocketAddress(inet,port));
        System.out.println("connecting ...");
    }
}
