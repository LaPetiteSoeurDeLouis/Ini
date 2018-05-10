import java.awt.HeadlessException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


import javax.swing.JOptionPane;

public class Client{
    Connect c = new Connect();
    static Socket socket;
    static BufferedReader read;
    static PrintWriter output;

    public static String Pseudo;
    
    public void startClient() throws UnknownHostException, IOException{
        //Create socket connection
        socket = new Socket(c.gethostName(), c.getPort());

        //create printwriter for sending login to server
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        //prompt for user name
        String username = JOptionPane.showInputDialog(null, "Entre ton pseudo:");
       
        //send user name to server
        output.println(username);

        //prompt for password
        String password = JOptionPane.showInputDialog(null, "Entre ton mot de passe:");

        //send password to server
        output.println(password);
        output.flush();

        //create Buffered reader for reading response from server
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read response from server
        String response = read.readLine();
        System.out.println(response);

        //display response
        JOptionPane.showMessageDialog(null, response);
        
        if(response.equals("Impossible de t'inscrire sous ce nom, le pseudo est déjà utilisé...")
        		|| response.equals("null")
        		|| response.equals("Login Failed. Mauvais mot de passe ?"))
        {
        	MultiBox.ERROR = true;
        	Client.Pseudo = "";
        }
        else if(response.startsWith("Bon retour sur Initial Drift 2.0")){
        	MultiBox.ERROR = false;
        	Client.Pseudo = username;
            Menu.nombreJoueurs = read.readLine();
        }
        
    }

    public void inscription() throws UnknownHostException, IOException{
        //Create socket connection
        socket = new Socket(c.gethostName(), c.getPort());

        //create printwriter for sending login to server
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        //prompt for user name
        String username = "inscription/";
        username += JOptionPane.showInputDialog(null, "Entre le pseudo désiré:");

        //send user name to server
        output.println(username);

        //prompt for password
        String password = JOptionPane.showInputDialog(null, "Mot de passe:");

        //send password to server
        output.println(password);
        output.flush();

        //create Buffered reader for reading response from server
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //read response from server
        String response = read.readLine();
        System.out.println(response);

        if(response.equals("Impossible de t'inscrire sous ce nom, le pseudo est déjà utilisé...")
        		|| response.equals("null")
        		|| response.equals("Login Failed. Mauvais mot de passe ?"))
        {
        	socket.close();
        	throw new IOException();
        }
        else if(response.startsWith("Bienvenue sur Initial Drift 2.0")){
        	MultiBox.ERROR = false;
        	Client.Pseudo = username;
        }
        
        //display response
        JOptionPane.showMessageDialog(null, response);
    }

    /**
     * Ajoute un record à la base de donnée des highscores
     * @param pseudo Pseudo du joueur
     * @param score Score du joueur
     * @param dateDuJour Date du record
     */
	public void ajouterScoreAuServeur(String pseudo, int score, String dateDuJour)
	{
		System.out.println("Envoi du score : "+pseudo+" - "+score+" - "+dateDuJour);		
        try {
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        output.println("HighScore/Add/"+pseudo+"/"+score+"/"+dateDuJour);
		output.flush();
		try {
			JOptionPane.showMessageDialog(null, read.readLine());
		} catch (HeadlessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Déconnecte le client du serveur
	 */
	public void deconnexion() {
		output.print("QUIT");
		output.flush();		
	}

	public ArrayList<String> getScoresServeur() {
		try {
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        output.println("HighScores/Get");
		output.flush();
		String recordAsString =null;
		try {
			recordAsString = read.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] records = recordAsString.split("&");
		ArrayList<String> tabRecords = new ArrayList<String>();
		for(String r : records)
		{
			tabRecords.add(r);
		}
		
		
		return tabRecords;
	}

	public String getTopScore() {
		try {
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        output.println("HighScores/TopScore");
		output.flush();
		String recordAsString =null;
		try {
			recordAsString = read.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return recordAsString;
	}

	public static String getNombreJoueurs() {
		
		try {
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        output.println("Logs/NombreJoueurs");
		output.flush();
		String recordAsString =null;
		try {
			recordAsString = read.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recordAsString;
	}

	public void rejoindreDuel() {
        output.println("Duel/Start");
		output.flush();		
	}


}