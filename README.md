#Maze Project 2 - Bachelor 1

##Auteurs
* 

##Partie 1
Nous avons ajouté une methode *void resetAnimal()* dans la classe Animal afin de pouvoir effacer la memoire de chacun des animaux
avant de les réajouter dans le Maze. Cela nous permet d'avoir plus de controle sur la memoire de chacuns des elements du Maze. Par
exemple, cela a été d'une très grande utilité quand il a fallu memoriser la position de depart de chacun des fantomes et des PacMans
dans la troisieme partie où même lorsque nous avons eu la possibilité de garder certains attribut dans la memeoire du *SpaceInvader*.
Nous avons aussi ajouté un attibut *startPosition* a la classe Animal qui est une variable que l'on ne peut changer après lui
avoir assigné une nouvelle valeur (par defaut: *INVALID_POS*)

##Partie 2
Nous avons fait le choix de diviser le zoo en deux types d'animaux: les deterministes et les non-deterministes. Pour cela nous
avons implémenté deux super-classes: *RandomChooser* dont hérites la souris, le hamster et le panda et qui sert a modeliser un
comportement aléatoire face à certaines situation; et la classe *WallFollower* dont hérite monkey et bear sert à modeliser un
comportement de suivit du mur gauche face à certaines situation. Ces super-classes sont abstraites et possèdent des attributs
qui sont partagés par tous ceux qui en hérite: *lastMove* et *orientation* pour respectivement *RandomChooser* et *WallFollower*.

##Partie 3
Nous avons fait le choix d'implementer la gestion du mode (*SCATTER* ou *CHASE*) directement dans la super-classe *Predator* puisque
ce comportement leur est commun. De même la gestion du déplacement est gèrée dans la super-classe. Pour la position de depart nous avons
utilisé le fait que l'attribut *startPosition* de la super-classe *Animal* etait immuable et que nous assignons la position de depart dans
la methode copie, ainsi cela garanti que chaque fantome réapparaîtra bien sur la bonne case meme apres des reset et des stop du dédale.

##Bonus
####Space Invader
* Le Space-Invader est un animal de ZOO qui herite de WallFollower puisqu'il est un suiveur de mur commme le singe ou le bear.
Son algorithme est Un hybride entre un Singe et un Panda simplifié. 
* Il utilise le simple constat que lorsque le singe parcours le labyrinthe, si il passe deux fois par une case intersection
(au minimum trois choix) cela signifie que le singe a d'abord pris un chemin qui conduit à une impasse puis qu'il a rebrousse chemin.
Autrement, il aurait gagné du temps à tourner directement. 
* Cela nous a conduit à faire un algorithme d'un singe intelligent qui apprends de ses erreures. Effectivement, dans la lignée de 
l'idee du panda, le singe marque son terrain dans 2 ArrayList lorqu'il est en terrain inconnu (mode discovery) et utilise ces deux 
arrayList pour eviter de perdre de temps en terrain connu (mode memory).
* Le role du reset a donc ete important à la fois pour garder la memoire des cases marquÈes et pour egalement connaitre la
derniere position du singe.

######Attribtus et fonction
* _unknown : Boolean afin de savoir si le singe est dans un terrain non marqué.
* _lastPosition : Derniere position qui à été reset qui sert à connaitre la position ou il a arreté son marquage
* _last : Derniere direction pour éviter de repartir en arriere lorsque la case est marquée une seule fois.
* _markedOnce : List des cases deja marques une seule fois.
* _markedTwice : List des cases deja marques une seule fois.

######Methode utilitaire
* *private void markTile(Vector2D v)*: Methode qui est appele dans move pour marquer la case sur laquelle le singe passe
en mode decouverte.
* *private Direction discoveryMode(Direction[] choices)*: Methode qui renvois la prochaine direction en discoveyMode
et qui appele markTile(Vector2D v) pour marquer la case.
* *private Direction memoryMode(Direction[] choices)*: Methode qui renvois la prochaine direction en memoryMode
Elle fait son choix en parcourant les choix et en allant dans la case qui est marque une seule fois lorque le
space ivader est dans une intersection ou renvois un mouvement de singe si il n'est pas dans une intersection.
* *private boolean isIntersection(Direction choices[])*: Methode qui determine si le SI est dans une intersection.

######Algorithme
Si il est arrivé a la position ou il a été reset: Unknown = true<br />
Si il est dans un territoire inconnu (unknown=true): lancement du mode memoire, sinon lancement du mode decouvrte.

####PacMan
En construction ....
