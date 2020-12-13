package reseau.paquet;

import reseau.ptprotocole.PtProtocole;
import reseau.ptprotocole.PtValeur;
import reseau.tool.PaquetOutils;
import reseau.tool.PtOutils;

import java.text.MessageFormat;

/**
 * <h1>Permet de creer des paquets</h1> Il permet de construire un protocol
 *
 * @author Sébastien Aglaé
 * @version 2.0
 */
public class Paquet {
	final String cle;
	String[] paras;
	final String[] noms;
	final String[] types;
	final String[] docs;
	final PtProtocole ptProtocol;
	final int nbArg;

	/**
	 * @param ptProtocol Un ptprotocol contenant les informations pour ce nouveau
	 *                   paquet
	 */
	public Paquet(PtProtocole ptProtocol) {
		this.ptProtocol = ptProtocol;
		this.cle = ptProtocol.getMotCle();
		this.paras = new String[ptProtocol.taille()];
		this.noms = ptProtocol.toNoms();
		this.types = ptProtocol.toTypes();
		this.docs = ptProtocol.toDocs();
		this.nbArg = ptProtocol.taille();
	}

	/**
	 * Permet de contruire le message sous forme d'une chaine de caractere pour
	 * l'envoie a partir de valeurs.
	 *
	 * @param param Les parametres necessaire pour construire le packet
	 * @return Le message pret a etre envoyé
	 * @throws IllegalArgumentException Si le nombre de parametre n'est pas correcte
	 * @throws IllegalArgumentException Si l'un des parametres n'est pas du bon type
	 */
	public String construire(Object... param) {
		if (param.length != nbArg)
			throw new IllegalArgumentException(
					"[" + cle + "] Le nombre de parametre n'est pas correcte !\n" + getDocs());
		StringBuilder tmp = new StringBuilder(cle + "-");
		for (int i = 0; i < param.length; i++) {
			String res = PtOutils.convertParamToString(param[i], types[i]);
			if (res == null)
				throw new IllegalArgumentException(MessageFormat.format("[{0}] {1} {2} n''est pas un type {3} !\n{4}",
						cle, i + 1, param[i], types[i], getDocs()));
			tmp.append(res).append("-");
		}
		return tmp.substring(0, tmp.length() - 1);
	}

	/**
	 * Permet a partir d'une chaine de caractere d'obtenir les valeurs du message.
	 *
	 * @param message Le message du protocol réseau
	 * @return Les valeurs du message (Il faut cast selon le bon type (.getDocs()))
	 * @throws IllegalArgumentException Si le nombre de parametre n'est pas correcte
	 * @throws IllegalArgumentException Si l'un des parametres n'est pas du bon type
	 */
	public Object[] getValeurs(String message) {
		String[] tmp = message.split("-");

		if (tmp.length != nbArg + 1)
			throw new IllegalArgumentException(
					MessageFormat.format("[{0}] Le nombre de parametre n''est pas correcte !", cle));

		Object[] obj = new Object[tmp.length];
		for (int i = 1; i < tmp.length; i++) {
			obj[i] = PtOutils.convertStringToObject(tmp[i], types[i - 1]);
			if (obj[i] == null)
				throw new IllegalArgumentException(
						MessageFormat.format("[{0}] {1} n''est pas un type {2} !", cle, tmp[i], types[i - 1]));
		}

		return obj;
	}

	/**
	 * Permet a partir d'une chaine de caractere d'obtenir les valeurs du message.
	 *
	 * @param message Le message du protocol réseau
	 * @param argNum  Numero de l'argument
	 * @return Les valeurs du message (Il faut cast selon le bon type (.getDocs()))
	 * @throws IllegalArgumentException Si le nombre de parametre n'est pas correcte
	 * @throws IllegalArgumentException Si l'un des parametres n'est pas du bon type
	 */
	public Object getValeur(String message, int argNum) {
		String[] tmp = message.split("-");
		if (tmp.length < argNum || argNum <= 0)
			throw new IllegalArgumentException(
					MessageFormat.format("[{0}] L''index est en dehors du tableau {1} !\n{2}", cle, argNum, getDocs()));

		Object obj = PtOutils.convertStringToObject(tmp[argNum], types[argNum - 1]);
		if (obj == null)
			throw new IllegalArgumentException(
					MessageFormat.format("[{0}] {1} n''est pas un type {2} !", cle, tmp[argNum], types[argNum - 1]));

		return obj;
	}

	/**
	 * Permet d'obtenir la documentation (nom, type et doc) du paquet courant.
	 *
	 * @return La documentation
	 */
	public String getDocs() {
		StringBuilder tmp = new StringBuilder(
				"Nom : " + ptProtocol.getNomClasse() + "\nDoc : " + ptProtocol.getDoc() + "\n");
		int i = 1;
		for (PtValeur doc : ptProtocol.getValeurs().getValeurs())
			tmp.append("[").append(i++).append("]").append(doc).append("\n");

		return tmp.toString();
	}

	/**
	 * Permet d'identifier un packet via son mot-clé
	 *
	 * @param cle le mot clé identifiant le paquet ou le message
	 * @return si le mot-clé correspond a celui du paquet
	 */
	public boolean isPaquet(String cle) {
		if (cle.contains("-"))
			return this.cle.equals(PaquetOutils.getCleMessage(cle));
		return this.cle.equals(cle);
	}

	/**
	 * Obtient le mot-clé.
	 *
	 * @return Le mot-clé
	 */
	public String getCle() {
		return cle;
	}

	@Override
	public String toString() {
		return ptProtocol.toString();
	}
}
