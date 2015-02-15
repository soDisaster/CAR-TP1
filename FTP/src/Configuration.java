public final class Configuration {

	private static Configuration instance = null;

	private static String rootDir = "./";
	private static int port = 9999;
	private static String welcome = "Bienvenue sur le FTP qui vaut son pesant de MasterBall.\nPika! Pika!";

	/**
	 * Créé une nouvelle configuration.
	 */
	private Configuration() {
	}

	/**
	 * Retourne l'instance d'une Configuration.
	 * 
	 * @return l'instance de Configuration
	 */
	synchronized public static Configuration getInstance() {
		if (Configuration.instance == null) {
			Configuration.instance = new Configuration();
		}
		return Configuration.instance;
	}

	/**
	 * Retourne le répertoire racine du serveur FTP.
	 * 
	 * @return the rootDir
	 */
	public static String getRootDir() {
		return rootDir;
	}

	/**
	 * Retourne le port d'écoute du serveur.
	 * 
	 * @return the port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Retourne le message d'acceuil du serveur.
	 * 
	 * @return the welcome
	 */
	public static String getWelcome() {
		return welcome;
	}

}
