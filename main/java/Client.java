import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Client {
    private static String ipAddress = null;
    private static String port = null;
    private static String nickName = null;
    private static boolean YOUR_FIRST_MOVE = false; //if this variable is true draw cross figure, else is circle figure.
    private static Scanner sc = null;
    private static PrintWriter pr = null;
    private static boolean IS_YOUR_TURN = false;

    private static List<Integer> listAllYourMove = new LinkedList<>();
    private static List<Integer> listAllYourOpponentMove = new LinkedList<>();

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
                client.connect(new InetSocketAddress(inet, port));

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
                    FrameTicTacToe.getResultGameLabel().setText("result your game: "+"is your move now");
                    IS_YOUR_TURN = true;
                }else{
                    FrameTicTacToe.getResultGameLabel().setText("result your game: "+"is your opponent move now");
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

        //is you turn now
        if (IS_YOUR_TURN) {
            FrameTicTacToe.getResultGameLabel().setText("result your game: "+"is your move now");


            pr.println(move);
            pr.flush();
            IS_YOUR_TURN = false;

            //add your move into map and checked your winning
            addYourMoveToList(move);
            if(isItWinningMove(IS_YOUR_TURN))

            FrameTicTacToe.enabledAllButtonOff();
            FrameTicTacToe.getResultGameLabel().setText("result your game: "+"is your opponent move now");
        }

        //is your opponent move now
        if(sc.hasNextLine()){
            int receiveMove = Integer.valueOf(sc.nextLine());
            FrameTicTacToe.enabledAllButtonOn();

            for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){
                if(el.getValue() == receiveMove){
                    el.getKey().rysujFigure(getFigureType(IS_YOUR_TURN));
                    el.getKey().setEnabled(false);
                }
            }
            IS_YOUR_TURN = true;
            FrameTicTacToe.getResultGameLabel().setText("result your game: "+"is your move now");
        }

    }

    private boolean isItWinningMove(boolean isYourTurn) {
        //if isYourTurn variable true we analized listAllYourMove map otherwise listAllYourOpponentMove.

    }

    private static void setFirstMove(String whoMoveIsNow){//this method get from the server start move if get Start then you have first move
        if(whoMoveIsNow.equals("Start")){
            YOUR_FIRST_MOVE = true; // if YOURS_FIRST_MOVE is true you have cross figure
        }else{ YOUR_FIRST_MOVE = false; } // else circle figure
    }
    protected static boolean getIsYourTurn(){ return IS_YOUR_TURN; } //this method return true if is your move or false if your opponent

    protected static String getFigureType(boolean whatFigureIs){
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
    private void addActionLitenerIntoButtonTicTacToe(){
        for(int x = 0 ; x < FrameTicTacToe.getAllButtonGameMap().size() ; x++){
            ButtonTicTacToe b = (ButtonTicTacToe) FrameTicTacToe.getAllButtonGameMap().keySet().toArray()[x];
            b.addActionListener(new YoursMoving(this,x+1));
        }
    }
    private void addYourMoveToList(int yourMove){ listAllYourMove.add(yourMove); }
    private void addYourOpponentMoveToList(int yourOpponentMove){ listAllYourOpponentMove.add(yourOpponentMove); }
}
