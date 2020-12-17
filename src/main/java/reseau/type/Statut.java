package reseau.type;

public enum Statut {
	ATTENTE {
		public String nomEntier() {
			return "Attente";
		}
	},
	ANNULEE {
		public String nomEntier() {
			return "Annulée";
		}
	},
	COMPLETE {
		public String nomEntier() {
			return "Complete";
		}
	},
	TERMINEE {
		public String nomEntier() {
			return "Terminée";
		}
	};

	public abstract String nomEntier();
}
