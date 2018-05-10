
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import MG2D.Clavier;
import MG2D.Fenetre;
import MG2D.audio.Bruitage;
import MG2D.audio.Musique;
import MG2D.geometrie.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;

/**
 * Menu de l'application
 * @author loan
 *
 */
public class Menu {

	/**
	 * Fenetre du jeu
	 */
	Fenetre fen;

	/**
	 * Clavier du joueur
	 */
	Clavier clavier;

	/**
	 * Affiche le menu
	 */


	/**
	 * On ne la présente plus
	 */
	Texture miguel;

	/**
	 * Ecran de commandes
	 */
	Texture commandes;


	/**
	 * Curseur du menu
	 */
	Texture curseur;

	/**
	 * Police de caractère du jeu
	 */
	Font policeDuJeu;

	/**
	 * Affichage du statut du serveur de jeu
	 */
	Texte statutServeur;

	public static String nombreJoueurs = "";

	public static boolean serveurEnLigne;

	public Menu() 
	{

		try {
			InputStream is = this.getClass().getResourceAsStream("assets/typo.otf");

			policeDuJeu = Font.createFont(Font.TRUETYPE_FONT, is);
			policeDuJeu = policeDuJeu.deriveFont(24f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		fen = new Fenetre("Initial Drift",Constantes.TAILLEX,Constantes.TAILLEY);
		fen.setAffichageNbPrimitives(true);
		clavier = new Clavier();
		fen.addKeyListener(clavier);

		clavier = fen.getClavier();

		//Ecran de chargement
		miguel = new Texture("decor/miguel.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		fen.ajouter(miguel);

		InputStream isB = this.getClass().getResourceAsStream("sons/pegi18.mp3");

		Bruitage b = new Bruitage(isB);
		try {
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		InputStream isM = this.getClass().getResourceAsStream("sons/mix.mp3");

		new Musique(isM).lecture();
		fen.supprimer(miguel);	
	}

	/**
	 * Menu principal
	 * @return Le choix du menu
	 * @throws IOException
	 */
	public int choisirMode() throws IOException 
	{
		int pos_curseur = 1;

		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		statutServeur = new Texte(Couleur.BLANC,"Statut du serveur : ...",policeDuJeu, new Point(350,30));

		curseur = new Texture("img/curseur.png",new Point(270,300),40,40);
		fen.ajouter(accueil);
		fen.ajouter(curseur);
		fen.ajouter(statutServeur);
		//Écritures dans le menu
		Texte ligne = new Texte(Couleur.BLANC,"Solo",policeDuJeu, new Point(315,320),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Multi",policeDuJeu, new Point(315,290),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Parametres",policeDuJeu, new Point(315,260),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Commandes",policeDuJeu, new Point(315,230),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Scénario",policeDuJeu, new Point(315,200),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Quitter",policeDuJeu, new Point(315,170),Texte.GAUCHE);
		fen.ajouter(ligne);		
		getStatutServeur();
		while(clavier.getEspaceTape() == false){
			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(clavier.getBasTape() && pos_curseur < 6)
			{
				pos_curseur++;
				//translater vers le bas le curseur
				curseur.translater(0, -30);
				fen.rafraichir();
			}

			if(clavier.getHautTape() && pos_curseur > 1)
			{
				pos_curseur--;
				//translater vers le haut le curseur
				curseur.translater(0, 30);
				fen.rafraichir();
			}
		}

		fen.effacer();

		return pos_curseur;	
	}


	/**
	 * Menu multijoueur
	 * @return le choix du menu
	 * @throws IOException
	 */
	public int choisirModeMulti() throws IOException 
	{
		int pos_curseur = 1;

		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		statutServeur = new Texte(Couleur.BLANC,"Statut du serveur : ...",policeDuJeu, new Point(350,30));
		Texte joueurConnecte = new Texte(Couleur.BLANC,"Pilote connecté : "+Client.Pseudo,policeDuJeu, new Point(0,Constantes.TAILLEY-20),Texte.GAUCHE);
		
		curseur = new Texture("img/curseur.png",new Point(270,300),40,40);
		fen.ajouter(accueil);
		fen.ajouter(curseur);
		fen.ajouter(statutServeur);
		fen.ajouter(joueurConnecte);
		
		//Écritures dans le menu
		Texte ligne = new Texte(Couleur.BLANC,"Partie classée",policeDuJeu, new Point(315,320),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Duel",policeDuJeu, new Point(315,290),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Highscores",policeDuJeu, new Point(315,260),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Leaderboard",policeDuJeu, new Point(315,230),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Retour",policeDuJeu, new Point(315,200),Texte.GAUCHE);
		fen.ajouter(ligne);		

		getStatutServeur();
		while(clavier.getEspaceTape() == false){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(clavier.getBasTape() && pos_curseur < 5)
			{
				pos_curseur++;
				//translater vers le bas le curseur
				curseur.translater(0, -30);
				fen.rafraichir();
			}

			if(clavier.getHautTape() && pos_curseur > 1)
			{
				pos_curseur--;
				//translater vers le haut le curseur
				curseur.translater(0, 30);
				fen.rafraichir();
			}
		}

		fen.supprimer(accueil);

		return pos_curseur;	
	}


	/**
	 * Menu des parametres
	 * @return le choix du menu
	 * @throws IOException
	 */
	public int choisirModeParam() throws IOException 
	{
		int pos_curseur = 1;

		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		statutServeur = new Texte(Couleur.BLANC,"Statut du serveur : ...",policeDuJeu, new Point(350,30));

		curseur = new Texture("img/curseur.png",new Point(270,300),40,40);
		fen.ajouter(accueil);
		fen.ajouter(curseur);
		fen.ajouter(statutServeur);
		//Écritures dans le menu
		Texte ligne = new Texte(Couleur.BLANC,"Voiture",policeDuJeu, new Point(315,320),Texte.GAUCHE); // Camaro, C3, camion poubelle, ...
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Map",policeDuJeu, new Point(315,290),Texte.GAUCHE); // Terre, Desert, Neige,...
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Retour",policeDuJeu, new Point(315,260),Texte.GAUCHE);
		fen.ajouter(ligne);		

		getStatutServeur();
		while(clavier.getEspaceTape() == false){
			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(clavier.getBasTape() && pos_curseur < 3)
			{
				pos_curseur++;
				//translater vers le bas le curseur
				curseur.translater(0, -30);
				fen.rafraichir();
			}

			if(clavier.getHautTape() && pos_curseur > 1)
			{
				pos_curseur--;
				//translater vers le haut le curseur
				curseur.translater(0, 30);
				fen.rafraichir();
			}
		}

		fen.supprimer(accueil);

		return pos_curseur;	
	}



	public void choisirVoiture() throws IOException {

		int pos_curseur = 1;

		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		statutServeur = new Texte(Couleur.BLANC,"Statut du serveur : ...",policeDuJeu, new Point(350,30));

		curseur = new Texture("img/curseur.png",new Point(270,300),40,40);
		fen.ajouter(accueil);
		fen.ajouter(curseur);
		fen.ajouter(statutServeur);
		//Écritures dans le menu
		Texte ligne = new Texte(Couleur.BLANC,"Stickman",policeDuJeu, new Point(315,320),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"C3",policeDuJeu, new Point(315,290),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Ambulance",policeDuJeu, new Point(315,260),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Tracteur",policeDuJeu, new Point(315,230),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"DS21",policeDuJeu, new Point(315,200),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Fire truck",policeDuJeu, new Point(315,170),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Remorqueur",policeDuJeu, new Point(315,140),Texte.GAUCHE);
		fen.ajouter(ligne);		


		getStatutServeur();
		while(clavier.getEspaceTape() == false){
			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(clavier.getBasTape() && pos_curseur < 7)
			{
				pos_curseur++;
				//translater vers le bas le curseur
				curseur.translater(0, -30);
				fen.rafraichir();
			}

			if(clavier.getHautTape() && pos_curseur > 1)
			{
				pos_curseur--;
				//translater vers le haut le curseur
				curseur.translater(0, 30);
				fen.rafraichir();
			}
		}

		switch(pos_curseur)
		{
		case 1:
			Main.setTextureVoiture("img/stickman.png");
			break;

		case 2:
			Main.setTextureVoiture("img/c3.png");
			break;


		case 3:
			Main.setTextureVoiture("img/ambulance.png");			
			break;

		case 4:			
			Main.setTextureVoiture("img/tracteur.png");
			break;

		case 5:		
			Main.setTextureVoiture("img/ds21.png");
			break;

		case 6:
			Main.setTextureVoiture("img/pompier.png");			
			break;

		case 7:
			Main.setTextureVoiture("img/remorqueur.png");			
			break;



		}
		fen.supprimer(accueil);

	}

	public void choisirMap() throws IOException {

		int pos_curseur = 1;

		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		statutServeur = new Texte(Couleur.BLANC,"Statut du serveur : ...",policeDuJeu, new Point(350,30));

		curseur = new Texture("img/curseur.png",new Point(270,300),40,40);
		fen.ajouter(accueil);
		fen.ajouter(curseur);
		fen.ajouter(statutServeur);
		//Écritures dans le menu
		Texte ligne = new Texte(Couleur.BLANC,"Terre",policeDuJeu, new Point(315,320),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Herbe",policeDuJeu, new Point(315,290),Texte.GAUCHE);
		fen.ajouter(ligne);
		ligne = new Texte(Couleur.BLANC,"Neige",policeDuJeu, new Point(315,260),Texte.GAUCHE);
		fen.ajouter(ligne);		
		ligne = new Texte(Couleur.BLANC,"Roche",policeDuJeu, new Point(315,230),Texte.GAUCHE);
		fen.ajouter(ligne);		

		getStatutServeur();
		while(clavier.getEspaceTape() == false){
			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(clavier.getBasTape() && pos_curseur < 4)
			{
				pos_curseur++;
				//translater vers le bas le curseur
				curseur.translater(0, -30);
				fen.rafraichir();
			}

			if(clavier.getHautTape() && pos_curseur > 1)
			{
				pos_curseur--;
				//translater vers le haut le curseur
				curseur.translater(0, 30);
				fen.rafraichir();
			}
		}

		switch(pos_curseur)
		{
		case 1:
			Main.setTextureMap("decor/dirt.jpg");
			break;

		case 2:
			Main.setTextureMap("decor/herbe.jpeg");
			break;


		case 3:
			Main.setTextureMap("decor/neige.jpg");			
			break;

		case 4:			
			Main.setTextureMap("decor/roche.jpeg");
			break;

		}
		fen.effacer();
	}


	/**
	 * Ecran des highscores.
	 */
	public void voirHighScores() throws IOException{
		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		statutServeur = new Texte(Couleur.BLANC,"Statut du serveur : ...",policeDuJeu, new Point(350,30));

		fen.ajouter(accueil);
		fen.ajouter(statutServeur);

		ArrayList<String> scores = MultiBox.client.getScoresServeur();

		//Écritures dans le menu
		int i = 320;
		Font police = policeDuJeu.deriveFont(14f);
		for(String r : scores)
		{
			Texte ligne = new Texte(Couleur.BLANC,r,police, new Point(350,i));
			fen.ajouter(ligne);			
			i=i-20;
		}
		Texte ligne = new Texte(Couleur.BLANC,"Q pour revenir en arrière",police, new Point(380,110));
		fen.ajouter(ligne);

		fen.rafraichir();
		getStatutServeur();
		while(clavier.getQTape() == false){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		fen.effacer();
	}


	/**
	 * Récupère le statut du serveur et le nombre de joueurs connectés s'il est en ligne. <Br /> Vert si tout est ok <Br /> Rouge s'il est injoignable
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	private void getStatutServeur() throws IOException 
	{

		InetAddress inet = InetAddress.getByName(Constantes.HOST);
		if(inet.isReachable(3000))
		{
			serveurEnLigne = true;

			statutServeur.setCouleur(Couleur.VERT);
			statutServeur.setTexte("Statut du serveur : EN LIGNE");
			if(MultiBox.client != null && Menu.nombreJoueurs != "")
			{
				nombreJoueurs = MultiBox.client.getNombreJoueurs();
				statutServeur.setTexte("Statut du serveur : EN LIGNE ("+nombreJoueurs+" joueurs)");				
			}
		}
		else
		{
			serveurEnLigne = false;

			statutServeur.setCouleur(Couleur.ROUGE);
			statutServeur.setTexte("Statut du serveur : HORS LIGNE");			
		}
		fen.rafraichir();
	}


	public Fenetre getFenetre() 
	{
		return fen;
	}
}
