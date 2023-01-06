-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 22 mai 2021 à 21:03
-- Version du serveur :  10.4.18-MariaDB
-- Version de PHP : 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `coach`
--

-- --------------------------------------------------------

--
-- Structure de la table `activitésprogramme`
--

CREATE TABLE `activitésprogramme` (
  `idActivitésprogramme` int(11) NOT NULL,
  `idProgramme` int(11) NOT NULL,
  `idActivité` int(11) NOT NULL,
  `quantitéActivité` int(11) NOT NULL,
  `répétitionActivité` int(11) NOT NULL,
  `recupérationActivité` int(11) NOT NULL DEFAULT 90
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `activitésprogramme`
--

INSERT INTO `activitésprogramme` (`idActivitésprogramme`, `idProgramme`, `idActivité`, `quantitéActivité`, `répétitionActivité`, `recupérationActivité`) VALUES
(1, 1, 1, 5, 10, 120),
(2, 1, 2, 3, 8, 90),
(3, 1, 3, 4, 8, 90),
(4, 1, 4, 3, 10, 60),
(5, 1, 5, 4, 11, 120),
(6, 1, 6, 3, 8, 60),
(7, 1, 7, 3, 10, 60),
(8, 1, 8, 4, 15, 45),
(9, 1, 9, 4, 15, 30),
(10, 2, 1, 5, 10, 120),
(11, 2, 2, 3, 8, 60),
(12, 2, 4, 4, 8, 90),
(13, 2, 3, 3, 10, 90),
(14, 2, 5, 4, 11, 120),
(15, 2, 6, 3, 8, 60),
(16, 2, 7, 3, 12, 60),
(17, 2, 8, 4, 15, 45),
(18, 2, 9, 4, 15, 30),
(19, 3, 1, 5, 10, 120),
(20, 3, 2, 3, 8, 60),
(21, 3, 3, 5, 3, 60),
(22, 3, 4, 3, 10, 60),
(23, 3, 5, 4, 11, 120),
(24, 3, 6, 3, 8, 60),
(25, 3, 7, 5, 10, 60),
(26, 3, 8, 4, 15, 60),
(27, 4, 1, 4, 10, 90),
(28, 4, 2, 4, 10, 90),
(29, 4, 10, 4, 12, 90),
(30, 4, 11, 3, 15, 90),
(31, 4, 12, 3, 12, 90),
(32, 4, 13, 10, 10, 10),
(33, 5, 13, 1, 1, 20),
(34, 5, 1, 4, 12, 60),
(35, 5, 14, 4, 12, 60),
(36, 5, 15, 4, 1, 30),
(37, 5, 16, 1, 1, 20),
(38, 5, 6, 5, 12, 60),
(39, 6, 17, 1, 1, 20),
(40, 6, 1, 4, 10, 60),
(41, 6, 18, 6, 12, 60),
(42, 6, 14, 4, 12, 60),
(43, 6, 11, 3, 10, 60),
(44, 6, 19, 3, 12, 60),
(45, 6, 15, 5, 1, 30),
(46, 6, 20, 3, 12, 60),
(47, 6, 6, 3, 12, 60),
(48, 7, 17, 1, 1, 20),
(49, 7, 1, 3, 10, 60),
(50, 7, 18, 4, 15, 60),
(51, 7, 14, 5, 12, 60),
(52, 7, 19, 4, 12, 60),
(53, 7, 15, 6, 1, 30),
(54, 7, 16, 1, 1, 20),
(55, 7, 10, 3, 20, 60),
(56, 8, 4, 4, 12, 70),
(57, 8, 7, 6, 12, 60),
(58, 8, 6, 4, 12, 60),
(59, 8, 16, 1, 1, 45),
(60, 8, 21, 1, 1, 5),
(61, 8, 1, 4, 12, 60),
(62, 8, 23, 4, 10, 60),
(63, 8, 11, 4, 10, 60),
(64, 10, 24, 1, 10, 60),
(65, 10, 15, 1, 1, 60),
(66, 10, 25, 1, 10, 60),
(67, 10, 26, 1, 1, 60),
(68, 10, 27, 1, 20, 60);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activitésprogramme`
--
ALTER TABLE `activitésprogramme`
  ADD PRIMARY KEY (`idActivitésprogramme`),
  ADD KEY `Foreign Key idActivité` (`idActivité`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activitésprogramme`
--
ALTER TABLE `activitésprogramme`
  MODIFY `idActivitésprogramme` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activitésprogramme`
--
ALTER TABLE `activitésprogramme`
  ADD CONSTRAINT `Foreign Key idActivité` FOREIGN KEY (`idActivité`) REFERENCES `activités` (`idActivité`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
