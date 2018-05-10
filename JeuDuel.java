
import java.util.ArrayList; //Importation classe ArrayList
import MG2D.*; // Importation MG2D
import MG2D.geometrie.*;
import MG2D.audio.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;



public class JeuDuel{ // Définition de la classe

	///////////////////////////////////// Attributs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
	Joueur player1,player2;
	Ennemi army,police,tonneau_ennemi;
	ArrayList<Ennemi> tabEnnemis;
	ArrayList<Texture> tabDecor,tabHerbe;
	ArrayList<Vie> tabVies;
	Fenetre fen;

	Point a; // utilisé pour la gen aléatoire d'ennemis
	Point b; // utilisé pour la gen aléatoire de décor

	Clavier clavier;
	int score;
	int random;
	int random_position;
	int dx,dy; // Utilisés pour la translation
	String chemin;

	Ligne trait;   // Délimitations entre voies de circulation
	Rectangle colonne; // voie de circulation

	Texture gameover;
	Texte aff_score;
	Vie vie;
	Texte aff_vie;


	// -- MODE DUEL --
	public static String pseudoj1;
	public static String pseudoj2;


	//textures decor
	Texture herbe,mursac,bcptono,tonottseul,soldat;


	///////////////////////////////////// Constructeurs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 

	// Constructeur par défaut -- Initialisation Interface graphique & clavier
	public JeuDuel(Fenetre f){
		Constantes.TAILLEX = 800;
		Constantes.TAILLEY = 600;
		tabEnnemis= new ArrayList<Ennemi>();
		tabDecor = new ArrayList<Texture>();
		tabHerbe = new ArrayList<Texture>();
		tabVies = new ArrayList<Vie>();

		score = 0;
		Font policeDuJeu = null;
		InputStream is = this.getClass().getResourceAsStream("assets/typo.otf");

		try {
			policeDuJeu = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		aff_score=new Texte(Couleur.BLEU,("Score : "+String.valueOf(score)),policeDuJeu,new Point(60,(Constantes.TAILLEY-(Constantes.TAILLEY-50))));
		fen = f;

		clavier = fen.getClavier();

		//Mise en place de la texture de fond
		for(int g=Constantes.TAILLEY;g>(-200);g--){

			herbe = new Texture(Main.getTextureMap(),new Point(0,g),200,200); 
			fen.ajouter(herbe);
			tabHerbe.add(herbe);
			herbe = new Texture(Main.getTextureMap(),new Point(Constantes.TAILLEX-200,g),200,200); 
			fen.ajouter(herbe);
			tabHerbe.add(herbe);
			g-=199;
		}

		//Mise en place score
		fen.ajouter(aff_score);

		//Mise en place des colonnes en fonction de la taille de la fenetre.
		for(int i=0;i<=Constantes.TAILLEX;i++){
			if(i>150 && i<Constantes.TAILLEX-200){
				colonne = new Rectangle(Couleur.GRIS_CLAIR,new Point(i,0),100,800,true);
				fen.ajouter(colonne);

				//Mise en place des délimitations blanches

				trait = new Ligne(Couleur.BLANC,new Point(i+2,0), new Point(i+2,Constantes.TAILLEY));
				fen.ajouter(trait);
				trait = new Ligne(Couleur.BLANC,new Point(i+1,0), new Point(i+1,Constantes.TAILLEY));
				fen.ajouter(trait);
				trait = new Ligne(Couleur.BLANC,new Point(i,0), new Point(i,Constantes.TAILLEY));
				fen.ajouter(trait);
				trait = new Ligne(Couleur.BLANC,new Point(i-1,0), new Point(i-1,Constantes.TAILLEY));
				fen.ajouter(trait);

				i=i+100;
			}
		}


		//Derniere délimitation blanche un peu relou
		trait = new Ligne(Couleur.BLANC,new Point(Constantes.TAILLEX-145,0), new Point(Constantes.TAILLEX-145,Constantes.TAILLEY));
		fen.ajouter(trait);

		trait = new Ligne(Couleur.BLANC,new Point(Constantes.TAILLEX-146,0), new Point(Constantes.TAILLEX-146,Constantes.TAILLEY));
		fen.ajouter(trait);

		trait = new Ligne(Couleur.BLANC,new Point(Constantes.TAILLEX-147,0), new Point(Constantes.TAILLEX-147,Constantes.TAILLEY));
		fen.ajouter(trait);

		trait = new Ligne(Couleur.BLANC,new Point(Constantes.TAILLEX-148,0), new Point(Constantes.TAILLEX-148,Constantes.TAILLEY));
		fen.ajouter(trait);

		//Mise en place du joueur
		player1 = new Joueur(new Point((Constantes.TAILLEX/2)-75,0));	
		player1.add(fen);

		player2 = new Joueur(new Point((Constantes.TAILLEX/2)+75,0));	
		player2.add(fen);

		Texture vie = new Texture("img/vie.png",new Point(Constantes.TAILLEX-120,Constantes.TAILLEY-(Constantes.TAILLEY-70)));
		aff_vie = new Texte(Couleur.BLEU,(String.valueOf(player1.getVie())),policeDuJeu,new Point(Constantes.TAILLEX-90,(Constantes.TAILLEY-(Constantes.TAILLEY-60))));
		fen.ajouter(vie);
		fen.ajouter(aff_vie);

		//TODO: Bandeau J1 VS J2

		fen.rafraichir();	
	}

	//////////////////////////////// Accesseurs & Mutateurs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 


	//joueur
	public Joueur getJoueur(){
		return player1;
	}
	public void setJoueur(Joueur j){
		player1 = new Joueur(j);
	}


	//tableau dynamique d'ennemis
	public ArrayList<Ennemi> getTabEnnemis(){
		return tabEnnemis;	
	}
	public void setTabEnnemis(ArrayList<Ennemi> e){
		tabEnnemis = e;
	}

	//score & gameover
	public int getScore(){
		return score;
	}
	public void setScore(int s){
		score = s;
	}
	public Texture getGameover(){
		return gameover;
	}
	public void setGameover(Texture go){
		gameover = new Texture(go);
	}


	//fenetre
	public Fenetre getFenetre(){
		return fen;
	}
	/*    public void setFenetre(Fenetre f){
	fen = new Fenetre(f);
    }
	 */    
	//Taille fenetre
	public int getTailleX(){
		return Constantes.TAILLEX;
	}
	public int getTailleY(){
		return Constantes.TAILLEY;
	}

	public void setTailleX(int tail){
		Constantes.TAILLEX = tail;
	}

	public void setTailleY(int tail){
		Constantes.TAILLEY = tail;
	}    


	//affichages
	public Texte getAff_score(){
		return aff_score;
	}
	public void setAff_score(Texte af){
		aff_score = new Texte(af);
	}


	///////////////////////////////////////// Méthodes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 

	public void AvancerUnPasDeTemps(){

		try{
			Thread.sleep(30);
		}
		catch(Exception e){}

		//Déplacement Joueur
		if (( clavier.getDroiteEnfoncee() == true )&(player1.getTextureJoueur().getB().getX() < Constantes.TAILLEX-146 ))
		{
			dx = (int)(score*0.1+15);
			player1.getTextureJoueur().translater(dx,0);
		}
		if (( clavier.getGaucheEnfoncee() == true )&(player1.getTextureJoueur().getB().getX() > Constantes.TAILLEX-600 ))
		{
			dx = -(int)(score*0.1+15);
			player1.getTextureJoueur().translater(dx,0);
		}
		if (( clavier.getHautEnfoncee() == true )&(player1.getTextureJoueur().getA().getY() < Constantes.TAILLEY ))
		{
			dy = (int)(score*0.1+15);
			player1.getTextureJoueur().translater(0,dy);
		}
		if (( clavier.getBasEnfoncee() == true )&(player1.getTextureJoueur().getA().getY() > 0 ))
		{
			dy = - (int)(score*0.1+15);
			player1.getTextureJoueur().translater(0,dy);
		}       
		fen.rafraichir();


		//Déplacement vies
		for(int ind=tabVies.size()-1;ind>0;ind--)
		{
			tabVies.get(ind).getTextVie().translater(0, -1);			
		}

		//Intersection vie
		for(int ind=tabVies.size()-1;ind>0;ind--)
		{		
			if(player1.intersection(tabVies.get(ind)))
			{
				player1.setVie(player1.getVie()+1);
				fen.supprimer(tabVies.get(ind).getTextVie());
				tabVies.remove(tabVies.get(ind));

				aff_vie.setTexte(String.valueOf(player1.getVie()));
			}
		}

		//Déplacement ennemis en fonction de leur vitesse
		for(int ind=0;ind<(tabEnnemis.size()-1);ind++){
			fen.rafraichir();
			tabEnnemis.get(ind).getTextureEnnemi().translater(0,(tabEnnemis.get(ind).getVitesse()*(-1)));
			fen.rafraichir();


			// On evite que les ennemis se roulent l'un sur l'autre (celui de derrière prend la vitesse de celui de devant ) 
			for(int ind2=0;ind2<(tabEnnemis.size()-1);ind2++){
				if(tabEnnemis.get(ind).intersection(tabEnnemis.get(ind2))){
					tabEnnemis.get(ind).setVitesse(tabEnnemis.get(ind2).getVitesse());
				}
				fen.rafraichir();
			}


			//Suppression des ennemis qui sont sortis de la fenetre
			if(tabEnnemis.get(ind).getTextureEnnemi().getB().getY() <= 0){  // Si l'ennemi sort de l'écran alors
				fen.supprimer(tabEnnemis.get(ind).getTextureEnnemi());    // On le supprime de la fenetre
				tabEnnemis.remove(ind);                                 // On le supprime de l'array list
				score++;
				aff_score.setTexte(("Score :"+String.valueOf(score)));      //Gére l'affichage du score
				fen.rafraichir();	

			}

		}

		//Déplacement du décor en fonction du 5+score
		for(int indd=0;indd<(tabDecor.size());indd++){
			tabDecor.get(indd).translater(0,(-5+score*(-1)));
			//Suppression décor sorti de la fenetre
			if(tabDecor.get(indd).getB().getY() <=0){
				fen.supprimer(tabDecor.get(indd));
				tabDecor.remove(indd);
			}
			fen.rafraichir();
		}  
		for(int ig=0;ig<(tabHerbe.size());ig++){
			tabHerbe.get(ig).translater(0,(-5+score*(-1)));
			if(tabHerbe.get(ig).getB().getY() <1){
				tabHerbe.get(ig).translater(0,Constantes.TAILLEY+200);
			}
			fen.rafraichir();
		}
	}





	/**
	 * Génération aléatoire ennemis
	 */
	public void GenererEnnemi(int positionX, int switchtexture){
		a = new Point(0,Constantes.TAILLEY);
		switch (positionX){ // position
		case 1:
			a.setX(150);
			break;
		case 2:
			a.setX(251);
			break;
		case 3:
			a.setX(352);
			break;
		case 4:
			a.setX(453);
			break;
		case 5:
			a.setX(554);
			break;			
		}

		switch(switchtexture){ // type d'ennemi
		case 1:
			if(tabEnnemis.size() < 10){ // pour éviter qu'il y ait trop de voitures
				army = new Ennemi("img/jeep.png",a);
				army.setVitesse((int)(10+score*0.1));
				tabEnnemis.add(army);
				fen.ajouter(army.getTextureEnnemi());
			}
			break;
		case 2:
			if(tabEnnemis.size() < 8){ // pour éviter qu'il y ait trop de voitures
				police = new Ennemi("img/police.png",a);
				police.setVitesse((int)(15+score*0.2));
				tabEnnemis.add(police);
				fen.ajouter(police.getTextureEnnemi());
				fen.rafraichir();
			}	
			break;

		case 3:
			if(tabEnnemis.size() < 6){ // pour éviter qu'il y ait trop de voitures
				tonneau_ennemi = new Ennemi("img/tonneau_ennemi.png",a);
				tonneau_ennemi.setVitesse((int)(18+score*0.3));
				tabEnnemis.add(tonneau_ennemi);
				fen.ajouter(tonneau_ennemi.getTextureEnnemi());
			}
			fen.rafraichir();
			break;
		default:
			break;
		}
		fen.rafraichir();

	}


	/////////////////////////////////////////////////////
	// fin
	/**
	 * Teste la fin du jeu. <br /> 
	 * @return True si il y a intersection avec un ennemi et que le joueur n'a plus de vie. <br/>False le cas échéant.
	 */
	public boolean fin(){
		for(int ind=0;ind<(tabEnnemis.size()-1);ind++){  // on check tout l'ArrayList qui contient les ennemis

			if(player1.intersection(tabEnnemis.get(ind)) == true){ // Si y'a un ennemi qui touche le joueur

				if(player1.getVie() <= 0) {


					new Bruitage("sons/explosion.mp3");

					tabEnnemis.get(ind).getTextureEnnemi().setImg("img/explosion.png");    // on fait pop des EXPLOSIONSSSSSSSSSSSS
					player1.getTextureJoueur().setImg("img/explosion.png");
					fen.rafraichir();


					Font policeInitialDrift= null;
					InputStream is = this.getClass().getResourceAsStream("assets/typo.otf");

					try {
						policeInitialDrift = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(48f);;
					} catch (FontFormatException | IOException e) {
						e.printStackTrace();
					}
					Texte aff_gameover = new Texte(Couleur.ROUGE,("GAME OVER"),policeInitialDrift,new Point((Constantes.TAILLEX/2),(Constantes.TAILLEY-100)));
					fen.ajouter(aff_gameover);
					aff_gameover = new Texte(Couleur.ROUGE,("Score total : "+String.valueOf(score)),policeInitialDrift,new Point((Constantes.TAILLEX/2),(Constantes.TAILLEY-250)));
					fen.ajouter(aff_gameover);
					fen.rafraichir();

					// petite pause de 3 secondes	
					try{

						Thread.sleep(3000);
						tabEnnemis.clear();
					}
					catch(Exception e){}
					fen.effacer();
					return true;
				}
				else {
					fen.supprimer(tabEnnemis.get(ind).getTextureEnnemi());
					tabEnnemis.remove(ind);
					player1.setVie(player1.getVie()-1);
					aff_vie.setTexte(String.valueOf(player1.getVie()));

				}
			}	    
		}
		return false;
	}





	/**
	 * Génération aléatoire décor 
	 */
	public void GenererDecor(int positionX, int switchtexture){


		b = new Point(0,Constantes.TAILLEY);
		switch(positionX){

		case 1:
			b.setX(25); // GAUCHE
			break;

		case 2:
			b.setX(Constantes.TAILLEX-125);   // DROITE

			break;
		}//fin de switch


		switch(switchtexture){

		case 1: // 1 - Les tonneaux
			bcptono = new Texture("decor/bcptono.png",new Point(b));
			tabDecor.add(bcptono);
			fen.ajouter(bcptono);
			break;

		case 2: // 2 - Le tonneau tout seul
			tonottseul = new Texture("decor/Tonneau.png",new Point(b)); 
			tabDecor.add(tonottseul);
			fen.ajouter(tonottseul);
			break;

		case 3: // 3 - Le soldat
			soldat = new Texture("decor/soldat.png",new Point(b));	    
			tabDecor.add(soldat);
			fen.ajouter(soldat);
			break;

		case 4:  // 4 - Le mur de sacs de sable
			mursac = new Texture("decor/mursac.png",new Point(b));
			tabDecor.add(mursac);
			fen.ajouter(mursac);
			break;


		}//fin de switch
		fen.rafraichir();	



	}//genererdecor


	/**
	 * génération aléatoire de vie
	 */
	public void GenererVie(int posX) {
		Point c = new Point(0,Constantes.TAILLEY);
		switch (posX){ // position

		case 1:
			c.setX(150);
			break;

		case 2:
			c.setX(251);
			break;

		case 3:
			c.setX(352);
			break;

		case 4:
			c.setX(453);
			break;

		case 5:
			c.setX(554);
			break;			
		}
		vie = new Vie(c);
		tabVies.add(vie);
		fen.ajouter(vie.getTextVie());
		fen.rafraichir();

	}
	
	/**
	 * Actualise la position du joueur 2
	 * @param x Coordonnée en largeur
	 * @param y Coordonnée en hauteur
	 */
	public void setPosJ2(int x, int y)
	{
		player2.getTextureJoueur().setA(new Point(x,y));
		fen.rafraichir();
	}
	
	
}//fin de classe