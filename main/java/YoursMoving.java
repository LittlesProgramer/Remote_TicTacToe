import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class YoursMoving implements ActionListener {

    private static List<Integer> listAllYourMove = new LinkedList<>();
    private static List<Integer> listAllYourOpponentMove = new LinkedList<>();
    private int locationButton = 0;

    public YoursMoving(int locationButton) {
        this.locationButton = locationButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Hello moje pysie = "+locationButton);

        for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){
            if(el.getValue() == locationButton){
                System.out.println("rysuje a co KURWA !!!!! :)"+locationButton);
                el.getKey().rysujFigure(Client.getFigureType());
            }
        }

        Client.setIsYourTurn();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Client.logicMove(locationButton);

    }

    public static void addMoveIntoListAllYourMove(int move){
        listAllYourMove.add(move);
    }
    public static void addMoveIntoListAllYourOpponentMove(int move){
        listAllYourOpponentMove.add(move);
    }
}
