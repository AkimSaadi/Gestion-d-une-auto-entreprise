-------------------------
EQUIPE 3 - TER Projet Gestion d'autoentreprise

Georges Bardaghji
Thomas Gomiz
Régis Luu Vu
David Ragaru
Akim Saadi
M1 INFORMATIQUE - CAMPUS Luminy
2020/2021

-------------------------
CONTENU

Ce rendu contient :
- Un dossier "Documents" contenant 4 documents : le Cahier des charges, l'analyse détaillée, le rapport final et le Gantt.
- Un dossier "Librairies" contenants 3 dossiers : "javafx" où est stocké la sdk 16 de javafx, "sqljar" où est stocké la jar mysql-connector-java et sqldata où sont stockés les fichiers sql. nécessaires au peuplement de la base.
- Un dossier "Programme" contenant le code de l'application réalisée.
- Le README que vous êtes en train de lire.
-------------------------
INSTALLATION


Cette installation est basée sur un PDF qui nous a été fourni en L1 à Luminy au cours d'un projet en JavaFX.

Nous avons utilisé INTELLIJ comme IDE et malheureusement nous n’avons pas testé le projet sur un autre IDE, donc ce manuel sera uniquement valable sur INTELLIJ.
Nous utilisons une librairie JavaFX fourni dans le dossier ‘’javafx-sdk-16’’, pour pouvoir réaliser des interfaces graphiques pour notre projet. 
Si vous avez installé un JDK en version 10 ou moins, cette librairie JavaFX est déjà contenue dans le JDK. Elle n’est pas contenue dans le JDK en version 15 que nous avons utilisé, proposé par le site d’Oracle.
Si (et seulement si) vous avez le JDK en version 11 et supérieure, suivez les instructions ci-dessous : 

1- Prenez le dossier ‘’javafx-sdk-16’’ et puis mettez-le dans le répertoire de votre choix (sous Linux idéalement dans /usr/lib/javafx/ si vous avez les droits d'administrateur, dans $HOME/java sinon, et sous Windows, dans C:\ProgramFiles\OpenJFX). Créez le dossier OpenJFX si vous ne l’avez pas. 

2. Ouvrez le projet IntelliJ. Dans le menu File > Project Structure, cherchez l’onglet Libraries. Ajoutez une
librairie au projet, en cliquant sur le symbole +. Choisissez Java et indiquez ensuite le répertoire lib dans
le répertoire décompressé (par exemple /usr/lib/javafx/javafx-sdk-16/lib). Fermez ensuite la fenêtre
Project Structure.

3. Ouvrez le menu Run > Edit Configurations. Dans la configuration du Main, modifiez le champ VM options en
Mettant : 
	sous linux : 
	--module-path /usr/lib/javafx/javafx-sdk-16/lib  --add-modules=javafx.controls,javafx.fxml
	sous Windows (n’oubliez pas les guillemets) :
	--module-path "C:\Program Files\OpenFX\javafx-sdk-16\lib" --add-modules=javafx.controls,javafx.fxml

Pour la base de données, nous avons utilisé XAMPP comme serveur. Vous pouvez l'installer depuis ce lien "https://www.apachefriends.org/download.html". Une fois 
que vous avez installé XAMPP :
- Lancez les serveurs APACHE et MYSQL, puis ouvrez votre navigateur web et allez sur "http://localhost/phpmyadmin/".
- Tapez sur new et crééz une nouvelle base de données qui s'appelle "coach". 
- Après cette étape, importez les fichiers .sql qui sont dans le dossier "Base de données" pour peupler la base de données.

-------------------------
PROBLEMES EVENTUELS :

Il est possible que le projet ne reconnaisse pas le jar mysql-connector-java et/ou le dossier lib de la sdk16 de javafx. Il faut donc l'ajouter à la structure du projet. Pour ce faire :
- Allez dans File > Project Structure > Libraries
- Sélectionnez mysql-connector-java, puis Supprimer le PATH de classe et indiquez le PATH vers la jar mysql-connector-java.
- Toujours dans File > Project Structure > Libraries, sélectionnez lib, supprimer les PATH, puis reprécisez le PATH vers javafx-sdk-16/lib.