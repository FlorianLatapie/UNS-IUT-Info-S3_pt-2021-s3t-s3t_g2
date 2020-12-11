import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import botmoyen.BotMoyen;
import reseau.tool.ThreadOutils;

<<<<<<< HEAD:src/main/java/ConsoleBotFaible.java
public class ConsoleBotFaible {
=======
public class ConsoleBotMoyen {
>>>>>>> BotMoyen:src/main/java/ConsoleBotMoyen.java

	public static void main(String[] args) {
		Logger.getGlobal().setLevel(Level.FINEST);
		ThreadOutils.asyncTaskRepeat(10,() -> {
			BotMoyen botMoyen = new BotMoyen(0);
			try {
				botMoyen.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
