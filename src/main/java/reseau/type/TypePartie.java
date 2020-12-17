package reseau.type;

public enum TypePartie {
	JRU {
		public String nomEntier() {
			return "Joueur réel uniquement";
		}
	},
	BOTU {
		public String nomEntier() {
			return "Joueur virtuel uniquement";
		}
	},
	MIXTE {
		public String nomEntier() {
			return "Joueur réel et virtuel";
		}
	};

	public abstract String nomEntier();
}
