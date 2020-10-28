package reseau.tool;

import reseau.type.PionType;

import java.util.*;

/**
 * <h1> Outils pour les paquets </h1>
 * Permet de facilité la décapsulation de certains message
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class PacketTool {
    private PacketTool() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Transforme une chaine de caractere en liste
     *
     * @param list     liste a convertir
     * @param enumType le type l'énumeration de la liste
     * @param <T>      le type
     * @return une chaine de caractere en liste
     */
    public static <T extends Enum<T>> List<T> strToListEnum(String list, Class<T> enumType) {
        if (list.equals(" "))
            return new ArrayList<>();

        String[] tmp = list.split(",");
        List<T> temp = new ArrayList<>();
        for (String t : tmp)
            temp.add(Enum.valueOf(enumType, t));

        return temp;
    }

    /**
     * Transforme une chaine de caractere en liste d'entier
     *
     * @param list liste a convertir
     * @return une chaine de caractere en liste d'entier
     * @throws IllegalArgumentException si un entier est inferieur a 0
     */
    public static List<Integer> strToListInteger(String list) {
        String[] tmp = list.split(",");

        if (list.equals(" "))
            return new ArrayList<>();

        List<Integer> temp = new ArrayList<>();
        for (String t : tmp) {
            int tmp1 = Integer.parseInt(t);
            if (tmp1 < 0)
                throw new IllegalArgumentException("Ne peut pas etre inferieur a 0");
            temp.add(tmp1);
        }

        return temp;
    }

    /**
     * Transforme une chaine de caractere en liste de chaine de caractere
     *
     * @param list liste a convertir
     * @return une chaine de caractere en liste de chaine de caractere
     */
    public static List<String> strToListStr(String list) {
        String[] tmp = list.split(",");

        if (list.equals(" "))
            return new ArrayList<>();

        List<String> temp = new ArrayList<>();
        for (String t : tmp)
            temp.add(t);

        return temp;
    }

    /**
     * Transforme une liste reseau.enum en chaine de caractere
     *
     * @param l   list liste a convertir
     * @param <T> le type
     * @return une liste reseau.enum en chaine de caractere
     */
    public static <T extends Enum<T>> String listEnumToStr(Object l) {
        StringBuilder tmp = new StringBuilder();
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) l;

        if (list.isEmpty())
            return " ";

        for (T t : list)
            tmp.append(t).append(",");

        return tmp.substring(0, tmp.length() - 1);
    }

    /**
     * Transforme une liste d'entier en chaine de caractere
     *
     * @param list list liste a convertir
     * @return une liste d'entier en chaine de caractere
     * @throws IllegalArgumentException si un entier est inferieur a 0
     */
    public static String listStrToInteger(List<Integer> list) {
        if (list.isEmpty())
            return " ";

        StringBuilder tmp = new StringBuilder();

        for (int t : list) {
            if (t < 0)
                throw new IllegalArgumentException("Ne peut pas etre inferieur a 0");
            tmp.append(t).append(",");
        }

        return tmp.substring(0, tmp.length() - 1);
    }

    /**
     * Transforme une liste de chaine de caractere en chaine de caractere
     *
     * @param list list liste a convertir
     * @return une liste de chaine de caractere en chaine de caractere
     */
    public static String listStrToStr(List<String> list) {
        if (list.isEmpty())
            return " ";

        StringBuilder tmp = new StringBuilder();

        for (String t : list)
            tmp.append(t).append(",");

        return tmp.substring(0, tmp.length() - 1);
    }

    /**
     * Transforme un hashmap en chaine de caractere
     *
     * @param list hashmap a convertir
     * @return un hashmap en chaine de caractere
     */
    public static String subListToStr(Map<PionType, List<Integer>> list) {
        if (list.isEmpty())
            return " ";

        StringBuilder tmp = new StringBuilder();
        for (Map.Entry<PionType, List<Integer>> pion : list.entrySet()) {
            if (pion.getValue().isEmpty())
                continue;
            tmp.append(pion.getKey()).append(":");
            for (Integer dest : pion.getValue())
                tmp.append(dest).append(",");
            tmp = new StringBuilder(tmp.substring(0, tmp.length() - 1));
            tmp.append(";");
        }
        tmp = new StringBuilder(tmp.substring(0, tmp.length() - 1));

        return tmp.toString();
    }

    /**
     * Transforme une chaine de caractere en hashmap
     *
     * @param message une chaine de caractere
     * @return une chaine de caractere en hashmap
     */
    public static HashMap<PionType, List<Integer>> strToSubList(String message) {
        if (message.equals(" "))
            return new HashMap<>();

        HashMap<PionType, List<Integer>> sublist = new HashMap<>();
        String[] sublist1 = message.split(";");
        for (String sl : sublist1) {
            String[] sublist2 = sl.split(":");
            String[] sublist3 = sublist2[1].split(",");
            List<Integer> dest = new ArrayList<>();
            for (String i : sublist3)
                dest.add(Integer.parseInt(i));
            sublist.put(PionType.valueOf(sublist2[0]), dest);
        }

        return sublist;
    }

    /**
     * Permet d'obtenir le mot-clé du paquet avec un message
     *
     * @param message le message recu
     * @return le mot-clé du paquet
     */
    public static String getKeyFromMessage(String message) {
        if (message.isEmpty())
            throw new IllegalArgumentException("Ne peut pas etre vide !");

        if (message.startsWith("-"))
            throw new IllegalArgumentException("Le mot-clé ne peut pas etre vide !");

        if (!message.contains("-"))
            return message;

        String[] tmp = message.split("-");

        return tmp[0];
    }
}
