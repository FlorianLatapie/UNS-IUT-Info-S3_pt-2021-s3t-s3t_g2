import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import botfaible.BotFaible;
import reseau.tool.ThreadOutils;

public class ConsoleBotFaible {

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskRepeat(10,() -> {
			BotFaible botFaible = new BotFaible(0);
			try {
				botFaible.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
