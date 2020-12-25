import java.util.logging.Level;
import java.util.logging.Logger;

import bot.Bot;
import bot.BotType;
import reseau.tool.ThreadOutils;

public class ConsoleBotFaibleNew {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskRepeat(10,new Bot(0,BotType.FAIBLE));
	}
}
