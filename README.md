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
- Gestion GitHub : Aurélien Arnault 
- Classes réseau : Sébastien Aglaé 
- Programmation algorithmes des bots : Alex Devauchelle
- Gestion des votes : N/D
 
**Politique de branches:**
 
![alt text1][logo]

[logo]: ./Branches.png "Branches"

La branche PP (Programme Pricipal) contient : 
l’interface graphique principale du jeu qui permet de configurer le programme, créer une partie…
Le moteur du jeu qui gère le déroulement d’une partie.
La partie communication, qui dialogue avec les joueurs virtuels et les interfaces déportées.
L’interface graphique « plateau de jeu » qui est utilisée durant une partie pour afficher le plateau de jeu (le centre commercial) et les autres éléments de jeu (pions, pioche, choix des joueurs…) ou de configuration (aide, réglages…).

La branche IDJR (Interfaces Déportées pour les Joueurs Réels) contient le programme permettant à un joueur de rejoindre une partie lancée sur le PP et qui fournit au joueur réel (humain) une interface graphique privée lui permettant de jouer sa partie (visualiser ses cartes, voter, choisir sa destination…).

Les branches Bot Faible, Bot Moyen et Bot Fort contiennent les codes des bots : une « IA » capable de jouer une partie à la place d’un joueur réel.

La branche réseau cotient le code réseau. Elle sera fusionnée avec toutes les branches de 1 niveau supérieur.

La branche produit sert à fusionner plusieurs branches de niveaux inférieurs afin de vérifier que les codes sont bons avant de push dans la branche Master.

La branche Master est la branche qui contiendra notre projet entier et fini.

Seul les chefs de sections pourront push dans les branches Master et Production.
