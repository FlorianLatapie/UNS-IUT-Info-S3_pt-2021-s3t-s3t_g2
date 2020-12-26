import java.util.logging.Level;
import java.util.logging.Logger;

import bot.Bot;
import bot.BotMode;
import bot.BotType;
import reseau.tool.ThreadOutils;

public class ConsoleBotFaibleDev {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskInfinite("Bot Faible", new Bot(0, BotType.FAIBLE, BotMode.Automatique, true));
	}
}
