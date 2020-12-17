package reseau.type;

public enum TypeJoueur {
	BOT {
		public String nomEntier() {
			return "Joueur virtuel";
		}
	},
	JR {
		public String nomEntier() {
			return "Joueur réel";
		}
	};

	public abstract String nomEntier();
}
