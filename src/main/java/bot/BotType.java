package bot;

public enum BotType {
	FAIBLE {
		public String typeString() {
			return "";
		}
	},
	MOYEN {
		public String typeString() {
			return "";
		}
	},

	FORT {
		public String typeString() {
			return "";
		}
	};
	public abstract String typeString();
}
