package bot.MCTS;

import java.util.Random;

import MCTS.Base.MCTS;
import MCTS.Base.MCTS.DEBUG;
import MCTS.Base.StateInfo;
import MCTS.Conditions.StopAfterMaxIterations;
import MCTS.Policies.RandomPolicy;
import MCTS.Policies.UpperConfidenceBoundPolicy;
import bot.partie.Partie;
import reseau.type.Couleur;

public class MCTSBotMoyen {

	static MCTS<StateInfo> mcts;
	private static Random rand = new Random();
	
	public static Integer getChoisDest(Partie partie,Couleur couleur) {
		mcts = new MCTS<StateInfo>(rand, new StopAfterMaxIterations(1000),
				new UpperConfidenceBoundPolicy(rand, MCTS.DEFAULT_EXPLORATION_CONST), new RandomPolicy(rand),
				StateInfo.class, DEBUG.NO);
		PartieState state = new PartieState(partie,couleur);
		PartieAction action = ((PartieAction) mcts.search(state));
		
		return action.getDestination();
	}
}
