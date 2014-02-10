package chat.client;

public class Cours {  
	
	int niveau;
	String reseau;
	String format;
	
	Cours(int niveau, String reseau)
	{
		this.niveau = niveau;
		this.reseau = reseau;
		if (reseau.equals("wifi") || reseau.equals("3G") || reseau.equals("4G"))
			format = "(TEXTE, AUDIO, VIDEO)";
		else
			format = "(TEXTE)";
	}
	
	public String get(){
		switch (niveau) {
		case 1:
			return niveauI();			
		case 2:
			return niveauII();
		case 3:
			return niveauIII();
		case 4:
			return niveauIV();
		case 5:
			return niveauV();
		default:
			return "--";
		}
	}
	
	public String niveauI()
	{		
		
		String data = 
				"Apprenez à programmer en Java\n" +
				"Partie 1 - Bien commencer en Java\n" +
				"=================================\n" +
				"Ch 1 : Installer les outils de développement " + format + "\n" +
				"Ch 2 : Les variables et les opérateurs " + format + "\n" +
				"Ch 3 : Les conditions " + format + "\n" +
				"Ch 4 : Les boucles " + format + "\n" +
				"Ch 5 : Les tableaux" + format + "\n";
	
		return data;
	}
	public String niveauII()
	{
		String data =
				"Partie 2 - Java Orienté Objet\n" +
				"=================================\n" +
				"Ch 1 : Votre première classe " + format + "\n" +
				"Ch 2 : L'héritage " + format + "\n" +
				"Ch 3 : Les classes abstraites et les interfaces " + format + "\n" +
				"Ch 4 : Les exceptions " + format + "\n" +
				"Ch 5 : La généricité en Java " + format + "\n" +
				"Ch 6 : Les packages " + format + "\n" ;
		
		return data;
	}
	public String niveauIII()
	{
		String data = 
				"Partie 3 - Java et la programmation événementielle\n" +
				"=================================\n" +
				"Ch 1 : Notre première fenêtre " + format + "\n" +
				"Ch 2 : Interagir avec des boutons " + format + "\n" +
				"Ch 3 : Les champs de formulaire " + format + "\n" +
				"Ch 4 : Les menus et boîtes de dialogue " + format + "\n" +
				"Ch 5 : Les composants graphique " + format + "\n" ;
		return data;
	}
	public String niveauIV()
	{
		String data = 
				"Partie 4 - Interactions avec les bases de données\n" +
				"=================================\n" +
				"Ch 1 : JDBC : la porte d'accès aux bases de données " + format + "\n" +
				"Ch 2 : Fouiller dans sa base de données " + format + "\n" +
				"Ch 3 : Lier ses tables avec des objets Java : le pattern DAO " + format + "\n" ;
		return data;
	}
	public String niveauV()
	{
		String data = 
				"Partie 5 - JEE\n" +
						"=================================\n" +
						"Ch 1 : Introduction au Java EE " + format + "\n" +
						"Ch 2 : MVC " + format + "\n" +
						"Ch 3 : Servlets " + format + "\n" +
						"Ch 4 : JSP " + format + "\n" +
						"Ch 5 : JSTL " + format + "\n"; 
		return data ;
	}
	public static void main(String[] args) {
		
	}

}
