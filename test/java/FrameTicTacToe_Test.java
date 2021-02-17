import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FrameTicTacToe_Test {
    static FrameTicTacToe frameTicTacToe = null;

    @Test
    @BeforeAll
    static void Initialization(){
        frameTicTacToe = new FrameTicTacToe();
    }

    @Test
    @DisplayName("test checking corrected insert ip variable")
    void checkCorrect_Ip_Port_NickName_Test(){
        Assertions.assertAll(
                ()->{ Assertions.assertTrue(frameTicTacToe.checkCorrect_Ip_Port_NickName("192.255.186.12","65100","Don Frye"));},
                ()->{ Assertions.assertFalse(frameTicTacToe.checkCorrect_Ip_Port_NickName("270.200.120.15","7000","Big Gorge"));}
        );
    }

    @Test
    @DisplayName("test return JTextField Class type")
    void getShowConnectionresult_Test(){
        Assertions.assertEquals(JTextField.class,FrameTicTacToe.getShowConnectionresult().getClass());
    }

    @Test
    @DisplayName("test return LinkedHashMap Class type")
    void getAllButtonGameMap_Test(){
        Assertions.assertEquals(LinkedHashMap.class,FrameTicTacToe.getAllButtonGameMap().getClass());
    }

    @Test
    @DisplayName("test return JLabel Class type")
    void getResultGameLabel(){

    }
}
