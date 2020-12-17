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
 
**Politique de branches:**
 
![alt text1][logo]

[logo]: ./Branches.png "Branches"

Ce github est paramétré pour merge automatiquement si la compilation et les testes passent.

La branche **PP** (*Programme Principal*) contient : 
L’interface graphique principale du jeu qui permet de configurer le programme, créer une partie…
Le moteur du jeu qui gère le déroulement d’une partie.
La partie communication, qui dialogue avec les joueurs virtuels et les interfaces déportées.
L’interface graphique « plateau de jeu » qui est utilisée durant une partie pour afficher le plateau de jeu (le centre commercial) et les autres éléments de jeu (pions, pioche, choix des joueurs…) ou de configuration (aide, réglages…).

La branche **IDJR** (*Interfaces Déportées pour les Joueurs Réels*) contient le programme permettant à un joueur de rejoindre une partie lancée sur le PP et qui fournit au joueur réel (humain) une interface graphique privée lui permettant de jouer sa partie (visualiser ses cartes, voter, choisir sa destination…).

Les branches **Bot Faible**, **Bot Moyen** et **Bot Fort** contiennent les codes des bots : une « IA » capable de jouer une partie à la place d’un joueur réel.

La branche **Réseau** contient le code réseau. Elle sera fusionnée avec toutes les branches de 1 niveau inférieur.

La branche **Production** sert à fusionner plusieurs branches de niveau supérieur afin de vérifier que les codes sont bons avant de push dans la branche Master.

La branche **Master** est la branche qui contiendra notre projet fonctionnel.

Seuls les chefs de section pourront push dans les branches Master et Production.

**Comment lancer le jeu ?**

Attention à ne pas séparer les fichiers, ils doivent tous garder le même dossier. 

Ordre à respecter : 
➔ 1.Lancer le PP en premier (Instruction ci-dessous) 
➔ 2.Puis l’IDJR et/ou les bots (Instruction ci-dessous)

|Pour le PP|Pour l’IDJR|Pour le BOT|
|------|------|------|
|➔Lancez le PP.bat|➔Lancez le IDJR.bat|➔Lancez le BOT.bat|
||||
|➔Jouer|➔Saisir votre nom (optionnel)|➔La connexion se fait automatique au PP|
|➔Saisir le nom de la partie (optionnel), le nombre total de joueurs (requis) et le nombre de bots (requis) |➔Saisir votre nom (optionnel)||
|➔Attendez que les IDJR et/ou bots se connectent|||
|➔Choisir la couleur de chaque joueur|||
|➔Lancer la partie |||
||➔Selon la phase du jeu, choisissez de déplacer vos personnages ||
|➔Affichage du gagnant|➔Affichage du gagnant|➔Affichage du gagnant|
|➔Vous pouvez à présent relancer une partie|➔Vous ne pouvez pas à relancer une partie. Il est nécessaire de redémarré |➔Vous pouvez à présent relancer une partie (Parfois cela n’est pas possible)|

**Gestion Gitignore**
Notre fichier .gitignore contient les fichiers a exclure, ce sont donc les fichiers inutile pour l'ensemble des contributeurs du projet. Ils sont propre a l'utilisateur et donc ils ne doivent pas etre transmit. Les fichiers inutiles de nos IDE (Eclipse & Intellij) et Maven sont exclus.
