
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Affichages de boxes pour création / connexion de compte.
 * @author loan
 */
public class MultiBox {

	public static boolean ERROR = false;

	public static Client client;


	public MultiBox()
	{
		if(Menu.serveurEnLigne)
		{
			//default icon, custom title
			int reply = JOptionPane.showConfirmDialog(null, "Possèdes-tu un compte sur le service online ?", "Vérification de compte", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				try {
					client = new Client();
					client.startClient();
				} catch (IOException e) {
					e.printStackTrace();
					ERROR = true;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Inscrivons-nous ensemble alors ;-)");
				try {
					client = new Client();
					client.inscription();
				} catch (IOException e) {
					ERROR = true;
				}catch (Exception e) {
					e.printStackTrace();
					ERROR = true;
				}

			}
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Serveur hors ligne");
		}
	}
}