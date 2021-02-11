import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class YoursMoving implements ActionListener {

    private Client client = null;
    private static List<Integer> listAllYourMove = new LinkedList<>();
    private static List<Integer> listAllYourOpponentMove = new LinkedList<>();
    private int locationButton = 0;

    public YoursMoving(Client client,int locationButton) {
        this.client = client;
        this.locationButton = locationButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){
            if(el.getValue() == locationButton){
                el.getKey().rysujFigure(Client.getFigureType());

                try {
                    client.ruch(locationButton);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
