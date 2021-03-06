import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class YoursMoving implements ActionListener {

    private Client client = null;
    private int locationButton = 0;

    public YoursMoving(Client client,int locationButton) {
        this.client = client;
        this.locationButton = locationButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(Map.Entry<ButtonTicTacToe,Integer> el: FrameTicTacToe.getAllButtonGameMap().entrySet()){
            if(el.getValue() == locationButton){
                el.getKey().rysujFigure(Client.getFigureType(Client.getIsYourTurn()));

                Thread t = new Thread(()->{
                    client.ruch(locationButton);
                });
                t.start();
                t.interrupt();
            }
        }

        //System.out.println("location BUTTONING: "+locationButton);
    }

}
