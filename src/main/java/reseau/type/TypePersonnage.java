package reseau.type;

public enum TypePersonnage {
	BLONDE {
		public String nomNormal() {
			return "Blonde";
		}
	},
	TRUAND {
		public String nomNormal() {
			return "Truand";
		}
	},
	BRUTE {
		public String nomNormal() {
			return "Brute";
		}
	},
	FILLETTE {
		public String nomNormal() {
			return "Fillette";
		}
	};

	public abstract String nomNormal();
}
