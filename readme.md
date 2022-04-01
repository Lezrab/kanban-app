# Description
L'objectif de ce TP est de r�aliser la partie backend du projet Kanban. Cette partie backend doit pouvoir g�rer un projet Kanban, contenant diff�rentes sections, elle-m�me pouvant contenir diff�rentes cartes.
J'ai d�cid� de r�aliser ce projet via Spring Boot pour d�velopper l'API REST permettant de proposer des op�rations CRUD sur les diff�rents types d'objets.

3 entit�s principales sont exploit�es dans ce projet :

![Entities schema](https://i.ibb.co/4Rv0phK/schema-db.png)

- Un KanbanBoard poss�de un label ainsi qu'une liste de KanbanSection.
- Une KanbanSection poss�de un label unique, une couleur hexad�cimale ainsi qu'une position unique. La position permet de choisir � quel endroit la section peut-�tre affich�e, dans une logique d'ordre (par exemple : A faire -> En cours -> Termin�). Une KanbanSection poss�de une liste de KanbanCard.
- Une KanbanCard poss�de un label, une dur�e estim�e, une date de cr�ation, une description, une URL associ�e, un utilisateur associ�, ainsi qu'une liste de tags associ�s. Une fiche peut-�tre associ�e � un seul utilisateur.


## Description des routes
### KanbanBoard 
Les routes pour acc�der aux KanbanBoard sont les suivantes :
- **(GET)** - /kanban/api/boards : Permet d'acc�der � tous les KanbanBoard
- **(DELETE)** /kanban/api/boards : Permet de supprimer tous les KanbanBoard
- **(GET)** /kanban/api/boards/{id} : Permet d'acc�der � un KanbanBoard en particulier
- **(POST)** /kanban/api/boards/{id} : Permet de cr�er un KanbanBoard
- **(PUT)** /kanban/api/boards/{id} : Permet de mettre � jour un KanbanBoard
- **(DELETE)** /kanban/api/boards/{id} : Permet de supprimer un KanbanBoard en particulier

### KanbanSection
Les routes pour acc�der aux KanbanSection sont les suivantes :
- **(GET)** - /kanban/api/boards/{id}/sections : Permet de r�cup�rer toutes les KanbanSection d'une KanbanBoard
- **(DELETE)** - /kanban/api/boards/{id}/sections : Permet de supprimer toutes les KanbanSection d'une KanbanBoard


## Gestion des erreurs
Lors de certains cas, une erreur customis�e doit �tre retourn�e � l'utilisateur afin de lui indiquer un cas d'erreur sp�cifique. Par exemple, pour une ressource d�j� existante lors de la cr�ation, ou d'une ressource manquante lors de la r�cup�ration, mise � jour ou suppression.

Pour cela, j'ai cr�� des exceptions personnalis�es : DatabaseFetchException, ResourceAlreadyExistsException et ResourceNotFoundException.
Ces exceptions sont soulev�es lors des cas indiqu�s pr�c�demment.

Pour permettre la gestion de ces exceptions, une classe annot�e avec @ControllerAdvice existe et permet de g�rer les diff�rents cas d'erreurs retourn�es selon l'exception catch�e.
## Utilisation des entit�s/DTOs

Pour permettre � l'utilisateur d'avoir une interface plus "user-friendly", les entit�s en base de donn�es sont converties en DTO. La classe ModelMapper se charge de faire la conversion. 

Les mappings d�clar�s dans la classe KanbanConfiguration permettent d'indiquer au ModelMapper de skip les id des identit�s lors du passe de l'entit� au DTO : en effet, il est inutile de demander � l'utilisateur d'indiquer un id lors de la cr�atio d'un KanbanBoard, par exemple.

Les DTOs sont g�n�r�s automatiquement gr�ce au Swagger � la compilation, via l'utilisation d'un plugin dans le pom.xml :
![Plugin de g�n�ration des DTOs](https://i.ibb.co/bJQDLg4/dto.png)

# Installation
## Pr�requis avant de d�marrer l'application
- Disposer d'une installation [WAMP/XAMPP](https://www.wampserver.com/) sur son PC Personnel. Celle-ci sera utilis�e pour h�berger la base de donn�es requ�t�e.
- Cloner le [projet GIT](https://gitlab.istic.univ-rennes1.fr/aarzel/kanban-app) sur son poste personnel.
- S'assurer d'avoir de quoi utiliser Maven (installation sur son poste personnel, ou bien utilisation du Maven Wrapper d'Intellij par exemple). Importer le projet en tant que projet Maven.
- Le projet est d�velopp� en [Java 11.0.13](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html)
- Cr�er la base de donn�es "kanban_app" sur l'interface phpMyAdmin. Le script de cr�ation de la base de donn�es se trouve dans le dossier **/src/main/ressources/database**. Sinon, dans le fichier de configuration de l'application **/src/main/ressources/config/application.yml**, passer la valeur de **spring.jpa.hibernate.ddl-auto** � **"create"**. Cette op�ration cr�era la base de donn�es � chaque lancement de l'application, alors s'assurer de remettre sa valeur � **"none"** apr�s la premi�re cr�ation.
- Compiler l'application : `mvn clean install` afin de g�n�rer les DTOs n�cessaires au fonctionnement de l'application.
- D�marrer l'application ?

## Ressources
Dans le dossier /src/main/ressources se trouvent un ensemble de ressources utiles pour pouvoir comprendre et tester le projet :
- assets : l'ensemble des images utilis�es dans ce readme
- config : le fichier de configuration de l'application. Ici, vous pouvez red�finir l'adresse de la base de donn�es ainsi que l'utilisateur et le mot de passe de connexion.
- database : le script de cr�ation de la base de donn�es "kanban_app". Il pourra �tre utile de l'utiliser si la cr�ation de la base de donn�es � la main est requise.
- postman : une collection de tests postman param�tr�e pour pouvoir �tre utilis�e d�s son importation.
- swaggers : le fichier [swagger de l'application](https://editor.swagger.io/).