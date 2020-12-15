package botmoyen.MCTS;

import MCTS.Base.Action;

public class PartieAction extends Action{
	private Integer destination;
	
	public PartieAction(Integer destination) {
		super();
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "BotMoyen destination = " + destination;
	}
	
	@Override
	public boolean equals(Object obj) {
		PartieAction objmove = (PartieAction) obj;
		return (objmove.getDestination() == destination);
	}

	public Integer getDestination() {
		return destination;
	}
	
	@Override
	public int hashCode() {
		return destination*179;
	}

	

}
