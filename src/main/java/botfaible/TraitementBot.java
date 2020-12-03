package botfaible;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import reseau.type.CarteType;
import reseau.type.Couleur;
import reseau.type.PionCouleur;

public class TraitementBot {

	public void initialiserPartie(BotFaible core, List<?> nomsT, List<?> couleursT, int lieuferme) {
		List<String> noms = new ArrayList<>();
		List<Couleur> couleurs = new ArrayList<>();
		for (Object o : nomsT)
			noms.add((String) o);
		for (Object o : couleursT)
			couleurs.add((Couleur) o);
		core.setCouleur(IdjrTools.getCouleurByName(core.getNom(), noms, couleurs));
		if (lieuferme == 2) {
			core.getLieuOuvert().add(1);
			core.getLieuOuvert().add(3);
			core.getLieuOuvert().add(4);
			core.getLieuOuvert().add(5);
			core.getLieuOuvert().add(6);
		} else {
			core.getLieuOuvert().add(1);
			core.getLieuOuvert().add(2);
			core.getLieuOuvert().add(3);
			core.getLieuOuvert().add(4);
			core.getLieuOuvert().add(5);
			core.getLieuOuvert().add(6);
		}
	}

	public void lancerDes(BotFaible core, List<?> pionT) {
		List<Integer> pion = new ArrayList<>();
		for (Object o : pionT)
			pion.add((Integer) o);
		core.setPionAPos(pion);
	}

	public int choisirDestPlacement(List<?> destRestantT) {
		List<Integer> destRestant = new ArrayList<>();
		for (Object o : destRestantT)
			destRestant.add((Integer) o);
		int dest = 0;
		if (!destRestant.isEmpty())
			dest = destRestant.get(new Random().nextInt(destRestant.size()));
		return dest;
	}

	public int choisirPionPlacement(BotFaible core) {
		int pion = 0;
		if (!core.getPionAPos().isEmpty())
			pion = core.getPionAPos().get(new Random().nextInt(core.getPionAPos().size()));
		return pion;
	}

	public void debutTour(BotFaible core, List<Couleur> couleurs) {
		if (!couleurs.contains(core.getCouleur())) {
			core.setEnvie(false);
		}
	}

	public int choixDest(BotFaible core){
		out.println("Entrez une destination");
		int dest = 0;
		dest = core.getLieuOuvert().get(new Random().nextInt(core.getLieuOuvert().size()));
		return dest;
	}
	
	public void debutDeplacemant(BotFaible core, List<?> lieuxT){
		List<Integer> lieux = new ArrayList<>();
		for (Object o : lieuxT)
			lieux.add((Integer) o);
		for (Integer i : lieux)
			if (core.getLieuOuvert().contains(i))
				core.getLieuOuvert().remove(i);
	}

	
	public List<Integer>  pionADeplacer(int dest, HashMap<Integer, List<Integer>> listedp) {
		List<Integer> destEtPion = new ArrayList<>();
		List<Integer> listePion = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
			for (int destPos : dp.getValue())
				if (destPos == dest)
					listePion.add(dp.getKey());
		int pionAdep;
		if (listePion.isEmpty()) {
			dest = 4;
			for (Map.Entry<Integer, List<Integer>> dp : listedp.entrySet())
				for (int destPos : dp.getValue())
					if (destPos == dest)
						listePion.add(dp.getKey());
			if (!listePion.isEmpty())
				pionAdep = listePion.get(new Random().nextInt(listePion.size()));
			else
				pionAdep = new ArrayList<Integer>(listedp.keySet()).get(0);
		} else
			pionAdep = listePion.get(new Random().nextInt(listePion.size()));
		destEtPion.add(dest);
		destEtPion.add(pionAdep);
		return destEtPion;
	}
	
	public void attaqueZombie(BotFaible core, List<PionCouleur> l, List<PionCouleur> ltemp) {
		for (PionCouleur pc : l) {
			if (IdjrTools.getCouleurByChar(pc) == core.getCouleur()) {
				ltemp.add(pc);
			}
		}
		core.setPoinSacrDispo(ltemp);
	}
	
	public PionCouleur choisirSacrifice(BotFaible core, List<?> listPionT) {
		List<Integer> listPion = new ArrayList<>();
		for (Object o : listPionT)
			listPion.add((Integer) o);
		int pionTemp = listPion.get(new Random().nextInt(listPion.size()));
		PionCouleur pion = PionCouleur.valueOf(String.valueOf(core.getCouleur().name().charAt(0)) + pionTemp);
		return pion;
	}
	
	public void finPartie(BotFaible core, Couleur gagnant) {
		out.println("Le gagant est le joueur " + gagnant + " !");
		core.setEstFini(true);
		// getControleurReseau().arreter();
	}
    
