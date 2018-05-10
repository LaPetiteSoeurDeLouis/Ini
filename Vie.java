import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;

public class Vie {

	/**
	 * Texture de la vie
	 */
    private Texture textVie; 
    
    public Vie() {
    	setTextVie(new Texture("img/vie.png",new Point(0,0)));
    }
    
    
    public Vie(Point a) {
    	setTextVie(new Texture("img/vie.png",a));
    }


	public Texture getTextVie() {
		return textVie;
	}


	public void setTextVie(Texture textVie) {
		this.textVie = textVie;
	}

}
