import java.util.List;
import java.util.Scanner;

import bot.Bot;
import bot.BotMode;
import bot.BotType;
import bot.PartieInfo;
import reseau.tool.ThreadOutils;
import reseau.type.TypePartie;

public class ConsoleBot {
	static Scanner scanner = new Scanner(System.in);
	static Bot bot;

	// Mode Automatique Manuel
	// Difficulté du bot
	// Nom de la partie a rejoindre
	// Delay des bots
	public static void main(String[] args) throws InterruptedException {
		BotMode botMode = getBotMode(args);

		if (args.length == 3 && botMode == BotMode.Automatique) {
			ThreadOutils.asyncTask("Bot", bot = new Bot(getDelay(args[2]), getBotType(args[1]), botMode));
			Thread.sleep(1000);
		} else if (args.length == 4 && botMode == BotMode.Manuel) {
			ThreadOutils.asyncTask("Bot", bot = new Bot(getDelay(args[2]), getBotType(args[1]), botMode));
			Thread.sleep(1000);
			ThreadOutils.asyncTask("Bot", () -> bot.connecter(choisirPartie(args[3])));
		} else {
			ThreadOutils.asyncTask("Bot", bot = new Bot(1000, choisirBot(), BotMode.Manuel));
			Thread.sleep(1000);
			ThreadOutils.asyncTask("Bot", () -> bot.connecter(choisirPartie("")));
		}
	}

	private static BotMode getBotMode(String[] args) {
		BotMode botMode;
		try {
			if (args.length >= 1)
				botMode = BotMode.valueOf(args[0]);
			else
				botMode = BotMode.Manuel;
		} catch (Exception e) {
			System.out.println(args[0] + " est inconnu. Le bot est passé en manuel.");
			botMode = BotMode.Manuel;
		}

		return botMode;
	}

	private static BotType getBotType(String val) {
		BotType botType;
		try {
			botType = BotType.valueOf(val);
		} catch (Exception e) {
			System.out.println(val + " est inconnu. Le bot est maintenant un bot faible");
			botType = BotType.FAIBLE;
		}

		return botType;
	}

	private static int getDelay(String val) {
		int delay;
		try {
			delay = Integer.valueOf(val);
		} catch (Exception e) {
			System.out.println(val + " est inconnu. Le bot est passer sur un delai de 1000ms.");
			delay = 1000;
		}

		return delay;
	}

	private static BotType choisirBot() {
		int reponse;
		do {
			System.out.println("Choissisez un type de bot ");
			for (int i = 0; i < BotType.values().length; i++) {
				System.out.println(i + "." + BotType.values()[i].name());
			}
			reponse = scanner.nextInt();
		} while (reponse < 0 || reponse > 2);

		return BotType.values()[reponse];
	}

	private static PartieInfo choisirPartie(String val) {
		List<PartieInfo> partieInfos;

		bot.getPartie(TypePartie.MIXTE, 6);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		partieInfos = bot.getPartiesActuel();

		for (PartieInfo partieInfo : partieInfos)
			if (partieInfo.getNom().equals(val))
				return partieInfo;

		System.out.println();
		System.out.println("=======================================");
		int reponse;
		do {
			System.out.println("Choissisez un type de partie ");
			for (int i = 0; i < TypePartie.values().length; i++) {
				System.out
						.println(i + "." + TypePartie.values()[i].name() + " - " + TypePartie.values()[i].nomEntier());
			}
			reponse = scanner.nextInt();
		} while (reponse < 0 || reponse > 2);

		int reponse1;
		do {
			System.out.println("Choissisez un nombre entre 3 et 6 inclus");
			reponse1 = scanner.nextInt();
		} while (reponse1 < 3 || reponse1 > 6);

		int reponse2 = -1;
		do {
			bot.getPartie(TypePartie.values()[reponse], reponse1);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			partieInfos = bot.getPartiesActuel();

			if (partieInfos.isEmpty()) {
				System.out.println("Aucune partie trouvé");
				System.out.println("Appuyez sur une touche pour actualiser");
				scanner.next();
			} else {
				System.out.println("Choissisez une partie");
				for (int i = 0; i < partieInfos.size(); i++) {
					System.out.println(i + "." + partieInfos);
				}
				reponse2 = scanner.nextInt();
			}
		} while (reponse2 < 0 || reponse2 >= partieInfos.size());

		return partieInfos.get(reponse2);
	}
}