	public List<CarteType> listeCarteJouee(BotFaible core, int n)
	{
		List<CarteType> listeCarteJouee = new ArrayList<>();
		List<CarteType> listeCarteUtilisable = new ArrayList<>();
		Random r= new Random();
		int nbCarteJouee = r.nextInt(core.getListeCarte().size());
		int indexCarteJouee;
		
		for (CarteType carte : core.getListeCarte())
		{
			if(n!=4)
			{
				if(carte.name()=="MAT")
				{
					listeCarteUtilisable.add(carte);
				}
			}
			switch (carte.name())
			{
			case "CAC":
				listeCarteUtilisable.add(carte);
				break;
			case "ACS":
				listeCarteUtilisable.add(carte);
				break;
			case "ATR":
				listeCarteUtilisable.add(carte);
				break;
			case "AGR":
				listeCarteUtilisable.add(carte);
				break;
			case "ARE":
				listeCarteUtilisable.add(carte);
				break;
			case "AHI":
				listeCarteUtilisable.add(carte);
				break;
			case "ABA":
				listeCarteUtilisable.add(carte);
				break;
			}
		}
		for (int i = 0; i < nbCarteJouee; i++)
		{
			indexCarteJouee = r.nextInt(core.getListeCarte().size());
			listeCarteJouee.add(listeCarteUtilisable.get(indexCarteJouee));
			core.getListeCarte().remove(listeCarteUtilisable.get(indexCarteJouee));
		}
		
		return listeCarteJouee;
	}
	
	public List<PionCouleur> listePionCache (BotFaible core) {
        List<CarteType> listeCarteCachette=new ArrayList<>();
        List<PionCouleur> listePionCache=new ArrayList<>();

        for(CarteType carte: core.getListeCarte()) {
            if(carte.name()=="CAC") {
                listeCarteCachette.add(carte);
            }
        }
        Random r = new Random();
        int nbrCartePossibleDejouer=listeCarteCachette.size();
        int nbrPion=core.getListePion().size();
        int nbrCarteJouee;
        if(nbrCartePossibleDejouer>nbrPion) {
            nbrCarteJouee=r.nextInt(nbrPion);
        }
        else {
            nbrCarteJouee=r.nextInt(nbrCartePossibleDejouer);

        }
        
        for(int i=0;i<nbrCarteJouee;i++) {
            listePionCache.add(core.getListePion().get(i));
        }

        return listePionCache;
    }
	
	  public CarteType ReponseJoueurCourant(BotFaible core) {
	        CarteType[] RJListe = CarteType.values();
	        int rand = new Random().nextInt(1);
	        CarteType RJ = RJListe[rand];
	        return RJ;
	    }
	  
	  public int IndiquerCarteJouees(BotFaible core) {
	          CarteType carteMenace= CarteType.MEN;
	          Random r= new Random();
	          int nbrCarteMen=0; 
	          for(CarteType carte: core.getListeCarte()) {
	              if(carte.name()==carteMenace.name()) {
	                  nbrCarteMen++;
	              }    
	          }        
	          int nbrCartemenaceReturn = r.nextInt(nbrCarteMen);
	          return nbrCartemenaceReturn;      
	      }
	  
	  public Couleur getRandom() {
	    	List<Couleur> li = new ArrayList<Couleur>();
	    	li.add(Couleur.NOIR);
	    	li.add(Couleur.VERT);
	    	li.add(Couleur.BLEU);
	    	li.add(Couleur.ROUGE);
	    	li.add(Couleur.JAUNE);
	    	li.add(Couleur.MARRON);
	    	int rand = new Random().nextInt(li.size());
	    	return li.get(rand);
	    }
	  
	  public List<Object> carteFouille(List<CarteType> listeCarte, BotFaible bot){
		  CarteType carteGarde = CarteType.NUL;
			CarteType carteOfferte = CarteType.NUL;
			CarteType carteDefausse = CarteType.NUL;
			Couleur couleur = Couleur.NUL;
			List<Object> carteChoisies = new ArrayList<Object>();
			if (listeCarte.size() == 3) {
				carteGarde = listeCarte.get(0);
				bot.addCarte(carteGarde);
				carteOfferte = listeCarte.get(1);
				carteDefausse = listeCarte.get(2);
				couleur = getRandom();
			}
			if (listeCarte.size() == 2) {
				carteGarde = listeCarte.get(0);
				bot.addCarte(carteGarde);
				carteOfferte = listeCarte.get(1);
				couleur = getRandom();
			}
			if (listeCarte.size() == 1) {
				carteGarde = listeCarte.get(0);
				bot.addCarte(carteGarde);
			}
			carteChoisies.add(carteGarde);
			carteChoisies.add(carteOfferte);
			carteChoisies.add(carteDefausse);
			carteChoisies.add(couleur);
			
			return carteChoisies;
	  }

}
