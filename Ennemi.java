
import MG2D.geometrie.*;  
class Ennemi{ // Définition de la classe
    
    ///////////////////////////////////// Attributs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
    
    Texture textEnnemi; //Texture de l'ennemi
    int vitesse; // Vitesse de l'ennemi
    
    ///////////////////////////////////// Constructeurs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    // Constructeur par défaut
    public Ennemi(){
	
	textEnnemi = new Texture("voiture.jpg",new Point(0,0),75,100);
	vitesse = 5;
    }
    
    // Constructeur par copie
    public Ennemi(Ennemi ee){
	
	textEnnemi = new Texture(ee.getTextureEnnemi());
	vitesse = ee.getVitesse();
    }
    
    
    //Constructeur avec texture en parametre
    public Ennemi(java.lang.String chemin){
	textEnnemi= new Texture(chemin,new Point(0,0),75,100);
	vitesse = 5;
    }
    
    public Ennemi(java.lang.String chemin, Point a){
	textEnnemi= new Texture(chemin,a,75,100);
	vitesse = 5;
    }  
    
    ////////////////////////////////// Accesseurs & Mutateurs \\\\\\\\\\\\\\\\\\\\\\\\\\\ \

    
    // Texture
    
    public Texture getTextureEnnemi(){
	return textEnnemi;
    }

    public void setTextureEnnemi(Texture text){
	textEnnemi = text;
    }
    
    // Vitesse
    
    public int getVitesse(){
	return vitesse; 
    }
    
    public void setVitesse(int vit){
	vitesse = vit;
	
    }
    
    
    
    ////////////////////////////////////////// Methodes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ \
    


    //intersection
    public boolean intersection(Ennemi e){
	return textEnnemi.getBoiteEnglobante().intersection(e.getTextureEnnemi());
    }



    //equals
    public boolean equals(Ennemi ee){
	
	return textEnnemi == ee.textEnnemi && vitesse == ee.vitesse;
	
    }
    
    
    //toString
    public String toString(){
	
	return new String("Texture: "+textEnnemi+"\n Vitesse: "+vitesse);
	
    }

   
    
}
