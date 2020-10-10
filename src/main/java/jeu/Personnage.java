package jeu;

/**
 * The Class personnage.
 * @author Alex
 * @version 0
 * @since 04/10/2020
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

		

		public Personnage (Joueur joueur, TypePersonnage type) {
			this.type = type;
			this.joueur = joueur;
		}
		
		
		//-------------------------------------------------------
		
		public boolean isEstVivant() {
			return estVivant;
		}
		
		
		public void setEstVivant(boolean estVivant) {
			this.estVivant = estVivant;
		}
		
		//-------------------------------------------------------
		public boolean isEstCache() {
			return estCache;
		}
		

		public void setEstCache(boolean estCache) {
			this.estCache = estCache;
		}
		
		//-------------------------------------------------------
		public int getNbrElection() {
			return nbrElection;
		}
		

		public void setNbrElection(int nbrElection) {
			this.nbrElection = nbrElection;
		}
		
		//-------------------------------------------------------
		public int getPoint() {
			return point;
		}

		//-------------------------------------------------------	
		public int getNbrZretenu() {
			return nbrZretenu;
		}
		
		//-------------------------------------------------------
		public int nbrVoixDeVote() {
			return nbrVoixPourVoter;
		}
		//-------------------------------------------------------
		public void changerDeLieux(Lieu newLieu) {
			monLieu.getPersonnage().remove(this);
			newLieu.getPersonnage().add(this);
			monLieu=newLieu;
			
		}
		
		public Lieu getMonLieu() {
			return monLieu;
		}

		//-------------------------------------------------------	
		public Joueur getJoueur() {
			return this.joueur;
		}
		
		public void setJoueur(Joueur joueur) {
			this.joueur = joueur;
		}
		
		//-------------------------------------------------------
		public TypePersonnage getType() {
			return type;
		}
	
}
