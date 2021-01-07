import java.util.logging.Level;
import java.util.logging.Logger;

import bot.Bot;
import bot.BotMode;
import bot.BotType;
import reseau.tool.ThreadOutils;

public class ConsoleBotFortDev {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskInfinite("Bot Fort", new Bot(0, BotType.FORT, BotMode.AUTOMATIQUE, true));
	}
}
