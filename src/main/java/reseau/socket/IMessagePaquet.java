package reseau.socket;

public interface IMessagePaquet {
	void ajouterMessage(String message);

	String getMessage(String cle);

	void attendreMessage(String cle);
}
