package reseau.tool;

import reseau.packet.Packet;
import reseau.ptprotocol.PtProtocol;
import reseau.ptprotocol.PtValue;
import reseau.ptprotocol.PtValues;
import reseau.socket.ControleurReseau;
import reseau.type.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

/**
 * <h1>Outils pour le ptprotocol</h1>
 *
 * @author Sébastien Aglaé
 * @version 1.0
 */
public abstract class PtOutils {
	private static final String PATHTOPACKET = "src\\main\\java\\reseau\\socket\\definition";

	private PtOutils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Permet de charger un paquet d'un protocol réseau.
	 *
	 * @param chemin Le chemin du fichier
	 * @return Le fichier en PtProtocol
	 * @throws IOException Si le fichier n'est pas accessible
	 */
	public static PtProtocol buildPtPa(String chemin) throws IOException {
		File file = new File(chemin);
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		int result = fileInputStream.read(buffer);
		if (result == -1)
			throw new IllegalArgumentException(MessageFormat.format("Le fichier {0} est vide !", chemin));
		fileInputStream.close();
		String[] str = new String(buffer, StandardCharsets.UTF_8).split("\n");

		PtValues ptValues = new PtValues();
		for (int i = 5; i < str.length; i++)
			if (str[i].contains("value:")) {
				String n = format(str[i + 1]);
				String t = format(str[i + 2]);
				String d = format(str[i + 3]);
				ptValues.ajouterValeur(new PtValue(n, t, d));
			}

		return new PtProtocol(format(str[0]), format(str[1]), format(str[2]), format(str[3]), format(str[4]), ptValues);
	}

	/**
	 * Charge l'intégralité des paquets du protocol réseau.
	 *
	 * @param chemin     Chemin du dossier
	 * @param protocole Le type du protocol
	 * @return L'ensemble des paquets
	 * @throws IOException Si un fichier n'est pas accessible
	 */
	public static Map<String, Packet> loadPacket(String chemin, String protocole) throws IOException {
		Map<String, Packet> packets = new HashMap<>();

		File folder = new File(PATHTOPACKET);
		if (folder.exists()) {
			folder = new File(PATHTOPACKET);
			for (File file : Objects.requireNonNull(folder.listFiles())) {
				if (file.getName().endsWith(".ptprotocol")) {
					PtProtocol pt = buildPtPa(file.getPath());

					if (!pt.getProtocole().equals(protocole))
						continue;
					Packet packet = new Packet(pt);
					packets.put(pt.getMotCle(), packet);
				}
			}
		} else
			for (File file : getResourceFolderFiles(chemin)) {
				if (file.getName().endsWith(".ptprotocol")) {
					PtProtocol pt = buildPtPa(file.getPath());

					if (!pt.getProtocole().equals(protocole))
						continue;
					Packet packet = new Packet(pt);
					packets.put(pt.getMotCle(), packet);
				}
			}

		return packets;
	}

	private static File[] getResourceFolderFiles(String dossier) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(dossier);
		String path = url.getPath();

