# Pimp my fridge

*Pimp my fridge* est un projet de 3e année dont l'objectif est de créer un circuit de refroidissement intelligent a l'aide d'une Arduino contrôlée par une application Java sur l'ordinateur connecté.

## Cahier des charges

* Poster
    - Explication des concepts physiques utilisés
    - Effet Peltier
    - Système asservi
    - Régulateur
    - Calcul de Steinhart-Hart
    - Point de rosée
    - Code Arduino
    - Code client
* Vidéo de présentation
    - Calculs
    - Montage
    - Développement Arduino
    - Développement client
    - Présentation général
    - Publicité (optionnel)
    - Upload sur youtube
* Rapport
    - Explication et justification des calculs effectués
    - Photos des montages
    - Explication et justification des montages
    - Explication de la structure du programme client
* Réfrigérateur USB
    - Refroidir et maintenir une température de consigne donnée
    - Éviter la condensation
    - Contrôler le module Peltier
        + MOSFET
    - Analyser l'humidité du réfrigérateur
        + Hygromètre
    - Analyser la température du réfrigérateur
        + Thermistance
        + Diode
* Code Arduino
    - Récupération des données des capteurs
    - Régulation de la tension sur le module Peltier
    - Transmission des données en série
    - Adaptation de la température sur une température donnée en série
    - Calcul de la température
        + Steinhart-Hart
* Application client
    - Configuration du port à écouter
    - Configuration de la consigne
    - Interface graphique
        + Affichage de la température
        + Affichage de l'humidité
        + Temps restant avant atteinte de la consigne (optionnel)
        + Affichage du point de rosée
        + Bloquer la consigne au dessus du point de rosée
        + L'évolution de la température en fonction du temps
        + L'évolution de l'humidité en fonction du temps
    - Gestion du port série
        + Réception des valeurs des capteurs
        + Envoie de la consigne
* Soutenance
    - Présentation de la vidéo
    - Présentation rapide du projet
    - Démo

## Timeline

* *Lundi 16 octobre* Début du projet
    - Storyboard vidéo
    - Design poster
* *Mardi 17 octobre* 
    - Montages
    - Calculs
* *Mercredi 18 octobre* 
    - Fin du développement de la communication série
    - Poster
* *Jeudi 19 octobre* 
    - Fin du développement client & Arduino
    - Poster
    - Montage vidéo
    - Rapport
* *Vendredi 20 octobre* 
    - *12h* Rendu de la vidéo, du poster et du rapport
    - Amélioration code
    - Debuggage code
* *Lundi 23 octobre* Soutenance
