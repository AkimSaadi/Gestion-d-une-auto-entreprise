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
-- Structure de la table `programme`
--

CREATE TABLE `programme` (
  `idProgramme` int(100) NOT NULL,
  `nomProgramme` text NOT NULL,
  `descProgramme` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `programme`
--

INSERT INTO `programme` (`idProgramme`, `nomProgramme`, `descProgramme`) VALUES
(1, 'Prise de masse (débutant)', 'Le programme a pour objectif principal de vous faire gagner du muscle et d’améliorer votre niveau de force.'),
(2, 'Prise de masse (intermédiaire)', 'Le programme a pour objectif principal de vous faire gagner du muscle et d’améliorer votre niveau de force. Ce programme s\'adresse au personne ayant entre 6 et 18 mois de musculation.'),
(3, 'Prise de masse (confirmé)', 'Le programme a pour objectif principal de vous faire gagner du muscle et d’améliorer votre niveau de force. Ce programme s\'adresse au personne ayant plus de 18 mois de musculation.'),
(4, 'Construction musculaire', 'L\'objectif de ce programme est de développer ses muscles sans prendre de graisse'),
(5, 'Sèche (débutant)', 'La sèche est un régime hypocalorique qui permet d\'alléger le plus de matière grasse possible pour mieux préserver le maximum de masse musculaire. Ce programme permet d\'optimiser sa sèche.'),
(6, 'Sèche (intermédiaire)', 'La sèche est un régime hypocalorique qui permet d\'alléger le plus de matière grasse possible pour mieux préserver le maximum de masse musculaire. Ce programme permet d\'optimiser sa sèche.'),
(7, 'Sèche (confirmé)', 'La sèche est un régime hypocalorique qui permet d\'alléger le plus de matière grasse possible pour mieux préserver le maximum de masse musculaire. Ce programme permet d\'optimiser sa sèche.'),
(8, 'Perte de poids', 'Le programme a pour objectif principal de vous faire perdre du poids et réduire votre masse graisseuse.'),
(10, 'Renforcement musculaire', 'Le renforcement musculaire a pour objectif de prévenir les blessures et est plus performant.');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `programme`
--
ALTER TABLE `programme`
  ADD PRIMARY KEY (`idProgramme`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `programme`
--
ALTER TABLE `programme`
  MODIFY `idProgramme` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
