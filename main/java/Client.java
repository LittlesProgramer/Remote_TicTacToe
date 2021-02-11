import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private static String ipAddress = null;
    private static String port = null;
    private static String nickName = null;
    private static boolean YOUR_FIRST_MOVE = false; //if this variable is true draw cross figure, else is circle figure.
    private static Scanner sc = null;
    private static PrintWriter pr = null;
    private static boolean IS_YOUR_TURN = false;

    public Client(String ipAddress, String port, String nickName) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.nickName = nickName;

        Thread clientStartThread = new Thread(()->{
            connectToServer(ipAddress, Integer.valueOf(port));
        });
        this.addActionLitenerIntoButtonTicTacToe();
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
                //client.connect(new InetSocketAddress(inet, port));
                client.connect(new InetSocketAddress(InetAddress.getLocalHost(),12345));

                sc = new Scanner(client.getInputStream());
                pr = new PrintWriter(client.getOutputStream());

                FrameTicTacToe.getShowConnectionresult().setText("");
                FrameTicTacToe.getShowConnectionresult().setText("connected");

                pr.println(nickName);
                pr.flush();

                String firstStartMove = sc.nextLine();
                System.out.println("start = "+firstStartMove);
                setFirstMove(firstStartMove);//set first move YOUR_FIRST_MOVE variable

                if(YOUR_FIRST_MOVE){
                    FrameTicTacToe.enabledAllButtonOn();
                    IS_YOUR_TURN = true;
                }else{
                    ruch(0);
                }


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

    public void ruch(int move) {

        if (IS_YOUR_TURN) {
            System.out.println("IS_YOU_TURN a = "+IS_YOUR_TURN);

            pr.println(move);
            pr.flush();
            IS_YOUR_TURN = false;

            FrameTicTacToe.enabledAllButtonOff();
        }

        System.out.println("IS_YOU_TURN b = "+IS_YOUR_TURN);

        if(sc.hasNextLine()){
            int receiveMove = Integer.valueOf(sc.nextLine());
            System.out.println("odebrany move = "+receiveMove);
            FrameTicTacToe.enabledAllButtonOn();

            for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){
                if(el.getValue() == receiveMove){
                    el.getKey().rysujFigure(getFigureType(IS_YOUR_TURN));
                    el.getKey().setEnabled(false);
                }
            }
            IS_YOUR_TURN = true;
        }

    }


    public static boolean getIsYourTurn(){ return IS_YOUR_TURN; }
    public static void setFirstMove(String whoMoveIsNow){//this method get from the server start move if get Start then you have first move
        if(whoMoveIsNow.equals("Start")){
            YOUR_FIRST_MOVE = true;
        }else{ YOUR_FIRST_MOVE = false; }
    }

    public static String getFigureType(boolean whatFigureIs){
        if(YOUR_FIRST_MOVE) {
            if (whatFigureIs) {
                return "Cross";
            } else {
                return "Circle";
            }
        }else{
            if (whatFigureIs) {
                return "Circle";
            } else {
                return "Cross";
            }
        }
    }

    //public static boolean whoFirstMove(){ return YOUR_FIRST_MOVE; }//this method return variable who those is responsible for first move cross or circle(TRUE=CROSS)
    public void addActionLitenerIntoButtonTicTacToe(){
        for(int x = 0 ; x < FrameTicTacToe.getAllButtonGameMap().size() ; x++){
            ButtonTicTacToe b = (ButtonTicTacToe) FrameTicTacToe.getAllButtonGameMap().keySet().toArray()[x];
            b.addActionListener(new YoursMoving(this,x+1));
        }
    }
}
