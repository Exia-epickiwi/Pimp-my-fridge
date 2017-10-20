# Script de la vidéo de présentation du projet

## Personnages

* **Steve Trabajo** Chef de projet ayant eu l'idée original (Romain)
* **Fabrice Genuino** Spécialiste hardware du projet (Tanguy)
* **Jean-Michel Poinjar** Spécialiste software du projet (Baptiste)

## Script

*Timelapse de la mise en place de la scène des interviews*

*Interview du chef de projet*

**STEVE TRABAJO**

J'ai imaginé ce projet il y à longtemps et j'ai toujours voulu l'amener a la réalité. Ce projet me tiens très a cœur et je suis persuadé qu'il va révolutionner la manière dont on se désaltère et dont on perçois la réfrigération.

Ce si petit objet est une révolution dans son domaine et le projet PMF va permettre à chacun de se désaltérer malgré le réchauffement climatique.

PMF fut un projet d'une semaine très intense d'un équipe de 3 personnes très investies. On a fait beaucoup de rencontres et nous avons débattus avec toute l'équipe pour aboutir un produit révolutionnaire, au design inégalable et parfaitement fonctionnel.

*Interview du spécialiste Hardware*

**Fabrice Genuino**

Pour aboutir au premier prototype nous avons du nous plonger dans la Thermodynamique et l’électronique des circuits automatiques.

Nous avons commencé par modéliser le système avec de nombreux calculs, afin d'approcher d'un système parfait en terme d'isolation, d'échange de chaleur et de condensation. Nous avons ensuite modélisé le système a mettre en place pour créer P-Fresh qui permet de conserver la température dans le réfrigérateur avec un module Peltier de dernière génération.

*Timelapse du spécialiste entrain de faire les calculs de steinhart-hart*

Puis on a conçu le circuit P22 qui va contrôler la température intelligemment. Il embarque prés de 2 capteurs ultra-précis pour une fiabilité au delà de toutes nos espérances. Ce circuit analyse la température et l'humidité dans le réfrigérateur pour conserver la température idéale et empêcher la désagréable sensation d'humidité sur la canette, tout cela, afin d'offrir la meilleur expérience possible à l'utilisateur.

On a ensuite monté et soudé le circuit à la main pour respecter les valeurs de l'entreprise et d'éviter tout problème matériel. Le circuit est entouré d'une coque en plastique ultra-résistante couplée au circuit de contrôle.

*Timelapse du spécialiste en train de souder les composants*

C'est ainsi que l'on à créé notre tout premier prototype du projet Pi-Fridge qui dépasse déjà toutes nos prévisions.

*Interview du spécialiste software*

**Jean-Michel Poinjar**

En tant que développeur pointilleux, je souhaitais une interface qui allie simplicité d'utilisation pour l'utilisateur et qui lui montre toutes les informations disponibles sur son réfrigérateur. De nombreuses heures de développement ont permis d'aboutir à une interface très épurée et réactive. Ce code respecte evidemment le patron de conception MVC, pour une meilleur maintenabilité.

*Timelapse code*

Elle permet à l'utilisateur de simplement régler la température qu'il souhaite à l'intérieur de son Pi-Fridge et d'avoir accès à différents graphiques sur l'évolution des paramètres. Cela a été rendu possible grâce à la communication au plus haut niveau grâce à JSON du module Arduino du Pi-Fridge et l'interface client Java.

*Démonstration interface*

