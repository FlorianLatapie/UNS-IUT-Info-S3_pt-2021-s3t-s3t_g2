import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import botfort.BotFort;
import reseau.tool.ThreadOutils;

public class ConsoleBotFort {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskRepeat(10,() -> {
			BotFort botFort = new BotFort(0);
			try {
				botFort.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
