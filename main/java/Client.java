import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
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
    private static String winingString = "";//these variable store configuration all winned figures ( cross or circle)

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

            //add your move into map and checked your winning move
            addYourMoveToList(move);
            if(isItWinningMove(IS_YOUR_TURN)){
                System.out.println("you are winners");
                drawWinningLine(winingString,getFigureType(IS_YOUR_TURN));
                FrameTicTacToe.getResultGameLabel().setText("result your game: "+"you are winners");
                FrameTicTacToe.enabledAllButtonOff();
            }else {

                IS_YOUR_TURN = false;
                FrameTicTacToe.enabledAllButtonOff();
                FrameTicTacToe.getResultGameLabel().setText("result your game: " + "is your opponent move now");
            }
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

            //add your move opponent into map and checkeing your opponent is winner
            addYourOpponentMoveToList(receiveMove);
            if(isItWinningMove(IS_YOUR_TURN)){
                System.out.println("you opponent are winners");
                drawWinningLine(winingString,getFigureType(IS_YOUR_TURN));
                FrameTicTacToe.getResultGameLabel().setText("result your game: "+"you opponent are winners");
                FrameTicTacToe.enabledAllButtonOff();
            }else {
                IS_YOUR_TURN = true;
                FrameTicTacToe.getResultGameLabel().setText("result your game: " + "is your move now");
            }
        }

    }

    private void drawWinningLine(String winingStringMoves,String crossOrCircle) {
        //all winning configuration moves

        //horizontal pozition: 123,456,789
        String tab_Horizontal[] = new String[]{"123","456","789"};
        //vertical pozition: 147,258,369
        String tab_Vertival[] = new String[]{"147","258","369"};
        //cross pozition: 159,357
        String crossI = "159";
        String crossII = "357";

        ArrayList<String> listAllWinningString = new ArrayList<>();
        listAllWinningString.addAll(Arrays.asList(tab_Horizontal));
        listAllWinningString.addAll(Arrays.asList(tab_Vertival));
        listAllWinningString.add(crossI);
        listAllWinningString.add(crossII);

        for(String el: listAllWinningString){
            System.out.println("el: "+el);
        }

        for(String el: listAllWinningString){
            if(el.equals(winingStringMoves)){
                if("123456789".contains(el)){
                    chooseDirectionAndDrawingLine(winingStringMoves,crossOrCircle,"Horizontal");
                }

                if("147258369".contains(el)){
                    chooseDirectionAndDrawingLine(winingStringMoves,crossOrCircle,"Vertical");
                }

                if("159".contains(el)){
                    chooseDirectionAndDrawingLine(winingStringMoves,crossOrCircle,"Cross I");
                }

                if("357".contains(el)){
                    chooseDirectionAndDrawingLine(winingStringMoves,crossOrCircle,"Cross II");
                }
            }
        }

    }

    private void chooseDirectionAndDrawingLine(String winingStringMoves, String crossOrCircle,String dirctionLine) {
        for(Map.Entry<ButtonTicTacToe,Integer> buttons: FrameTicTacToe.getAllButtonGameMap().entrySet()){
            for(char chars: winingStringMoves.toCharArray()){
                int button = Integer.valueOf(String.valueOf(chars));
                if(button == buttons.getValue()){

                    if(crossOrCircle.equals("Cross")){
                        buttons.getKey().rysujFigure("Cross+"+dirctionLine);
                    }else{
                        buttons.getKey().rysujFigure("Circle+"+dirctionLine);
                    }
                }
            }
        }
    }

    private boolean isItWinningMove(boolean isYourTurn) {
        //if variable isYourTurn true we must checking all your moves else we checking all moves your opponent

        boolean win = false;//variable win == true if allYourOpponentMoveString contains one of allWinningConfigurationTab value;

        //all winning configuration moves
        //horizontal pozition: 123,456,789
        //vertical pozition: 147,258,369
        //cross pozition: 159,357
        String allWinningConfigurationTab[] = new String[]{"123","456","789","147","258","369","159","357"};
        String allYourMovesString = "";
        String allYourOpponentMoveString = "";

        if(isYourTurn){

            //add all you moves into allYourMovesString String type variable
            for(Integer el: listAllYourMove){
                allYourMovesString = allYourMovesString + String.valueOf(el);
            }

            //sorted all char in allYourMovesString
            char allYourMovesTab[] = allYourMovesString.toCharArray();
            Arrays.sort(allYourMovesTab);
            allYourMovesString = new String(allYourMovesTab);

            //checking that allYourMovesString contains one of the variable from allWinningConfigurationTab table
            for(String el : allWinningConfigurationTab){
                if(allYourMovesString.contains(el)){
                    winingString = el;
                    win = true;
                }
            }

        }else{

            //add all you moves into allYourMovesString String type variable
            for(Integer el: listAllYourOpponentMove){
                allYourOpponentMoveString = allYourOpponentMoveString + String.valueOf(el);
            }

            //sorted all char in allYourMovesString
            char allYourOpponentMovesTab[] = allYourOpponentMoveString.toCharArray();
            Arrays.sort(allYourOpponentMovesTab);
            allYourOpponentMoveString = new String(allYourOpponentMovesTab);

            //checking that allYourMovesString contains one of the variable from allWinningConfigurationTab table
            for(String el : allWinningConfigurationTab){
                if(allYourOpponentMoveString.contains(el)){
                    winingString = el;
                    win = true;
                }
            }
        }

        return win;
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
