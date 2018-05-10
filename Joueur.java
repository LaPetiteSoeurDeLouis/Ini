
import MG2D.*; // Importation MG2D
import MG2D.geometrie.*;

class Joueur { // Définition de la classe

	///////////////////////////////////// Attributs
	///////////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	Texture textJoueur;
	private int vie;
	///////////////////////////////////// Constructeurs
	///////////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// Constructeur par défaut
	public Joueur() {
		setVie(0);
		textJoueur = new Texture("img/stickman.png", new Point(0, 0), 50, 100);
	}

	// Constructeur par copie
	public Joueur(Joueur j) {

		setVie(0);
		textJoueur = new Texture(j.getTextureJoueur());

	}

	// Constructeur avec texture en paramètre
	public Joueur(Texture text) {
		setVie(1);

		textJoueur = new Texture(text);
	}

	// Constructeur avec largeur en parametre
	public Joueur(int larg) {
		setVie(0);

		textJoueur = new Texture("img/stickman.png", new Point(0, 0), larg, 100);

	}

	// Constructeur avec point en parametre (position)
	public Joueur(Point p) {
		setVie(0);

		textJoueur = new Texture(Main.getTextureVoiture(), new Point(p.getX(), p.getY()), 50, 100);
	}

	// Constructeur avec point et largeur en paramètre
	public Joueur(Point p, int larg) {
		setVie(0);

		textJoueur = new Texture("img/c3.png", new Point(p.getX(), p.getY()), larg, 100);
	}

	// Constructeur avec point, largeur et hauteur en parametre
	public Joueur(Point p, int larg, int haut) {
		setVie(0);

		textJoueur = new Texture("img/stickman.png", new Point(p.getX(), p.getY()), larg, haut);
	}

	//////////////////////////////// Accesseurs & Mutateurs
	//////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// Texture

	public Texture getTextureJoueur() {
		return textJoueur;
	}

	public void setTextureJoueur(Texture text) {
		textJoueur = text;
	}

	///////////////////////////////////////// Méthodes
	///////////////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// intersection ennemi
	public boolean intersection(Ennemi e) {

		return textJoueur.getBoiteEnglobante().intersection(e.getTextureEnnemi());

	}

	// intersection vie
	public boolean intersection(Texture t) {
		return textJoueur.getBoiteEnglobante().intersection(t);
	}
	
	// affichage joueur
	public void add(Fenetre f) {

		f.ajouter(textJoueur);
	}

	// Equals
	public boolean equals(Joueur j) {
		return textJoueur == j.getTextureJoueur();

	}

	// toString
	public String toString() {
		return new String("Texture joueur : " + textJoueur);
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public boolean intersection(Vie vie) {
		return textJoueur.getBoiteEnglobante().intersection(vie.getTextVie());	
	}

}
