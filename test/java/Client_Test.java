import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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

    @Test
    void getFigureType_Test(){
        // if YOUR_FIRST_MOVE = (default is false) and whatFigureIs = true then your figur type is Circle
        // else if whatFigureIs = false, then your figur type is Cross
        Assertions.assertEquals("Circle",Client.getFigureType(true));
    }

    @Test
    @DisplayName("Test add your move to List")
    void addMoveToList_Test() throws NoSuchFieldException, IllegalAccessException {
        int sizeMap = 1; //default sizeList is 0
        int addValueIntoMap = 5; //add value to list

        Class obj = c.getClass();
        Field field = obj.getDeclaredField("listAllYourMove");
        field.setAccessible(true);
        List<Integer> listTest = (List<Integer>)field.get(obj);
        listTest.add(addValueIntoMap);

        Assertions.assertAll(
                ()->{Assertions.assertEquals(sizeMap,listTest.size());},
                ()->{Assertions.assertEquals(addValueIntoMap,(int)listTest.get(0));}
        );

    }

    @Test
    @DisplayName("Test add your opponent move to List")
    void listAllYourOpponentMove_Test(){
        Class obj = c.getClass();

    }

}
