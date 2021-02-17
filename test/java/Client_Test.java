import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Client_Test {
    static Client c = null;

    @Test
    @BeforeAll
    static void Initialization_Client(){
        c = new Client("1.1.1.1","10000","CocoChannel");
    }

    @Test
    void isItWinningMove_Test(){
        // You or your opponent don't have any moves in listAllYourMove or listAllYourOpponentMove
        // variables therefore in this case we testing this method always give false result
        Assertions.assertEquals(false,c.isItWinningMove(false));
    }
}
