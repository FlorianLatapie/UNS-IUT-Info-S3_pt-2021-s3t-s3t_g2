package reseau.tool;

import org.junit.jupiter.api.Test;
import reseau.type.PionType;
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

        String message1 = "Annule,az";
        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListEnum(message1, Status.class));
    }

    @Test
    void strToListInteger() {
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(-3);
        String message = "0,1,2,-3";
        assertEquals(expected, PacketTool.strToListInteger(message));
        expected = new ArrayList<>();
        assertNotEquals(expected, PacketTool.strToListInteger(message));

        String message1 = "Annule,az";
        assertThrows(IllegalArgumentException.class, () -> PacketTool.strToListInteger(message1));
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
    }

    @Test
    void listStrToInteger() {
        List<Integer> actual = new ArrayList<>();
        actual.add(-9);
        actual.add(5);
        actual.add(1);
        String expected = "-9,5,1";
        assertEquals(expected, PacketTool.listStrToInteger(actual));
        expected = "";
        assertNotEquals(expected, PacketTool.listStrToInteger(actual));
    }

    @Test
    void subListToStr() {
        HashMap<PionType, List<Integer>> actual = new HashMap<>();
        List<Integer> blonde = new ArrayList<>();
        blonde.add(5);
        actual.put(PionType.BLONDE, blonde);
        List<Integer> brute = new ArrayList<>();
        brute.add(2);
        actual.put(PionType.BRUTE, brute);
        List<Integer> truand = new ArrayList<>();
        truand.add(3);
        truand.add(5);
        actual.put(PionType.TRUAND, truand);
        List<Integer> filette = new ArrayList<>();
        filette.add(-1);
        actual.put(PionType.FILETTE, filette);
        String expected = "BLONDE:5;BRUTE:2;TRUAND:3,5;FILETTE:-1";
        //Assertions.assertEquals(expected, PacketTool.subListToStr(actual));

        expected = "BLONDE:5;BRUTE:2;TRUAND:3,5";
        assertNotEquals(expected, PacketTool.subListToStr(actual));

        String message1 = "Annule,az";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> PacketTool.strToSubList(message1));
    }

    @Test
    void strToSubList() {
        HashMap<PionType, List<Integer>> expected = new HashMap<>();
        List<Integer> blonde = new ArrayList<>();
        blonde.add(5);
        expected.put(PionType.BLONDE, blonde);
        List<Integer> brute = new ArrayList<>();
        brute.add(2);
        expected.put(PionType.BRUTE, brute);
        List<Integer> truand = new ArrayList<>();
        truand.add(3);
        truand.add(5);
        expected.put(PionType.TRUAND, truand);
        List<Integer> filette = new ArrayList<>();
        filette.add(-1);
        expected.put(PionType.FILETTE, filette);
        String message = "BLONDE:5;BRUTE:2;TRUAND:3,5;FILETTE:-1";
        assertEquals(expected, PacketTool.strToSubList(message));

        message = "BLONDE:5;BRUTE:2;TRUAND:3,5";
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