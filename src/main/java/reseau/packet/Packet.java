package reseau.packet;

import reseau.ptprotocol.PtProtocol;
import reseau.ptprotocol.PtValue;
import reseau.tool.PacketTool;
import reseau.tool.PtTool;

import java.text.MessageFormat;

/**
 * <h1> Permet de creer des paquets </h1>
 * Il permet de construire un protocol
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public class Packet {
    final String key;
    String[] paras;
    final String[] names;
    final String[] types;
    final String[] docs;
    final PtProtocol ptProtocol;
    final int argCount;

    /**
     * @param ptProtocol un ptprotocol contenant les informations pour ce nouveau paquet
     */
    public Packet(PtProtocol ptProtocol) {
        this.ptProtocol = ptProtocol;
        this.key = ptProtocol.getKeyword();
        this.paras = new String[ptProtocol.valueCount()];
        this.names = ptProtocol.toNameArray();
        this.types = ptProtocol.toTypeArray();
        this.docs = ptProtocol.toDocArray();
        this.argCount = ptProtocol.valueCount();
    }

    /**
     * Permet de contruire le message sous forme d'une chaine de caractere pour l'envoie a partir de valeurs
     *
     * @param param les parametres necessaire pour construire le packet
     * @return le message pret a etre envoyé
     * @throws IllegalArgumentException si le nombre de parametre n'est pas correcte
     * @throws IllegalArgumentException si l'un des parametres n'est pas du bon type
     */
    public String build(Object... param) {
        if (param.length != argCount)
            throw new IllegalArgumentException("[" + key + "] Le nombre de parametre n'est pas correcte !\n" + getDocs());

        StringBuilder tmp = new StringBuilder(key + "-");
        for (int i = 0; i < param.length; i++) {
            String res = PtTool.convertParamToString(param[i], types[i]);
            if (res == null)
                throw new IllegalArgumentException(MessageFormat.format("[{0}] {1} {2} n''est pas un type {3} !\n{4}", key, i, param[i], types[i], getDocs()));

            tmp.append(res).append("-");
        }

        return tmp.substring(0, tmp.length() - 1);
    }

    /**
     * Permet a partir d'une chaine de caractere d'obtenir les valeurs du message
     *
     * @param message le message du protocol réseau
     * @return les valeurs du message (Il faut cast selon le bon type (.getDocs()))
     * @throws IllegalArgumentException si le nombre de parametre n'est pas correcte
     * @throws IllegalArgumentException si l'un des parametres n'est pas du bon type
     */
    public Object[] getValues(String message) {
        String[] tmp = message.split("-");

        if (tmp.length != argCount + 1)
            throw new IllegalArgumentException(MessageFormat.format("[{0}] Le nombre de parametre n''est pas correcte !", key));

        Object[] obj = new Object[tmp.length];
        for (int i = 1; i < tmp.length; i++) {
            obj[i] = PtTool.convertStringToObject(tmp[i], types[i - 1]);
            if (obj[i] == null)
                throw new IllegalArgumentException(MessageFormat.format("[{0}] {1} n''est pas un type {2} !", key, tmp[i], types[i - 1]));
        }

        return obj;
    }

    /**
     * Permet a partir d'une chaine de caractere d'obtenir les valeurs du message
     *
     * @param message le message du protocol réseau
     * @param argNum  numero de l'argument
     * @return les valeurs du message (Il faut cast selon le bon type (.getDocs()))
     * @throws IllegalArgumentException si le nombre de parametre n'est pas correcte
     * @throws IllegalArgumentException si l'un des parametres n'est pas du bon type
     */
    public Object getValue(String message, int argNum) {
        String[] tmp = message.split("-");
        if (tmp.length < argNum || argNum <= 0)
            throw new IllegalArgumentException(MessageFormat.format("[{0}] L''index est en dehors du tableau {1} !\n{2}", key, argNum, getDocs()));

        Object obj = PtTool.convertStringToObject(tmp[argNum], types[argNum - 1]);
        if (obj == null)
            throw new IllegalArgumentException(MessageFormat.format("[{0}] {1} n''est pas un type {2} !", key, tmp[argNum], types[argNum - 1]));

        return obj;
    }

    /**
     * Permet d'obtenir la documentation (nom, type et doc) du paquet courant
     *
     * @return la documentation
     */
    public String getDocs() {
        StringBuilder tmp = new StringBuilder("Nom : " + ptProtocol.getClassName() + "\nDoc : " + ptProtocol.getDoc() + "\n");
        int i = 1;
        for (PtValue doc : ptProtocol.getValues().getValues())
            tmp.append("[").append(i++).append("]").append(doc).append("\n");

        return tmp.toString();
    }

    /**
     * Permet d'identifier un packet via son mot-clé
     *
     * @param key le mot clé identifiant le paquet ou le message
     * @return si le mot-clé correspond a celui du paquet
     */
    public boolean isPacket(String key) {
        if (key.contains("-"))
            return this.key.equals(PacketTool.getKeyFromMessage(key));
        return this.key.equals(key);
    }

    /**
     * Obtient le mot-clé
     *
     * @return le mot-clé
     */
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return ptProtocol.toString();
    }
}
