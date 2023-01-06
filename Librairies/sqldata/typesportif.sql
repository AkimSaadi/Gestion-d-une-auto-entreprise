-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2021 at 09:06 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coach`
--

-- --------------------------------------------------------

--
-- Table structure for table `typesportif`
--

CREATE TABLE `typesportif` (
  `idTypeSportif` int(100) NOT NULL,
  `nomType` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `typesportif`
--

INSERT INTO `typesportif` (`idTypeSportif`, `nomType`) VALUES
(1, 'Sportif haut niveau'),
(3, 'Sportif occasionnel'),
(4, 'Sportif débutant');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `typesportif`
--
ALTER TABLE `typesportif`
  ADD PRIMARY KEY (`idTypeSportif`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `typesportif`
--
ALTER TABLE `typesportif`
  MODIFY `idTypeSportif` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
