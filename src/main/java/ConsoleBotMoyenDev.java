import java.util.logging.Level;
import java.util.logging.Logger;

import bot.Bot;
import bot.BotMode;
import bot.BotType;
import reseau.tool.ThreadOutils;

public class ConsoleBotMoyenDev {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskInfinite("Bot Moyen", new Bot(0, BotType.MOYEN, BotMode.AUTOMATIQUE, true));
	}
}
