# CAR-TP1


Thibault Rosa
Anne-Sophie Saint-Omer


Structure de notre projet
-------------------------

3 classes :
- FtpRequest.java 
- Serveur.java
- ThreadServeur.java



FtpRequest.java 
---------------

Contient toutes les méthodes process envoyées à l'utilisateur.

 
#### processUSER

Utilisateur reconnu, en attente du mot de passe.
dout.write(new String("331\n").getBytes());


#### processPASS

Si le mot de passe correspond. L'utilisateur est connecté.

dout.write(new String("230\n").getBytes());
Utilisateur ou mot de passe eronné.
dout.write(new String("430\n").getBytes());


#### processLIST

Liste les fichiers du dossier fichiersLIST

File status okay; about to open data connection.
dout.write(new String("150\n").getBytes());

Ok
dout.write(new String("226\n").getBytes());


#### processRETR

Utilisé pour prendre un fichier du répertoire distant et le déposer dans le répertoire local
	
Data connection open; no transfer in progress.
dout.write(new String("226\n").getBytes());

Erreur fichier invalide...
dout.write(new String("550\n").getBytes());



#### processSTORE

Fichier répertoire local dans répertoire distant

/Data connection open; no transfer in progress.
dout.write(new String("226\n").getBytes());

Erreur fichier invalide...
dout.write(new String("550\n").getBytes());


#### processSYST

NAME system type. Where NAME is an official system name from the register


#### processPORT

Utilisé pour ouvrir une connexion de transfert de fichiers.
Utile pour STORE, RETR et LIST




Serveur.java 
------------



#### Méthode loop : 

Boucle infinie
socket.accept() permet d'accepter les demandes de connexion.


#### Méthode initUser()

Lit le fichier users.txt et ajoute le contenu dans une Map





ThreadServeur.java
-------------------


Dans le constructeur on initialise un DataInputStream et DataOutputStream

#### Méthode keyword

Récupère par exemple USER dans le cas de USER saintomer


#### Méthode arg

Retourne argument de la commande si il existe


On créé une instance de FtpRequest grâce à keyWord et arg. On invoque (invoke()) la méthode correspondante présente dans FtpRequest.
 => Principe de réflexivité. 





Informations supplémentaires
----------------------------


On utilise : 

ftp 127.0.0.1 9999 

car ftp localhost 9999 localhost => IPV6

Cela posait problème pour le processLIST, envoyait une commande différente.








