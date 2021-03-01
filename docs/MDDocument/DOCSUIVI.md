![Logo]([IMAGE:LOGO])
* * *

Objectif du document  
======

Présentation  
--------------  
Le présent document permet de suivre les différentes étapes du projet « COMPAS » dans sa version 1. Il couvre la période qui va du cadrage des besoins des utilisateurs jusqu'à à la livraison des applicatifs réalisés.  
Pour ce faire il rend compte :  
*	Des différentes phases du projet (cadrage, réalisation, validation)  
*	Des décisions entérinées et arbitrages effectués  
*	Des éléments structurants de conception ou de réalisation partagés  

Mode de fonctionnement  
----------------------  
Ce document est initialisé en début de projet, il est mis à jour régulièrement pour prendre en compte les éléments inconnus lors de son initialisation et/ou les changements en cours de projet.  

Points non traités    
---------------------- 
Un certain nombre de points ne sont pas abordés dans le présent document. Ils font l'objet de documents spécifiques.  
Il s'agit notamment :  
*	Du support documentaire à la phase de validation (ex : Cahier de Recette, ...) qui est de la responsabilité de NRCO  
*	Du support documentaire fonctionnel détaillé qui est livré par Simplicité Software sous la forme d'un dossier de paramétrage Simplicité®  
*	Du support documentaire technique qui est livré par Simplicité Software sous la forme de manuels d’installation et d’exploitation  
*	Des interactions projet pendant les réunions entre les équipes de NRCO et Simplicité Software  

Organisation projet  
======

Méthodologie   
--------------  
Le projet sera réalisé via un processus projet agile simplifié destiné à la réalisation de prototypes opérationnels pour l’application COMPAS.  
Un projet agile se décompose en 3 phases successives :  
*	Phase de cadrage  
*	Phase de réalisation (Conception, Paramétrage et Développement)  
*	Phase de validation (Recette et Intégration)  
 
![Méthodologie]([IMAGE:METHODO])  

![Méthodologie]([IMAGE:METHODO1])

La phase de cadrage a pour objectif la compréhension la plus complète des besoins, du contexte métier, des processus métier et outils existants, des enjeux du projet.  
C’est une phase d’approfondissement global de l’expression de besoin à laquelle s’ajoute un échange sur les orientations futures du projet.  
Dans le cadre de la version 1 de l’application COMPAS, la phase de cadrage se limite à 1 réunion fonctionnelle avec les interlocuteurs projet le 29 Novembre, par une présentation générale lors de la réunion de lancement de projet du processus de saisie des événements et des faits marquants.   
La phase de réalisation a pour objectif de construire, de manière itérative et en interaction avec NRCO, une solution logicielle conforme au besoin amendé des décisions et éventuels arbitrages réalisés tout au long du projet.  
Dans le cadre de la méthodologie simplifiée appliquée ici, il est prévu de réaliser 2 ou 3 itérations pour la phase de réalisation.    
Chaque itération débute par une réunion de restitution où la solution logicielle est présentée aux acteurs métiers du projet. 
Ces réunions étant l’occasion de recadrer si nécessaire le besoin (les comptes rendu avec relevés de décision seront fournis dans des documents séparés).  
La phase de validation a pour objectif la vérification de la conformité au besoin final.  
Dans le cadre du projet, cette validation est sous la responsabilité de NRCO. Il s'agira de phases de recette effectuées par les équipes projet.  
Simplicité Software sera en support lors de cette phase afin de corriger les éventuelles anomalies et/ou prendre en compte les derniers ajustements fonctionnels mineurs.  
A l’issue de cette validation, un procès verbal de recevabilité sera prononcé par NRCO et le projet passera en phase de garantie.  

Dispositif Simplicité Software   
----------------------  
Le dispositif projet Simplicité Software est le suivant :  
*	L’équipe projet : Nathalie SEITZ et Alistair WHEELER  
*	Des experts plateforme Simplicité® techniques en fonction des sujets  

Dispositif NRCO      
---------------------- 
Le dispositif projet est le suivant :  
Représentants projet pour le métier :  
	Valérie Bouclier
*	Florent Rossetti
*	Luc Dubreuil
*	Christophe Grieumard

Macro Planning        
----------------------  
Le macro planning du projet est le suivant :  
*	Cadrage le 18/01  
*	Début de la phase de réalisation le 29/11/2018    
*	1ère restitution (1ère itération)  le 14/12/2018    
*	2ème restitution (2ème itération)  le 09/01/2019

Remarque: Le délai de réalisation sera mis à jour à chaque comité projet et sera revu en fonction des contraintes opérationnelles projet (disponibilité des acteurs, arbitrages sur besoins, etc…). Il pourra donc être revu à la baisse ou à la hausse en cours de projet.  

Livrables 
----------------------  
Documents  
* Le document de suivi  
* Le dossier de paramétrage Simplicité®  
* Les manuels d’installation et d’exploitation  
* Les compte-rendus des comités projet et ateliers  
Livrables techniques  
* Prototype livré sur serveur Simplicité® pendant la phase de conception  
* Package projet avec paramétrage métier sous forme de fichier(s) XML ou ZIP et code(s) source(s)  
 
