-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 22 mai 2021 à 21:41
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
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `idClient` int(100) NOT NULL,
  `prenom` text NOT NULL,
  `nom` text NOT NULL,
  `dateNaiss` text NOT NULL,
  `adresse` text NOT NULL,
  `mail` text NOT NULL,
  `telephone` text NOT NULL,
  `taille` double NOT NULL,
  `poids` double NOT NULL,
  `typeSportif` text NOT NULL,
  `objectif` text NOT NULL,
  `lieuPratique` text NOT NULL,
  `programme` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`idClient`, `prenom`, `nom`, `dateNaiss`, `adresse`, `mail`, `telephone`, `taille`, `poids`, `typeSportif`, `objectif`, `lieuPratique`, `programme`) VALUES
(23, 'Lionel', 'Messi', '28/04/2021', 'Barcelonne', 'messi@gmail.com', '7869968433', 190, 90, 'Sportif haut niveau', 'Affiner les muscles ', 'En salle', 'Prise de masse (confirmé)'),
(24, 'Neymar', 'Junior', '04/05/2021', 'Paris', 'neymar@gmail.com', '7986958411', 186, 86, 'Sportif haut niveau', 'Perte de poids', 'Distance ', 'Renforcement musculaire'),
(25, 'Roger', 'federer', '27/05/2021', 'suiss', 'test@test.com', '7894998969', 190, 56, 'Sportif débutant', 'Perdre du ventre', 'Distance ', 'Perte de poids'),
(28, 'Akim', 'Saadi', '06/05/2021', 'test', 'tet2@test2', '1234567896', 165, 80, 'Sportif débutant ', 'Perte de poids', 'Distance ', 'Construction musculaire'),
(30, 'Karime', 'Benzema', '04/05/1991', 'Madrid', 'karim@gmail.fr', '8719695847', 198, 90, 'Sportif occasionnel', 'Affiner les muscles ', 'En salle', 'Prise de masse (intermédiaire)'),
(32, 'Georges', 'Bardaghji', '27/06/1997', 'Marseille Rue Goerges', 'goergeBardaghji@gmail.com', '9854478596', 190, 85, 'Sportif haut niveau', 'Affiner les muscles ', 'En salle', 'Prise de masse (confirmé)'),
(33, 'Nadal', 'Rafael', '28/04/2021', 'Madrid Spain', 'nadal@hotmail.com', '5987458901', 190, 89, 'Sportif débutant', 'Affiner les muscles ', 'Distance ', 'Prise de masse (intermédiaire)');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `idClient` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
