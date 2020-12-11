package reseau.type;

public enum CarteType {
	NUL {
		public String nomEntier() {
			return "NUL";
		}
	},
	CDS {
		public String nomEntier() {
			return "Caméra de sécurité";
		}
	},
	SPR {
		public String nomEntier() {
			return "Sprint";
		}
	},
	MEN {
		public String nomEntier() {
			return "Menace";
		}
	},
	MAT {
		public String nomEntier() {
			return "Matériel";
		}
	},
	ACS {
		public String nomEntier() {
			return "Canon scié";
		}
	},
	ATR {
		public String nomEntier() {
			return "Tronçonneuse";
		}
	},
	AGR {
		public String nomEntier() {
			return "Grenade";
		}
	},
	ARE {
		public String nomEntier() {
			return "Revolver";
		}
	},
	AHI {
		public String nomEntier() {
			return "Hache";
		}
	},
	ABA {
		public String nomEntier() {
			return "Batte";
		}
	},
	CAC {
		public String nomEntier() {
			return "Cachette";
		}
	};

	public abstract String nomEntier();
}
