import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private String ipAddress = null;
    private String port = null;
    private String nickName = null;
    private static Socket clientSocket = null;
    private static boolean YOUR_FIRST_MOVE = false; //if this variable is true draw cross figure, else is circle figure.

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
            clientSocket = client;

            FrameTicTacToe.getShowConnectionresult().setText("waiting ...");
            try {
                client.connect(new InetSocketAddress(inet, port));
                FrameTicTacToe.getShowConnectionresult().setText("");
                FrameTicTacToe.getShowConnectionresult().setText("connected");

                Scanner sc = new Scanner(client.getInputStream());
                String startMove = sc.nextLine();//get started move from server
                whoFirstMove(startMove);//set first move in YOUR_FIRST_MOVE variable get the first move from server
                firstMove();//drawing your's Cross or your's opponent

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

    public void firstMove(){//this method generate first move drawing Cross depending on return value whoFirstMove() method

        if(Client.whoWasFirstMove()){//if first move drawing your figure cross,now CROSS is YOUR BASE figure
            FrameTicTacToe.getResultGameLabel().setText("result your game: "+"Your first move cross");

        }else{//if first move doing your opponent he drawing cross ,and your BASE figure is CIRCLE

            for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){
                el.getKey().setEnabled(false);
            }

            FrameTicTacToe.getResultGameLabel().setText("result your game: "+"Your opponent move");

            try {

                Scanner scanner = new Scanner(clientSocket.getInputStream());
                for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){//draw move your opponent on the button in GamePanel
                    if(el.getValue() == Integer.valueOf(3)) {
                        el.getKey().rysujFigure("Cross");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void whoFirstMove(String whoMoveIsNow){//this method get from the server start move if get Start then you have first move
        if(whoMoveIsNow.equals("Start")){
            YOUR_FIRST_MOVE = true;
        }else{ YOUR_FIRST_MOVE = false; }
    }

    public static boolean whoWasFirstMove(){ return YOUR_FIRST_MOVE; }//this method return variable who those is responsible for first move cross or circle(TRUE=CROSS)
}
