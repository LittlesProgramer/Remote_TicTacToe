import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

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

                System.out.println("1");
                String firstStartMove = sc.nextLine();
                System.out.println("2 = "+firstStartMove);
                setFirstMove(firstStartMove);//set first move YOUR_FIRST_MOVE variable


                if(YOUR_FIRST_MOVE){
                    FrameTicTacToe.getResultGameLabel().setText("result your game: "+"Your first move cross");
                    Thread.sleep(500);
                    FrameTicTacToe.enabledAllButtonOn();
                    IS_YOUR_TURN = false;

                    while(true){
                        //System.out.println("petla");
                        if(IS_YOUR_TURN){
                            break;
                        }
                    }
                    System.out.println("opusczono pętle");

                }else{

                    FrameTicTacToe.getResultGameLabel().setText("result your game: "+"Your opponent first move");
                    FrameTicTacToe.enabledAllButtonOff();
                    Thread.sleep(500);
                    String yourOpponentMoveNumber = sc.nextLine();
                    System.out.println("otrzymany ruch to = "+yourOpponentMoveNumber+" ale nie z nicku: "+nickName);
                    logicMove(Integer.parseInt(yourOpponentMoveNumber));
                }


                while(true) {

                    if(IS_YOUR_TURN){

                        FrameTicTacToe.getResultGameLabel().setText("result your game: "+"Your move");
                        Thread.sleep(500);
                        FrameTicTacToe.enabledAllButtonOn();
                        IS_YOUR_TURN = false;

                        while(true){
                            //System.out.println("petla");
                            if(IS_YOUR_TURN){
                                break;
                            }
                        }
                        IS_YOUR_TURN = false;

                    }else{

                        FrameTicTacToe.getResultGameLabel().setText("result your game: "+"Your opponent move");
                        FrameTicTacToe.enabledAllButtonOff();
                        Thread.sleep(500);
                        String yourOpponentMoveNumber = sc.nextLine();
                        logicMove(Integer.parseInt(yourOpponentMoveNumber));

                        IS_YOUR_TURN = true;

                    }

                }

            } catch (ConnectException connExc){
                JOptionPane.showMessageDialog(null,"Client connection: "+connExc.getMessage());
                FrameTicTacToe.getShowConnectionresult().setText("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch(UnknownHostException unknowHostException){
            JOptionPane.showMessageDialog(null,"Unknown Host :"+unknowHostException.getMessage());
        }catch(IOException ioExc){
            JOptionPane.showMessageDialog(null,"IO Client Exception: "+ioExc.getMessage());
        }
    }

    public static void logicMove(int move) {//this method generate first move drawing Cross depending on return value whoFirstMove() method

        if(IS_YOUR_TURN){//if first move drawing your figure cross,now CROSS is YOUR BASE figure

            String moves = String.valueOf(move);
            pr.println(moves);
            pr.flush();
            System.out.println("Clinet = "+nickName+" wyslal = "+moves);

            FrameTicTacToe.enabledAllButtonOff();
            IS_YOUR_TURN = false;


        }else{//if first move doing your opponent he drawing cross ,and your BASE figure is CIRCLE

            for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){//draw move your opponent on the button in GamePanel
                if(el.getValue() == Integer.valueOf(move)) { //3 is temp move your opponent after must be automatic move
                    el.getKey().rysujFigure(Client.getFigureType());
                }
            }
            IS_YOUR_TURN = true;
        }
    }

    public static void setFirstMove(String whoMoveIsNow){//this method get from the server start move if get Start then you have first move
        if(whoMoveIsNow.equals("Start")){
            YOUR_FIRST_MOVE = true;
        }else{ YOUR_FIRST_MOVE = false; }
    }

    public static String getFigureType(){
        if(YOUR_FIRST_MOVE){
            return "Cross";
        }else{
            return "Circle";
        }
    }

    public static void setIsYourTurn(){ IS_YOUR_TURN = true; }
    //public static boolean whoFirstMove(){ return YOUR_FIRST_MOVE; }//this method return variable who those is responsible for first move cross or circle(TRUE=CROSS)
    public static Scanner getScanner(){ return sc; }
    public static PrintWriter getPrintWriter(){ return pr; }
}
