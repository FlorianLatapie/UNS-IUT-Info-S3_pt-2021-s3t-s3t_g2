package reseau.tool;

import org.junit.jupiter.api.Test;
import reseau.type.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacketToolTest {

    @Test
    void strToListEnum() {
        List<Status> expected = new ArrayList<>();
        expected.add(Status.ANNULEE);
        expected.add(Status.COMPLETE);
        expected.add(Status.TERMINE);
        String message = "ANNULEE,COMPLETE,TERMINE";
        assertEquals(expected, PacketTool.strToListEnum(message, Status.class));
        expected = new ArrayList<>();
        assertNotEquals(expected, PacketTool.strToListEnum(message, Status.class));

        List<Status> expected1 = new ArrayList<>();
        message = " ";
        assertEquals(expected1, PacketTool.strToListEnum(message, Status.class));

        String message1 = "Annule,az";
        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListEnum(message1, Status.class));
    }

    @Test
    void strToListInteger() {
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        String message = "0,1,2,3";
        assertEquals(expected, PacketTool.strToListInteger(message));
        expected = new ArrayList<>();
        assertNotEquals(expected, PacketTool.strToListInteger(message));

        List<Integer> expected1 = new ArrayList<>();
         message = " ";
        assertEquals(expected1, PacketTool.strToListInteger(message));

        String message1 = "Annule,az";
        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListInteger(message1));

        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListInteger("-1,-2"));
        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListInteger("-1,0"));
        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListInteger("0,-1"));
    }

    @Test
    void strToListStr() {
        List<String> expected = new ArrayList<>();
        expected.add("Yo");
        expected.add("123");
        expected.add("Po");
        expected.add("pm");
        String message = "Yo,123,Po,pm";
        assertEquals(expected, PacketTool.strToListStr(message));
        expected = new ArrayList<>();
        assertNotEquals(expected, PacketTool.strToListStr(message));

        List<String> expected1 = new ArrayList<>();
        message = " ";
        assertEquals(expected1, PacketTool.strToListStr(message));
    }

    @Test
    void listEnumToStr() {
        List<Status> actual = new ArrayList<>();
        actual.add(Status.ANNULEE);
        actual.add(Status.COMPLETE);
        actual.add(Status.TERMINE);
        String expected = "ANNULEE,COMPLETE,TERMINE";
        assertEquals(expected, PacketTool.listEnumToStr(actual));
        expected = "";
        assertNotEquals(expected, PacketTool.listEnumToStr(actual));

        List<Status> actual1 = new ArrayList<>();
        String expected1 = " ";
        assertEquals(expected1, PacketTool.listEnumToStr(actual1));
    }

    @Test
    void listStrToInteger() {
        List<Integer> actual = new ArrayList<>();
        actual.add(9);
        actual.add(5);
        actual.add(1);
        String expected = "9,5,1";
        assertEquals(expected, PacketTool.listStrToInteger(actual));
        expected = "";
        assertNotEquals(expected, PacketTool.listStrToInteger(actual));

        List<Integer> actual1 = new ArrayList<>();
        String expected1 = " ";
        assertEquals(expected1, PacketTool.listStrToInteger(actual1));

        List<Integer> actual2 = new ArrayList<>();
        actual2.add(-9);
        assertThrows(IllegalArgumentException.class, () -> PacketTool.listStrToInteger(actual2));
    }

    @Test
    void listStrToStr() {
        List<String> actual = new ArrayList<>();
        actual.add("Oui");
        actual.add("5");
        actual.add("Bof");
        String expected = "Oui,5,Bof";
        assertEquals(expected, PacketTool.listStrToStr(actual));
        expected = "";
        assertNotEquals(expected, PacketTool.listStrToStr(actual));

        List<String> actual1 = new ArrayList<>();
        String expected1 = " ";
        assertEquals(expected1, PacketTool.listStrToStr(actual1));
    }

    @Test
    void subListToStr() {
        HashMap<Integer, List<Integer>> actual = new HashMap<>();
        List<Integer> blonde = new ArrayList<>();
        blonde.add(5);
        actual.put(7, blonde);
        List<Integer> brute = new ArrayList<>();
        brute.add(2);
        actual.put(5, brute);
        List<Integer> truand = new ArrayList<>();
        truand.add(3);
        truand.add(5);
        actual.put(3, truand);
        List<Integer> filette = new ArrayList<>();
        filette.add(1);
        actual.put(1, filette);
        String expected = "BLONDE:5;BRUTE:2;TRUAND:3,5;FILETTE:-1";
        //Assertions.assertEquals(expected, PacketTool.subListToStr(actual));

        HashMap<Integer, List<Integer>> actual1 = new HashMap<>();
        String expected1 = " ";
        assertEquals(expected1, PacketTool.subListToStr(actual1));

        expected = "BLONDE:5;BRUTE:2;TRUAND:3,5";
        assertNotEquals(expected, PacketTool.subListToStr(actual));

        String message1 = "Annule,az";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> PacketTool.strToSubList(message1));
    }

    @Test
    void strToSubList() {
        HashMap<Integer, List<Integer>> expected = new HashMap<>();
        List<Integer> blonde = new ArrayList<>();
        blonde.add(5);
        expected.put(7, blonde);
        List<Integer> brute = new ArrayList<>();
        brute.add(2);
        expected.put(5, brute);
        List<Integer> truand = new ArrayList<>();
        truand.add(3);
        truand.add(5);
        expected.put(3, truand);
        List<Integer> filette = new ArrayList<>();
        filette.add(1);
        expected.put(1, filette);
        String message = "7:5;5:2;3:3,5;1:1";
        assertEquals(expected, PacketTool.strToSubList(message));

        HashMap<Integer, List<Integer>> expected1 = new HashMap<>();
        String message2 = " ";
        assertEquals(expected1, PacketTool.strToSubList(message2));

        message = "7:5;5:2;3:3,5";
        assertNotEquals(expected, PacketTool.strToSubList(message));

        String message1 = "Annule,az";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> PacketTool.strToSubList(message1));
    }

    @Test
    void getKeyFromMessage() {
        String expected = "ACP";
        assertEquals(expected, PacketTool.getKeyFromMessage("ACP-op-456"));
        assertEquals(expected, PacketTool.getKeyFromMessage("ACP"));
        assertNotEquals(expected, PacketTool.getKeyFromMessage("ACPa"));
        assertNotEquals(expected, PacketTool.getKeyFromMessage("ECP"));

        assertThrows(IllegalArgumentException.class, () -> PacketTool.getKeyFromMessage(""));

        assertThrows(IllegalArgumentException.class, () -> PacketTool.getKeyFromMessage("-"));
    }
}
