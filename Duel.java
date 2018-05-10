import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import MG2D.Fenetre;
import MG2D.geometrie.Couleur;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;

public class Duel {

	private Socket socket;
    static BufferedReader read;
    static PrintWriter output;

	@SuppressWarnings("static-access")
	public Duel(Fenetre f)
	{
		f.effacer();
		// -- Attente du lancement du serveur ( sécurité si premier joueur sur les deux )
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		
		// -- Connexion au serveur de Duels
		try {
			socket = new Socket(Constantes.HOST, 9091);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// -- Envoi du pseudo
		output.println(MultiBox.client.Pseudo);
		output.flush();
		

		// -- Affichage de l'écran d'attente
		f.effacer();
		Font policeDuJeu = null;
		try {
			InputStream is = this.getClass().getResourceAsStream("assets/typo.otf");

			policeDuJeu = Font.createFont(Font.TRUETYPE_FONT, is);
			policeDuJeu = policeDuJeu.deriveFont(24f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		//Ecran d'accueil
		Texture accueil = new Texture("decor/accueil.jpg",new Point(0,0),Constantes.TAILLEX,Constantes.TAILLEY);
		Texte joueurConnecte = new Texte(Couleur.BLANC,"Pilote connecté : "+Client.Pseudo,policeDuJeu, new Point(130,Constantes.TAILLEY-20));
		
		f.ajouter(accueil);
		f.ajouter(joueurConnecte);
		
		//Écritures dans le menu
		Texte ligne = new Texte(Couleur.BLANC,"Rercherche",policeDuJeu, new Point(400,320));
		f.ajouter(ligne);
		Texte ligne2 = new Texte(Couleur.BLANC,"d'adversaire",policeDuJeu, new Point(350,290));
		f.ajouter(ligne2);
		Texte ligne3 = new Texte(Couleur.BLANC,"Estimé : 4sec",policeDuJeu, new Point(360,200));
		f.ajouter(ligne3);		
		f.rafraichir();
		
		//TODO : Reception du lancement de la partie
        try {
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ligne.setTexte(read.readLine());
        } catch (IOException e) {
			e.printStackTrace();
		}
    	f.supprimer(ligne2);
    	f.supprimer(ligne3);
    	f.rafraichir();


		f.effacer();
		
   	 	// -- Récupération des pseudos des joueurs de la partie
		output.println("Duel/Get/Pseudos");
		output.flush();
    	try {
			JeuDuel.pseudoj1 = read.readLine();
	    	JeuDuel.pseudoj2 = read.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
    	JeuDuel jd = new JeuDuel(f);
        int i42=0;
        int i43=0;


        
        while(true)
        {
    		jd.AvancerUnPasDeTemps();
    		jd.getFenetre().rafraichir();

    		try {
    			
    			
				output.println("Pos/X/"+MultiBox.client.Pseudo+"/"+jd.player1.getTextureJoueur().getA().getX());
				output.println("Pos/Y/"+MultiBox.client.Pseudo+"/"+jd.player1.getTextureJoueur().getA().getY());

				output.flush();
    			
        		i42 = Integer.parseInt(read.readLine());
        		i43 = Integer.parseInt(read.readLine());


        		String coordonneesJ1 = read.readLine();
        		String coordonneesJ2 = read.readLine();
        		
        		if(MultiBox.client.Pseudo.equals(coordonneesJ1.split("/")[0]))
        		{
            		jd.setPosJ2(Integer.parseInt(coordonneesJ2.split("/")[1]), Integer.parseInt(coordonneesJ2.split("/")[2]));

        		}
        		else
        		{
            		jd.setPosJ2(Integer.parseInt(coordonneesJ1.split("/")[1]), Integer.parseInt(coordonneesJ1.split("/")[2]));       			
        		}
        		
        		
        		
        		if (i42 == 15) {
		        	
		        	//decor
		        	int posX = Integer.parseInt(read.readLine());
		        	int type = Integer.parseInt(read.readLine());
		        	jd.GenererDecor(posX,type);

		        	Thread.sleep(10);

		        	//ennemis        	
		        	int posX2 = Integer.parseInt(read.readLine());
		        	int type2 = Integer.parseInt(read.readLine());
		        	jd.GenererEnnemi(posX2,type2);
		        }
				if (i43 == 25) {
		        	//vie        	
		        	int posX3 = Integer.parseInt(read.readLine());
		        	jd.GenererVie(posX3);
				}

				
        	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    		
    		
        }
	}
	
}