Hypothèses d'implémentation sur socle Simplicité   
----------------------  
Objets métiers  
* Configurés et paramétrés dans les standards Simplicité®  
Workflows  
* Seront configurés comme des diagrammes d’états avec habilitation des transitions 
Règles de gestion  
* Les règles de gestion métiers seront configurées comme des contraintes (standard Simplicité®) et/ou développées en Java ou Script (en utilisant les APIs Simplicité®)  

IHM / interface utilisateur  
* L’IHM standard Simplicité® sera utilisée.  


Besoin    
======

Contexte et enjeux    
--------------  
L'équipe commerciale du Groupe La Nouvelle République du Centre Ouest souhaite se doter d'une application pour la gestion et le suivi de son portefeuille client et des différentes actions commerciales réalisées.

L'objectif de cette application est de :  
*	D’avoir une application de gestion permettant de gérer les prospects / clients et actions commerciales
*	Avoir un référentiel des contacts et adresses associées aux prospects / clients
*	Créer des actions commerciales de type Visite, Appel, Rendez-vous, Ouverture de Point de Vente, Pack NR, Pack Brocante, Pack GMS, Vente Privilège et Vente Autre
*	Avoir une application permettant de saisir/consulter toutes les informations associées à un prospect / client depuis un mobile ou une tablette et un ordinateur  

	

Application COMPAS      
======  

Processus authentification/identification  
---------------------  
![Module authentification]([IMAGE:AUTHENT])

La page d’accueil présente un module d’authentification à l’application.  
L’utilisateur se connecte à l’application en saisissant son login et son mot de passe Google.
L’utilisateur a un compte sur l’application.

Profils de droits   
--------------------- 

Administrateurs NRCO :  c'est le profil qui possède tous les droits

| Groupe utilisateur        | Uses cases                                                            | 
|---------------------------|:---------------------------------------------------------------------:|
| COM_MANAGER				    | Gère les données de référence |
|					        | Gère toutes les données métier (Tous les droits sur les objets)                                                                  |
|				            | Possède les droits de toutes les transitions d’états sur les objets qui suivent un diagramme d’état    |
|			                | Gère les utilisateurs                                                                  |

Utilisateurs Commerciaux :  

| Groupe utilisateur        | Uses cases                                                            | 
|---------------------------|:---------------------------------------------------------------------:|
| COM_COMMERCIAL				    | Gère les données de référence |
|					        | Gère toutes les données métier (Tous les droits sauf suppression sur les objets)                                                                  |
|				            | Possède les droits de toutes les transitions d’états sur les objets qui suivent un diagramme d’état  (sans retour arrière)  |


Utilisateurs du groupe ADV

| Groupe utilisateur        | Uses cases                                                            | 
|---------------------------|:---------------------------------------------------------------------:|
| COM_ADV	    | A préciser |
|					        |                                                                    |
|				            |                                                              |



Page d'accueil   
---------------------  

A compléter



Modèle de données COMPAS    
---------------------  
![]([MODEL:COMPASMODEL])


## Prospects / Clients :

[OBJECTDOC:ComClient]

### Règles de gestion Prospects / Clients :



## Contacts :

[OBJECTDOC:ComContact]

### Règles de gestion Contacts :



##Adresses :
[OBJECTDOC:ComAdresse]

### Règles de gestion Adresses :
 


## Pièces Jointes :
[OBJECTDOC:ComPieceJointe]

### Règles de gestion Pièces Jointes :


## Actions Commerciales :
[OBJECTDOC:ComActionCommerciale]

### Règles de gestion Actions Commerciales :
Un utilisateur ne peut voir que les Actions Commerciales de sa zone. 
Il ne peut modifier que celles dont il est responsable.

#### Règles de gestion SAP :
Une Action Commerciale remontée dans SAP selon le type, contenir un certain nombre d'informations. Tant que ces champs ne sont pas valorisés la remontée dans SAP est impossible. Lors de la remontée dans SAP, la valorisation du l'ID SAP est faite de manière asynchrone (PUT depuis le bus Redway).

Certaines informations sont obligatoires pour la remontée dans SAP : 
	- Type de paiement, fréquence de facturation
	- Au moins une édition avec tous les champs renseignés


##Jour de la Semaine :

[OBJECTDOC:ComJourSemaine]

###Règles de gestion Jours de la Semaine :



## Editions :

[OBJECTDOC:ComEdition]

### Règles de gestion Editions :



## Vente Privilège :

[OBJECTDOC:ComVentePriv]

### Règles de gestion Vente Privilège :



## PNR :

[OBJECTDOC:ComPNR]

### Règles de gestion PNR :



## Dotation :

[OBJECTDOC:ComDotation]

### Règles de gestion Dotation :



## Commande de Journaux :

[OBJECTDOC:ComCommandeJournaux]

### Règles de gestion Commande de Journaux :