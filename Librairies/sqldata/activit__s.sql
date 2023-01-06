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
-- Structure de la table `activités`
--

CREATE TABLE `activités` (
  `idActivité` int(11) NOT NULL,
  `nomActivité` text NOT NULL,
  `descActivité` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `activités`
--

INSERT INTO `activités` (`idActivité`, `nomActivité`, `descActivité`) VALUES
(1, 'Développé couché', 'Le développé couché est un exercice poly-articulaire de force et de musculation qui consiste à soulever et abaisser une barre d\'haltères, développant principalement les pectoraux et les triceps, mais qui sollicite également d\'autres muscles comme le grand dorsal, le grand rond, les trapèzes, les deltoïdes antérieurs et les biceps'),
(2, 'Développé incliné avec haltères', 'Le développé incliné avec haltères est une variante de la version classique en position couchée. Il s’effectue sur un banc incliné, ce qui permet de mettre l’accent sur la partie supérieure des pectoraux (faisceau claviculaire).\n\n'),
(3, 'Écarté à la poulie vis à vis', 'Les écartés avec haltères permettent d’isoler les muscles pectoraux. Ce mouvement avec haltères vous permet de travailler avec une amplitude maximum qui vous permet de bien étirer les muscles de la poitrine.'),
(4, 'Pull Over', 'Le pull-over en musculation est un exercice pour le haut du corps qui renforce de nombreux muscles dont, principalement, les pectoraux, le grand dorsal et le dentelé antérieur.'),
(5, 'Barre au front', 'La barre au front est un mouvement de musculation qui cible les triceps et a recours dans une moindre mesure aux avant-bras.'),
(6, 'Extension poulie haute en pronation', 'L’extension poulie haute en pronation est un exercice de base pour travailler l’ensemble des triceps. Il met l’accent sur les vastes interne et externe.'),
(7, 'Kickback', 'Le kickback ou extension arrière avec haltère est un exercice d’isolation pour les triceps. A première vue plutôt simple à réaliser, ce mouvement nécessite toutefois un minimum de technique pour muscler ses bras efficacement.'),
(8, 'Crunch à la poulie haute', 'Le crunch à la poulie haute est un bon exercice pour muscler les abdominaux et tonifier le ventre. On peut le réaliser debout à la poulie haute avec une barre de tirage ou bien à la corde, il peut s’effectuer également à genoux.'),
(9, 'Relevé de jambe au sol', 'Les levés de jambes au sol ciblent particulièrement la partie inférieure des abdominaux.'),
(10, 'Dips', 'Les dips, sont un exercice polyarticulaire de musculation destiné à développer principalement la force et le volume des triceps, des pectoraux et des épaules.'),
(11, 'Press', 'Le press est un appareil de musculation qui sert à travailler le bas du corps. C’est une machine de musculation plutôt complète pour les membres inférieurs car vous pourrez travailler les quadriceps, les ischios, les fessiers et les mollets'),
(12, 'Bucheron', 'Le tirage bûcheron est un exercice de musculation centré sur le haut du dos. Plusieurs exercices vont permettre de rendre votre dos plus épais ou plus large. Celui-ci développera votre épaisseur'),
(13, 'Cardio', 'Le cardio est un entraînement qui joue sur le contrôle de la fréquence cardiaque lors d’un effort à intensité progressive. Il permet d’améliorer la performance du cœur et des poumons pour une meilleure distribution de l’oxygène.'),
(14, 'Squat', 'Le squat est un mouvement d\'accroupi qui constitue un exercice poly-articulaire de force et de musculation ciblant les muscles de la cuisse et les fessiers.'),
(15, 'Gainage', 'Le gainage est un entraînement musculaire très efficace et accessible à tous pour renforcer les abdominaux.'),
(16, 'Vélo', 'Le vélo d’appartement est un appareil de fitness apprécié pour ses nombreux bienfaits sur l’organisme, sur la forme physique et l’état de bien-être en général. Pratiqué régulièrement, il aide à la perte de poids et au renforcement du système cardiovasculaire. Il participe également au gain musculaire et à l’amélioration de l’endurance.'),
(17, 'Marche rapide', 'La marche sportive, aussi nommée marche rapide ou marche active, est un sport qui se pratique à un rythme plus soutenu que la marche pratiquée au quotidien. Il fait donc travailler le souffle, l’endurance et presque tous les muscles du corps. A l’inverse de la marche quotidienne, qui se pratique souvent de façon inconsciente, la marche sportive se pratique de façon volontaire et à un rythme accéléré.'),
(18, 'Butterfly', 'Butterfly est un exercice de musculation des pectoraux qui cible le grand pectoral dans son ensemble. Néanmoins, la sensation sur la partie sternale est plus prononcée lors du rapprochement des coudes en fin de mouvement.'),
(19, 'Leg Extensions', 'Leg Extensions  ou extension de jambes est un exercice d’isolation qui permet de travailler uniquement le quadriceps.'),
(20, 'Developpé haltere', 'Le developpé halteres sollicite les muscles des épaules, et indirectement les triceps – à l\'arrière des bras – et le haut des pectoraux. C’est un exercice de base, idéal pour prendre de la masse, de l\'épaisseur et construire des épaules massives.\n\n'),
(21, 'Le rameur', 'Le rameur fait travailler quasiment tous les muscles du corps et brûle efficacement les graisses pendant et surtout après l\'effort. '),
(22, 'Traction', 'La traction est un exercice physique consistant à hisser ses épaules au niveau d\'une barre en la tenant par les mains. Les tractions ont pour objectif principal le développement des muscles du dos et des bras.'),
(23, 'Shrugs barre', 'Shrugs barre est un exercice de musculation sollicite et développe les trapèzes. Ces grands muscles en éventail sont situés sur le haut du dos et recouvrent toute la partie supérieure du cou et du dos.'),
(24, 'Squat jump', 'Le squat jump, comme son nom l’indique, est un exercice qui combine un squat et un saut vertical. Il cible particulièrement les muscles de vos jambes et vous permet de gagner en tonus musculaire.'),
(25, 'Pompe hindou', 'Les pompes hindoux est un exercice qui sollicite les deltoïdes, les pectoraux et les triceps.'),
(26, 'Feinte', 'Les fentes sont l\'exercice idéal pour travailler ses fessiers et dessiner ses jambes. Elles sollicitent l\'ensemble de la cuisse et l\'arrière de la jambe.'),
(27, 'Relevé de buste', 'Le relevé de buste a pour but de faire travailler les abdominaux.');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activités`
--
ALTER TABLE `activités`
  ADD PRIMARY KEY (`idActivité`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activités`
--
ALTER TABLE `activités`
  MODIFY `idActivité` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
