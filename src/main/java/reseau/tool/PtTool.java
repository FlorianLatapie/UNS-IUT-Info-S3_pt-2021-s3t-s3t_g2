package reseau.tool;

import reseau.packet.Packet;
import reseau.ptprotocol.PtProtocol;
import reseau.ptprotocol.PtValue;
import reseau.ptprotocol.PtValues;
import reseau.socket.NetWorkManager;
import reseau.type.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <h1> Outils pour le ptprotocol </h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class PtTool {
    private PtTool() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Permet de charger un paquet d'un protocol réseau
     *
     * @param path le chemin du fichier
     * @return le fichier en PtProtocol
     * @throws IOException si le fichier n'est pas accessible
     */
    public static PtProtocol buildPtPa(String path) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        int result = fileInputStream.read(buffer);
        if (result == -1)
            throw new IllegalArgumentException(MessageFormat.format("Le fichier {0} est vide !", path));
        fileInputStream.close();
        String[] str = new String(buffer, StandardCharsets.UTF_8).split("\n");

        PtValues ptValues = new PtValues();
        for (int i = 5; i < str.length; i++)
            if (str[i].contains("value:")) {
                String n = r(str[i + 1]);
                String t = r(str[i + 2]);
                String d = r(str[i + 3]);
                ptValues.addValue(new PtValue(n, t, d));
            }

        return new PtProtocol(r(str[0]), r(str[1]), r(str[2]), r(str[3]), r(str[4]), ptValues);
    }

    /**
     * Charge l'intégralité des paquets du protocol réseau
     *
     * @param path     chemin du dossier
     * @param protocol le type du protocol
     * @return l'ensemble des paquets
     * @throws IOException si un fichier n'est pas accessible
     */
    public static Map<String, Packet> loadPacket(String path, String protocol) throws IOException {
        File folder = new File(path);
        Map<String, Packet> packets = new HashMap<>();

        if (folder.isDirectory())
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.getName().endsWith(".ptprotocol")) {
                    PtProtocol pt = buildPtPa(file.getPath());

                    if (!pt.getProtocol().equals(protocol))
                        continue;
                    Packet packet = new Packet(pt);
                    packets.put(pt.getKeyword(), packet);
                }
            }

        return packets;
    }

    /**
     * Permet d'obtenir l'objet en chaine de caractere
     *
     * @param value variable
     * @param type  type de la variable
     * @return l'objet en chaine de caractere
     */
    public static String convertParamToString(Object value, String type) {
        try {
            switch (type) {
                case "String":
                    return ((String) value);
                case "int":
                    Integer i = ((Integer) value);
                    if (i < 0)
                        throw new IllegalArgumentException("La valeur a convertir est inferieur a 0");
                    return i.toString();
                case "VoteType":
                    VoteType vt = ((VoteType) value);
                    return vt.toString();
                case "VoteEtape":
                    VoteEtape ve = ((VoteEtape) value);
                    return ve.toString();
                case "VigileEtat":
                    VigileEtat vie = ((VigileEtat) value);
                    return vie.toString();
                case "TypeJoueur":
                    TypeJoueur tj = ((TypeJoueur) value);
                    return tj.toString();
                case "ReasonType":
                    ReasonType rt = ((ReasonType) value);
                    return rt.toString();
                case "PionType":
                    PionType pty = ((PionType) value);
                    return pty.toString();
                case "PionCouleur":
                    PionCouleur pc = ((PionCouleur) value);
                    return pc.toString();
                case "Couleur":
                    Couleur c = ((Couleur) value);
                    return c.toString();
                case "CondType":
                    CondType ct = ((CondType) value);
                    return ct.toString();
                case "CarteType":
                    CarteType cat = ((CarteType) value);
                    return cat.toString();
                case "TypePartie":
                    TypePartie tp = ((TypePartie) value);
                    return tp.toString();
                case "Status":
                    Status sss = ((Status) value);
                    return sss.toString();
                case "HashMap<PionType,List<Integer>>":
                    @SuppressWarnings("unchecked")
                    HashMap<PionType, List<Integer>> pti = (HashMap<PionType, List<Integer>>) value;
                    return PacketTool.subListToStr(pti);
                case "List<CarteType>":
                case "List<PionType>":
                case "List<Couleur>":
                case "List<PionCouleur>":
                    return PacketTool.listEnumToStr(value);
                case "List<Integer>":
                    @SuppressWarnings("unchecked")
                    List<Integer> il = (List<Integer>) value;
                    return PacketTool.listStrToInteger(il);
                case "List<String>":
                    @SuppressWarnings("unchecked")
                    List<String> il1 = (List<String>) value;
                    return PacketTool.listStrToStr(il1);
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permet de convertir une chaine de caractere en objet
     *
     * @param param le parametre
     * @param type  le type du parametre a retourner
     * @return l'object correspondant
     */
    public static Object convertStringToObject(String param, String type) {
        try {
            switch (type) {
                case "String":
                    return param;
                case "int":
                    return Integer.parseInt(param);
                case "VoteType":
                    return VoteType.valueOf(param);
                case "VoteEtape":
                    return VoteEtape.valueOf(param);
                case "VigileEtat":
                    return VigileEtat.valueOf(param);
                case "TypeJoueur":
                    return TypeJoueur.valueOf(param);
                case "ReasonType":
                    return ReasonType.valueOf(param);
                case "PionType":
                    return PionType.valueOf(param);
                case "PionCouleur":
                    return PionCouleur.valueOf(param);
                case "Couleur":
                    return Couleur.valueOf(param);
                case "CondType":
                    return CondType.valueOf(param);
                case "CarteType":
                    return CarteType.valueOf(param);
                case "TypePartie":
                    return TypePartie.valueOf(param);
                case "Status":
                    return Status.valueOf(param);
                case "HashMap<PionType,List<Integer>>":
                    return PacketTool.strToSubList(param);
                case "List<CarteType>":
                    return PacketTool.strToListEnum(param, CarteType.class);
                case "List<PionType>":
                    return PacketTool.strToListEnum(param, PionType.class);
                case "List<Couleur>":
                    return PacketTool.strToListEnum(param, Couleur.class);
                case "List<PionCouleur>":
                    return PacketTool.strToListEnum(param, PionCouleur.class);
                case "List<Integer>":
                    return PacketTool.strToListInteger(param);
                case "List<String>":
                    return PacketTool.strToListStr(param);
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtient le paquet via une chaine de caractere
     *
     * @param message        le message a convertir
     * @param netWorkManager le NetworkManager associé
     * @return le paquet associé
     */
    public static Packet strToPacketUdp(String message, NetWorkManager netWorkManager) {
        if (netWorkManager.getPacketsUdp().size() == 0)
            throw new IllegalArgumentException("NetWorkManager n'a pas ete initialise au moins une fois !");

        return netWorkManager.getPacketsUdp().get(PacketTool.getKeyFromMessage(message));
    }

    /**
     * Obtient le paquet via une chaine de caractere
     *
     * @param message        le message a convertir
     * @param netWorkManager le NetworkManager associé
     * @return le paquet associé
     */
    public static Packet strToPacketTcp(String message, NetWorkManager netWorkManager) {
        if (netWorkManager.getPacketsTcp().size() == 0)
            throw new IllegalArgumentException("NetWorkManager n'a pas ete initialise au moins une fois !");

        return netWorkManager.getPacketsTcp().get(PacketTool.getKeyFromMessage(message));
    }

    /**
     * Convertit une chaine de caractere
     *
     * @param v parametre
     * @return une chaine de caractere
     */
    private static String r(String v) {
        return v.split(":")[1].replace("\r", "");
    }
}
