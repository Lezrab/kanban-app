# Description
L'objectif de ce TP est de réaliser la partie backend du projet Kanban. Cette partie backend doit pouvoir gérer un projet Kanban, contenant différentes sections, elle-même pouvant contenir différentes cartes.
J'ai décidé de réaliser ce projet via Spring Boot pour développer l'API REST permettant de proposer des opérations CRUD sur les différents types d'objets.

3 entités principales sont exploitées dans ce projet :

![Entities schema](https://i.ibb.co/4Rv0phK/schema-db.png)

- Un KanbanBoard possède un label ainsi qu'une liste de KanbanSection.
- Une KanbanSection possède un label unique, une couleur hexadécimale ainsi qu'une position unique. La position permet de choisir à quel endroit la section peut-être affichée, dans une logique d'ordre (par exemple : A faire -> En cours -> Terminé). Une KanbanSection possède une liste de KanbanCard.
- Une KanbanCard possède un label, une durée estimée, une date de création, une description, une URL associée, un utilisateur associé, ainsi qu'une liste de tags associés. Une fiche peut-être associée à un seul utilisateur.


## Description des routes
### KanbanBoard 
Les routes pour accéder aux KanbanBoard sont les suivantes :
- **(GET)** - /kanban/api/boards : Permet d'accéder à tous les KanbanBoard
- **(DELETE)** /kanban/api/boards : Permet de supprimer tous les KanbanBoard
- **(GET)** /kanban/api/boards/{boardId} : Permet d'accéder à un KanbanBoard en particulier
- **(POST)** /kanban/api/boards/{boardId} : Permet de créer un KanbanBoard
- **(PUT)** /kanban/api/boards/{boardId} : Permet de mettre à jour un KanbanBoard
- **(DELETE)** /kanban/api/boards/{boardId} : Permet de supprimer un KanbanBoard en particulier

### KanbanSection
Les routes pour accéder aux KanbanSection sont les suivantes :
- **(GET)** - /kanban/api/boards/{boardId}/sections : Permet de récupérer toutes les KanbanSection d'une KanbanBoard
- **(DELETE)** - /kanban/api/boards/{boardId}/sections : Permet de supprimer toutes les KanbanSection d'une KanbanBoard
- **(GET)** - /kanban/api/boards/{boardId}/sections/{sectionId} : Permet d'accéder à une KanbanSection en particulier dans un KanbanBoard en particulier
- **(POST)** - /kanban/api/boards/{boardId}/sections/{sectionId} : Permet de créer une KanbanSection dans un KanbanBoard en particulier
- **(PUT)** - /kanban/api/boards/{boardId}/sections/{sectionId} : Permet de mettre à jour une KanbanSection dans un KanbanBoard en particulier
- **(DELETE)** - /kanban/api/boards/{boardId}/sections/{sectionId} : Permet de supprimer une KanbanSection dans un KanbanBoard en particulier

### KanbanCard
Les routes pour accéder aux KanbanCard sont les suivantes :
- **(GET)** - /kanban/api/boards/{boardId}/sections/{sectionId}/cards : Permet de récupérer toutes les KanbanCard d'une KanbanSection d'un KanbanBoard
- **(DELETE)** - /kanban/api/boards/{boardId}/sections/{sectionId}/cards : Permet de supprimer toutes les KanbanCard d'une KanbanSection d'un KanbanBoard
- **(GET)** - /kanban/api/boards/{boardId}/sections/{sectionId}/{sectionId}/cards/{cardId} : Permet d'accéder à une KanbanCard en particulier dans une KanbanSection d'un KanbanBoard
- **(POST)** - /kanban/api/boards/{boardId}/sections/{sectionId}/{sectionId}/cards/{cardId} : Permet de créer une KanbanCard dans une KanbanSection d'un KanbanBoard
- **(PUT)** - /kanban/api/boards/{boardId}/sections/{sectionId}/{sectionId}/cards/{cardId} : Permet de mettre à jour une KanbanCard dans une KanbanSection d'un KanbanBoard
- **(DELETE)** - /kanban/api/boards/{boardId}/sections/{sectionId}/{sectionId}/cards/{cardId} : Permet de supprimer une KanbanCard dans une KanbanSection d'un KanbanBoard


## Gestion des erreurs
Lors de certains cas, une erreur customisée doit être retournée à l'utilisateur afin de lui indiquer un cas d'erreur spécifique. Par exemple, pour une ressource déjà existante lors de la création, ou d'une ressource manquante lors de la récupération, mise à jour ou suppression.

Pour cela, j'ai créé des exceptions personnalisées : DatabaseFetchException, ResourceAlreadyExistsException et ResourceNotFoundException.
Ces exceptions sont soulevées lors des cas indiqués précédemment.

Pour permettre la gestion de ces exceptions, une classe annotée avec @ControllerAdvice existe et permet de gérer les différents cas d'erreurs retournées selon l'exception catchée.
## Utilisation des entités/DTOs

Pour permettre à l'utilisateur d'avoir une interface plus "user-friendly", les entités en base de données sont converties en DTO. La classe ModelMapper se charge de faire la conversion. 

Les mappings déclarés dans la classe KanbanConfiguration permettent d'indiquer au ModelMapper de skip les id des identités lors du passage de l'entité au DTO : en effet, il est inutile de demander à l'utilisateur d'indiquer un id lors de la création d'un KanbanBoard, par exemple.

Les DTOs sont générés automatiquement grâce au Swagger à la compilation, via l'utilisation d'un plugin dans le pom.xml :
![Plugin de génération des DTOs](https://i.ibb.co/bJQDLg4/dto.png)

# Installation
## Prérequis avant de démarrer l'application
- Disposer d'une installation [WAMP/XAMPP](https://www.wampserver.com/) sur son PC Personnel. Celle-ci sera utilisée pour héberger la base de données requêtée.
- Cloner le [projet GIT](https://gitlab.istic.univ-rennes1.fr/aarzel/kanban-app) sur son poste personnel.
- S'assurer d'avoir de quoi utiliser Maven (installation sur son poste personnel, ou bien utilisation du Maven Wrapper d'Intellij par exemple). Importer le projet en tant que projet Maven.
- Le projet est développé en [Java 11.0.13](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html)
- Créer la base de données "kanban_app" sur l'interface phpMyAdmin. Le script de création de la base de données se trouve dans le dossier **/src/main/ressources/database**. Sinon, dans le fichier de configuration de l'application **/src/main/ressources/config/application.yml**, passer la valeur de **spring.jpa.hibernate.ddl-auto** à **"create"**. Cette opération créera la base de données à chaque lancement de l'application, alors s'assurer de remettre sa valeur à **"none"** après la première création.
- Compiler l'application : `mvn clean install` afin de générer les DTOs nécessaires au fonctionnement de l'application.
- Démarrer l'application :)

## Ressources
Dans le dossier /src/main/ressources se trouvent un ensemble de ressources utiles pour pouvoir comprendre et tester le projet :
- assets : l'ensemble des images utilisées dans ce readme
- config : le fichier de configuration de l'application. Ici, vous pouvez redéfinir l'adresse de la base de données ainsi que l'utilisateur et le mot de passe de connexion.
- database : le script de création de la base de données "kanban_app". Il pourra être utile de l'utiliser si la création de la base de données à la main est requise.
- postman : une collection de tests postman paramétrée pour pouvoir être utilisée dès son importation.
- swaggers : le fichier [swagger de l'application](https://editor.swagger.io/).
