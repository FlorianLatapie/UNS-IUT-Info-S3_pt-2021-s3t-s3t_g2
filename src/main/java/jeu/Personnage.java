package jeu;

/**
 * The Class Personnage.
 * @authors Leo Theo Yanis Kevin Vincent
 * @version 0.1
 * @since 04/10/2020
 * @category personnage
 */
 public abstract class Personnage {
		
		protected Lieu monLieu;// position actuel du personnage
		
		private Joueur joueur;
		
		private TypePersonnage type;
		
		protected int point;
		
		protected int nbrZretenu=1;
		
		protected int nbrVoixPourVoter=1;
		
		protected int nbrElection;
		
		protected boolean estVivant;
		
		protected boolean estCache; 

		
		/**
		 * Instancie un personnage
		 * @param Joueur         joueur
		 * @param TypePersonnage type
		 */
		public Personnage (Joueur joueur, TypePersonnage type) {
			this.type = type;
			this.joueur = joueur;
		}
		
		
		//-------------------------------------------------------
		/**
		 * Renvoie true si le personnage est vivant, sinon false
		 */
		public boolean isEstVivant() {
			return estVivant;
		}
		
		/**
		 * définis en true ou false le paramètre EstVivant du personnage
		 */
		public void setEstVivant(boolean estVivant) {
			this.estVivant = estVivant;
		}
		
	
		
		//-------------------------------------------------------
		/**
		 * Renvoie true si le personnage est caché, sinon false
		 */
		public boolean isEstCache() {
			return estCache;
		}
		
		/**
		 * définis en true ou false le paramètre EstCache du personnage
		 */
		public void setEstCache(boolean estCache) {
			this.estCache = estCache;
		}
		
		//-------------------------------------------------------
		/**
		 * Renvoie le nombre de vote donné à ce personnage à la suite d'un vote
		 */
		public int getNbrElection() {
			return nbrElection;
		}
		
		/**
		 * définis le nombre de vote donné à ce personnage à la suite d'un vote
		 */
		public void setNbrElection(int nbrElection) {
			this.nbrElection = nbrElection;
		}
		
		//-------------------------------------------------------
		/**
		 * Renvoie le nombre de point du personnage
		 */
		public int getPoint() {
			return point;
		}
		
		public void setPoint(int point) {
			this.point = point;
		}

		//-------------------------------------------------------	
		/**
		 * Renvoie le nombre de Zombie que le personnage peut retenir
		 */
		public int getNbrZretenu() {
			return nbrZretenu;
		}
		
		
		//-------------------------------------------------------
		/**
		 * Renvoie le nombre de voix que le personnage peut utiliser durant un vote
		 */
		public int getNbrVoixPourVoter() {
			return nbrVoixPourVoter;
		}
		//-------------------------------------------------------
		/**
		 * Definis un nouveau lieu pour le personnage 
		 * @param Lieu           newLieu
		 */
		public void changerDeLieux(Lieu newLieu) {
			monLieu=newLieu;
		}
		
		/**
		 * Renvoie le lieu dans lequel se trouve actuellement le joueur
		 */
		public Lieu getMonLieu() {
			return monLieu;
		}

		//-------------------------------------------------------
		/**
		 * Renvoie le joueur possédant le personnage
		 */
		public Joueur getJoueur() {
			return this.joueur;
		}
		
		/**
		 * Définis le joueur qui possède le personnage
		 */
		public void setJoueur(Joueur joueur) {
			this.joueur = joueur;
		}
		
		//-------------------------------------------------------
		/**
		 * Renvoie le type du personnage
		 */
		public TypePersonnage getType() {
			return type;
		}
		
		public abstract String toString();
	
}
