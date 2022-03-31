# Description
L'objectif de ce TP est de r�aliser la partie backend du projet Kanban. Cette partie backend doit pouvoir g�rer un projet Kanban, contenant diff�rentes sections, elle-m�me pouvant contenir diff�rentes cartes.
J'ai d�cid� de r�aliser ce projet via Spring Boot pour d�velopper l'API REST permettant de proposer des op�rations CRUD sur les diff�rents types d'objets.


## Description des routes
### KanbanBoard 
Les routes pour acc�der aux KanbanBoard sont les suivantes :
- (GET) - **/kanban/api/boards** : Permet d'acc�der � tous les KanbanBoard
- **(DELETE)** /kanban/api/boards : Permet de supprimer tous les KanbanBoard
- **(GET)** /kanban/api/boards/{id} : Permet d'acc�der � un KanbanBoard en particulier
- **(POST)** /kanban/api/boards/{id} : Permet de cr�er un KanbanBoard
- **(PUT)** /kanban/api/boards/{id} : Permet de mettre � jour un KanbanBoard
- **(DELETE)** /kanban/api/boards/{id} : Permet de supprimer un KanbanBoard en particulier

### KanbanSection