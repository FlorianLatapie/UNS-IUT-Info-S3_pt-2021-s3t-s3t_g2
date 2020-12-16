
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bot.Bot;
import bot.BotType;
import reseau.tool.ThreadOutils;

public class ConsoleBotFortNew {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskRepeat(10,() -> {
			Bot botFort = new Bot(0,BotType.FORT);
			try {
				botFort.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