		return new File(path).listFiles();
	}

	/**
	 * Permet d'obtenir l'objet en chaine de caractere.
	 *
	 * @param valeur Variable
	 * @param type  Type de la variable
	 * @return L'objet en chaine de caractere
	 */
	public static String convertParamToString(Object valeur, String type) {
		try {
			switch (type) {
			case "String":
				return ((String) valeur);
			case "int":
				Integer i = ((Integer) valeur);
				if (i < 0)
					throw new IllegalArgumentException("La valeur a convertir est inferieur a 0");
				return i.toString();
			case "VoteType":
				VoteType vt = ((VoteType) valeur);
				return vt.toString();
			case "VoteEtape":
				VoteEtape ve = ((VoteEtape) valeur);
				return ve.toString();
			case "VigileEtat":
				VigileEtat vie = ((VigileEtat) valeur);
				return vie.toString();
			case "TypeJoueur":
				TypeJoueur tj = ((TypeJoueur) valeur);
				return tj.toString();
			case "ReasonType":
				ReasonType rt = ((ReasonType) valeur);
				return rt.toString();
			case "PionCouleur":
				PionCouleur pc = ((PionCouleur) valeur);
				return pc.toString();
			case "Couleur":
				Couleur c = ((Couleur) valeur);
				return c.toString();
			case "CondType":
				CondType ct = ((CondType) valeur);
				return ct.toString();
			case "CarteType":
				CarteType cat = ((CarteType) valeur);
				return cat.toString();
			case "TypePartie":
				TypePartie tp = ((TypePartie) valeur);
				return tp.toString();
			case "Status":
				Status sss = ((Status) valeur);
				return sss.toString();
			case "HashMap<Integer,List<Integer>>":
				HashMap<?, ?> pti = (HashMap<?, ?>) valeur;
				HashMap<Integer, List<Integer>> ptiTemp = new HashMap<>();
				for (Map.Entry<?, ?> entry : pti.entrySet()) {
					List<?> illtmp = (List<?>) entry.getValue();
					List<Integer> ill = new ArrayList<>();
					for (Object o : illtmp)
						ill.add((int) o);

					ptiTemp.put((Integer) entry.getKey(), ill);
				}
				return PaquetOutils.subListToStr(ptiTemp);
			case "List<CarteType>":
			case "List<Couleur>":
			case "List<PionCouleur>":
				return PaquetOutils.listEnumToStr(valeur);
			case "List<Integer>":
				List<?> illtmp = (List<?>) valeur;
				List<Integer> il = new ArrayList<>();
				for (Object o : illtmp)
					il.add((int) o);
				return PaquetOutils.listStrToInteger(il);
			case "List<String>":
				List<?> alltmp = (List<?>) valeur;
				List<String> al = new ArrayList<>();
				for (Object o : alltmp)
					al.add((String) o);
				return PaquetOutils.listStrToStr(al);
			default:
				throw new IllegalStateException("Unexpected value: " + type);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Permet de convertir une chaine de caractere en objet.
	 *
	 * @param param Le parametre
	 * @param type  Le type du parametre a retourner
	 * @return L'object correspondant
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
			case "HashMap<Integer,List<Integer>>":
				return PaquetOutils.strToSubList(param);
			case "List<CarteType>":
				return PaquetOutils.strToListEnum(param, CarteType.class);
			case "List<Couleur>":
				return PaquetOutils.strToListEnum(param, Couleur.class);
			case "List<PionCouleur>":
				return PaquetOutils.strToListEnum(param, PionCouleur.class);
			case "List<Integer>":
				return PaquetOutils.strToListInteger(param);
			case "List<String>":
				return PaquetOutils.strToListStr(param);
			default:
				throw new IllegalStateException("Unexpected value: " + type);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Obtient le paquet via une chaine de caractere.
	 *
	 * @param message        Le message a convertir
	 * @param controleurReseau Le NetworkManager associé
	 * @return Le paquet associé
	 */
	public static Packet strToPacketUdp(String message, ControleurReseau controleurReseau) {
		if (controleurReseau.getPacketsUdp().size() == 0)
			throw new IllegalArgumentException("NetWorkManager n'a pas ete initialise au moins une fois !");

		return controleurReseau.getPacketsUdp().get(PaquetOutils.getCleMessage(message));
	}

	/**
	 * Obtient le paquet via une chaine de caractere.
	 *
	 * @param message        Le message a convertir
	 * @param controleurReseau Le NetworkManager associé
	 * @return Le paquet associé
	 */
	public static Packet strToPacketTcp(String message, ControleurReseau controleurReseau) {
		if (controleurReseau.getPacketsTcp().size() == 0)
			throw new IllegalArgumentException("NetWorkManager n'a pas ete initialise au moins une fois !");

		return controleurReseau.getPacketsTcp().get(PaquetOutils.getCleMessage(message));
	}

	/**
	 * Convertit une chaine de caractere.
	 *
	 * @param valeur Parametre
	 * @return Une chaine de caractere
	 */
	private static String format(String valeur) {
		return valeur.split(":")[1].replace("\r", "");
	}
}
