package reseau.type;

public enum VoteType {
	FDC {
		public String nomEntier() {
			return "Fouille du camion";
		}
	},
	ECD {
		public String nomEntier() {
			return "Election chef des vigiles";
		}
	},
	MPZ {
		public String nomEntier() {
			return "Sacrifice";
		}
	};

	public abstract String nomEntier();
}
