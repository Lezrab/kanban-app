-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 31 mars 2022 à 09:11
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `kanban_app`
--
CREATE DATABASE IF NOT EXISTS `kanban_app` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `kanban_app`;

-- --------------------------------------------------------

--
-- Structure de la table `t_associated_tags`
--

DROP TABLE IF EXISTS `t_associated_tags`;
CREATE TABLE IF NOT EXISTS `t_associated_tags` (
  `at_card_id` bigint NOT NULL,
  `at_label` varchar(255) DEFAULT NULL,
  KEY `FKjpijeyelthknmkbusrtpwfx24` (`at_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `t_kanban_board`
--

DROP TABLE IF EXISTS `t_kanban_board`;
CREATE TABLE IF NOT EXISTS `t_kanban_board` (
  `kb_id` bigint NOT NULL AUTO_INCREMENT,
  `kb_label` varchar(255) NOT NULL,
  PRIMARY KEY (`kb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `t_kanban_card`
--

DROP TABLE IF EXISTS `t_kanban_card`;
CREATE TABLE IF NOT EXISTS `t_kanban_card` (
  `kc_id` bigint NOT NULL AUTO_INCREMENT,
  `kc_affected_user` varchar(255) DEFAULT NULL,
  `kc_associated_url` varchar(255) DEFAULT NULL,
  `kc_creation_date` date DEFAULT NULL,
  `kc_description` varchar(255) DEFAULT NULL,
  `kc_estimated_time` int DEFAULT NULL,
  `kc_label` varchar(255) NOT NULL,
  `kc_section_id` bigint DEFAULT NULL,
  PRIMARY KEY (`kc_id`),
  KEY `FKl6r6uykhgyqtrpco955v100vn` (`kc_section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `t_kanban_section`
--

DROP TABLE IF EXISTS `t_kanban_section`;
CREATE TABLE IF NOT EXISTS `t_kanban_section` (
  `ks_id` bigint NOT NULL AUTO_INCREMENT,
  `ks_color` varchar(255) NOT NULL,
  `ks_label` varchar(255) NOT NULL,
  `ks_position` bigint NOT NULL,
  `ks_board_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ks_id`),
  KEY `FKqdxagecbgw4ll54fbbnsuobd4` (`ks_board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `t_associated_tags`
--
ALTER TABLE `t_associated_tags`
  ADD CONSTRAINT `FKjpijeyelthknmkbusrtpwfx24` FOREIGN KEY (`at_card_id`) REFERENCES `t_kanban_card` (`kc_id`);

--
-- Contraintes pour la table `t_kanban_card`
--
ALTER TABLE `t_kanban_card`
  ADD CONSTRAINT `FKl6r6uykhgyqtrpco955v100vn` FOREIGN KEY (`kc_section_id`) REFERENCES `t_kanban_section` (`ks_id`);

--
-- Contraintes pour la table `t_kanban_section`
--
ALTER TABLE `t_kanban_section`
  ADD CONSTRAINT `FKqdxagecbgw4ll54fbbnsuobd4` FOREIGN KEY (`ks_board_id`) REFERENCES `t_kanban_board` (`kb_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
