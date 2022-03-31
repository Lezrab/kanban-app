# Description
L'objectif de ce TP est de réaliser la partie backend du projet Kanban. Cette partie backend doit pouvoir gérer un projet Kanban, contenant différentes sections, elle-même pouvant contenir différentes cartes.
J'ai décidé de réaliser ce projet via Spring Boot pour développer l'API REST permettant de proposer des opérations CRUD sur les différents types d'objets.


## Description des routes
### KanbanBoard 
Les routes pour accéder aux KanbanBoard sont les suivantes :
- (GET) - **/kanban/api/boards** : Permet d'accéder à tous les KanbanBoard
- **(DELETE)** /kanban/api/boards : Permet de supprimer tous les KanbanBoard
- **(GET)** /kanban/api/boards/{id} : Permet d'accéder à un KanbanBoard en particulier
- **(POST)** /kanban/api/boards/{id} : Permet de créer un KanbanBoard
- **(PUT)** /kanban/api/boards/{id} : Permet de mettre à jour un KanbanBoard
- **(DELETE)** /kanban/api/boards/{id} : Permet de supprimer un KanbanBoard en particulier

### KanbanSection