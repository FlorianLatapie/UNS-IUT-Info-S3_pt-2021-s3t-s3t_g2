package bot;

import bot.langues.International;

public enum BotType {
	FAIBLE {
		public String typeString() {
			return International.trad("texte.botFaible");
		}
	},
	MOYEN {
		public String typeString() {
			return International.trad("texte.botMoyen");
		}
	},

	FORT {
		public String typeString() {
			return International.trad("texte.botFort");
		}
	};

	public abstract String typeString();
}
