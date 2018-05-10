
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import MG2D.*;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;

public class Main {

	// Attributs
	/**
	 * Chemin d'accès à la texture de la voiture choisie dans les paramètres.
	 */
	private static String textureVoiture = "img/stickman.png";

	/**
	 * Chemin d'accès à la texture de la map choisie dans les paramètres.
	 */
	private static String textureMap = "decor/dirt.jpg";

	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {

		// attributs
		int i42 = 0;
		int i43 = 0;
		Menu m = new Menu();
		int i = Constantes.ChoixParam;
		Fenetre f = m.getFenetre();
		Clavier c = f.getClavier();
		
		while (i != Constantes.ChoixQuitter) {
			i = m.choisirMode();
			switch (i) {

			case Constantes.ChoixSolo:
				Jeu j = new Jeu(f, false);
				// Jeu
				while (!j.fin()) {

					j.AvancerUnPasDeTemps();
					j.getFenetre().rafraichir();
					if (i42 == 15) {
						j.GenererDecor();
						j.GenererEnnemi();
						i42 = 0;
						i43++;
					}
					if (i43 == 25) {
						i43 = 0;
						j.GenererVie();
					}
					i42++;
				}
				break;

			case Constantes.ChoixMulti:
				new MultiBox();
				if (!MultiBox.ERROR && Menu.serveurEnLigne) {
					int choixMulti = 1;
					while (choixMulti != Constantes.ChoixMultiRetour) {
						choixMulti = m.choisirModeMulti();

						switch (choixMulti) {

						case Constantes.ChoixMultiClassee:
							// new Jeu(); + récupération du score final
							Jeu jc = new Jeu(f, true);
							// Jeu
							while (!jc.fin()) {

								jc.AvancerUnPasDeTemps();
								jc.getFenetre().rafraichir();
								if (i42 == 15) {
									jc.GenererDecor();
									jc.GenererEnnemi();
									i42 = 0;
									i43++;
								}
								if (i43 == 25) {
									i43 = 0;
									jc.GenererVie();
								}
								i42++;
								;
							}
							MultiBox.client.ajouterScoreAuServeur(MultiBox.client.Pseudo, jc.getScore(),
									(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
							break;

						case Constantes.ChoixMultiDuel:
							MultiBox.client.rejoindreDuel();
							new Duel(f);
							
							break;

						case Constantes.ChoixMultiHighscore:
							m.voirHighScores();
							// new TableauHighScores();
							break;

						case Constantes.ChoixMultiLadderboard:
							// new LadderBoardDuels();
							break;

						case Constantes.ChoixMultiRetour:
							MultiBox.client.deconnexion();
							MultiBox.client = null;
							f.effacer();
							break;
						}

					}
				}
				break;

			case Constantes.ChoixParam:
				f.effacer();
				int choixParam = 1;
				while (choixParam != Constantes.ChoixParamRetour) {
					choixParam = m.choisirModeParam();

					switch (choixParam) {

					case Constantes.ChoixParamVoiture:
						m.choisirVoiture();
						break;

					case Constantes.ChoixParamMap:
						m.choisirMap();
						break;
						
					case Constantes.ChoixParamRetour:
						break;
					}

				}
				break;

			case Constantes.ChoixCommandes:
				f.effacer();
				Texture commandes = new Texture("decor/commandes.png", new Point(0, 0), Constantes.TAILLEX,
						Constantes.TAILLEY);
				m.getFenetre().ajouter(commandes);
				while (c.getQTape() == false) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				f.supprimer(commandes);
				f.rafraichir();
				break;

			case Constantes.ChoixScenario:
				f.effacer();
				Texture scenario = new Texture("decor/scenario.jpg", new Point(0, 0), Constantes.TAILLEX,
						Constantes.TAILLEY);
				m.getFenetre().ajouter(scenario);
				while (c.getQTape() == false) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				f.supprimer(scenario);
				f.rafraichir();
				break;

			case Constantes.ChoixQuitter:
				System.exit(0);
				System.exit(0);
				break;
			}
		}

	}

	public static String getTextureVoiture() {
		return textureVoiture;
	}

	public static void setTextureVoiture(String textureVoiture) {
		Main.textureVoiture = textureVoiture;
	}

	public static String getTextureMap() {
		return textureMap;
	}

	public static void setTextureMap(String textureMap) {
		Main.textureMap = textureMap;
	}
}
