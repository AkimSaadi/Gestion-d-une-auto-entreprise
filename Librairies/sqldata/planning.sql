-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 22 mai 2021 à 21:42
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
-- Structure de la table `planning`
--

CREATE TABLE `planning` (
  `idPlanning` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `idProgramme` int(11) NOT NULL,
  `idLieu` int(11) NOT NULL,
  `dateDebut` datetime NOT NULL,
  `dateFin` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `planning`
--

INSERT INTO `planning` (`idPlanning`, `idClient`, `idProgramme`, `idLieu`, `dateDebut`, `dateFin`) VALUES
(43, 25, 3, 1, '2021-05-23 06:00:00', '2021-05-23 08:00:00'),
(44, 24, 3, 1, '2021-05-23 08:00:00', '2021-05-23 12:00:00'),
(45, 30, 3, 1, '2021-05-25 08:00:00', '2021-05-25 11:00:00'),
(46, 28, 5, 2, '2021-05-22 08:00:00', '2021-05-22 11:00:00'),
(47, 23, 6, 1, '2021-05-22 14:00:00', '2021-05-22 17:00:00'),
(48, 25, 3, 1, '2021-05-22 18:00:00', '2021-05-22 22:00:00'),
(49, 25, 3, 2, '2021-05-23 13:00:00', '2021-05-23 15:00:00'),
(50, 32, 8, 1, '2021-05-26 07:00:00', '2021-05-26 11:00:00'),
(51, 28, 8, 2, '2021-05-28 10:00:00', '2021-05-28 13:00:00'),
(52, 28, 4, 2, '2021-05-28 13:00:00', '2021-05-28 17:00:00'),
(53, 30, 7, 2, '2021-06-02 13:00:00', '2021-06-02 17:00:00'),
(54, 30, 7, 2, '2021-06-01 10:00:00', '2021-06-01 12:00:00'),
(55, 30, 7, 2, '2021-07-07 08:00:00', '2021-07-07 09:00:00'),
(56, 30, 7, 2, '2021-08-08 11:00:00', '2021-08-08 12:00:00'),
(57, 33, 7, 2, '2021-06-04 11:00:00', '2021-06-04 15:00:00');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `planning`
--
ALTER TABLE `planning`
  ADD PRIMARY KEY (`idPlanning`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `planning`
--
ALTER TABLE `planning`
  MODIFY `idPlanning` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
