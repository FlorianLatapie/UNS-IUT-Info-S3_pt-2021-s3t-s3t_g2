# S3T-G2
Projet Tutoré 2020 en S3T Groupe 2 

**Membres de l'équipe :** 
- Latapie Florian **Chef d'équipe** (FlorianLatapie)
- Langlade Théo (LangladeTheo)
- Boulton Nina (ninaboulton)
- Blondin Remy (RemyBldn)
- Sébastien Aglaé (sebastienaglae)
- Maxime Lecerf  **Sous Chef d'équipe** (Maxime-Lecerf-Info)
- Kevin Pouzaud (G2-KevinPouzaud)
- Vincent Calatayud (G2-VincentCalatayud)
- Emeric Maximil (Emeric-Maximil)
- Jehan Berthé (Jehan-Berthé)
- Aurélien Arnault(AurelienARNAULT)
- Bouchemot Yanis (yanis-bouchemot)
- Le Bihan Leo (LeBihanLeo)
- Devauchelle Alex (AlexDevauchelle)
- Chatelain Baptiste (Baptiste-Chatelain)
- Gontero Tom (GonteroTom)
- Floran Tatopoulos (FloranTatopoulos)

**Chefs de section :**
- Classes et tests métier java : Alex Devauchelle
- Autres classes java : Alex Devauchelle, Sébastien Aglaé 
- Interfaces Graphiques JavaFx : Sébastien Aglaé  
- Gestion GitHub : Aurélien Arnaud 
- Classes réseau : Sébastien Aglaé 
- Programmation algorithmes des bots : Alex Devauchelle
- Gestion des votes : N/D
 
**Politique de branches:**
 
![alt text1][logo]

[logo]: ./Branches.png "Branches"

La branche pp (programme pricipale) Contient: 
l’interface graphique principale du jeu qui permet de configurer le programme, créer une partie, …
Le moteur du jeu qui gère le déroulement d’une partie.
La partie communication qui dialogue avec les joueurs virtuels et les interfaces déportées.
L’interface graphique « plateau de jeu » qui est utilisée durant une partie pour afficher le plateau de jeu (le centre commercial) et les autres éléments de jeu (pions, pioche, choix des joueurs, …) ou de configuration (aide, réglages, …)

La branche IDJR (Interfaces déportées pour les joueurs réel) contient le programmes permettant à un joueur de rejoindre une partie lancée sur le PP et qui fournit au joueur réel (humain) une interface graphique privée lui permettant de jouer sa partie (visualiser ses cartes, voter, choisir sa destination, …).

Les branches Bot Faible, Bot Moyen et Bot Fort contiennent les codes des bots: une « IA » capable de jouer une partie à la place d’un joueur réel.

La branche réseau cotient le code réseau. Elle sera fusionné avec toute les branches de 1 niveaux supérieur.

La branche produit sert a fusionner plusieurs branches de niveaux inférieurs pour vérifier que les codes sont bon avant de push dans la branche Master.

La branche Master est la branche qui contiendra notre projet entié et fini.

Seul les chefs de sections pourron push dans les branches Master et Production.
