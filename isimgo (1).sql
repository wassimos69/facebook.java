-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : dim. 24 nov. 2024 à 13:02
-- Version du serveur : 8.0.33
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `isimgo`
--

-- --------------------------------------------------------

--
-- Structure de la table `chat`
--

DROP TABLE IF EXISTS `chat`;
CREATE TABLE IF NOT EXISTS `chat` (
  `msg_id` int NOT NULL,
  `username1` varchar(20) NOT NULL,
  `username2` varchar(20) NOT NULL,
  `msg` varchar(40) NOT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `publication` varchar(50) NOT NULL,
  `comm` varchar(50) NOT NULL,
  `username_com` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `comment`
--

INSERT INTO `comment` (`id`, `username`, `publication`, `comm`, `username_com`) VALUES
(1, 'admin', 'Welcoom To ISIMGO', 'thank you srr', 'wassim'),
(2, 'admin', 'Welcoom To ISIMGO', 'merci mr', 'amin'),
(3, 'admin', 'Welcoom To ISIMGO', 'done', 'wassim'),
(4, 'wassim', 'bonjour', 'bonjour wassim!', 'amin'),
(5, 'admin', 'Welcoom To ISIMGO', 'thank you ', 'ali'),
(6, 'wassim', 'samdi soir', 'have fun bro', 'salah'),
(7, 'admin', 'Welcoom To ISIMGO', 'ok', 'emna'),
(8, 'admin', 'Welcoom To ISIMGO', 'okk', 'emna');

-- --------------------------------------------------------

--
-- Structure de la table `friend_request`
--

DROP TABLE IF EXISTS `friend_request`;
CREATE TABLE IF NOT EXISTS `friend_request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usernameA` varchar(50) NOT NULL,
  `usernameB` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `frined`
--

DROP TABLE IF EXISTS `frined`;
CREATE TABLE IF NOT EXISTS `frined` (
  `id_friendship` int NOT NULL AUTO_INCREMENT,
  `usernameA` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `usernameB` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_friendship`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `frined`
--

INSERT INTO `frined` (`id_friendship`, `usernameA`, `usernameB`) VALUES
(1, 'wassim', 'admin'),
(2, 'admin', 'wassim'),
(3, 'amin', 'wassim'),
(4, 'wassim', 'amin'),
(5, 'amin', 'admin'),
(6, 'admin', 'amin'),
(7, 'mohamed', 'admin'),
(8, 'admin', 'mohamed'),
(9, 'mostafa', 'ali'),
(10, 'ali', 'mostafa'),
(11, 'ali', 'amin'),
(12, 'amin', 'ali'),
(13, 'wassim', 'houda'),
(14, 'houda', 'wassim'),
(15, 'wassim', 'wael'),
(16, 'wael', 'wassim'),
(17, 'emna', 'wassim'),
(18, 'wassim', 'emna'),
(19, 'wassim', 'mayssem'),
(20, 'mayssem', 'wassim'),
(21, 'wassim', 'ala'),
(22, 'ala', 'wassim'),
(23, 'wassim', 'hanen'),
(24, 'hanen', 'wassim');

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id_msg` int NOT NULL AUTO_INCREMENT,
  `usernameA` varchar(20) NOT NULL,
  `usernameB` varchar(20) NOT NULL,
  `msg` varchar(50) NOT NULL,
  PRIMARY KEY (`id_msg`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `message`
--

INSERT INTO `message` (`id_msg`, `usernameA`, `usernameB`, `msg`) VALUES
(51, 'admin', 'wassim', 'hello wassim'),
(52, 'wassim', 'admin', 'hi admin '),
(53, 'admin', 'wassim', 'cv'),
(54, 'wassim', 'admin', 'cv et tt admin'),
(55, 'amin', 'wassim', 'bnj wassim je suis amin '),
(56, 'wassim', 'amin', 'bonjour amin je suis wassim'),
(57, 'amin', 'wassim', 'ok wassim'),
(58, 'wassim', 'amin', 'ok amin'),
(59, 'wassim', 'admin', 'cc'),
(60, 'admin', 'wassim', 'ok'),
(61, 'mostafa', 'ali', 'bonjour ali c\'est mostafa'),
(62, 'ali', 'mostafa', 'bonjour'),
(63, 'mostafa', 'ali', 'cv'),
(64, 'ali', 'mostafa', 'cv ett'),
(65, 'ali', 'amin', 'bonjour amin'),
(66, 'amin', 'ali', 'bonjour'),
(67, 'ali', 'amin', 'cv'),
(68, 'amin', 'ali', 'cv w enta ali '),
(69, 'wassim', 'emna', 'bojour'),
(70, 'emna', 'wassim', 'bonjour wassim'),
(71, 'wassim', 'mayssem', 'bonjour mayssem'),
(72, 'mayssem', 'wassim', 'bonjour wassim'),
(73, 'mayssem', 'wassim', ' hello my freind'),
(74, 'wassim', 'mayssem', 'whats\'up'),
(75, 'mayssem', 'wassim', '50515346'),
(76, 'wassim', 'mayssem', 'OK'),
(77, 'mayssem', 'wassim', 'chbik tonzr '),
(78, 'ala', 'wassim', 'bnj'),
(79, 'wassim', 'hanen', 'hi'),
(80, 'hanen', 'wassim', 'heloo');

-- --------------------------------------------------------

--
-- Structure de la table `publication`
--

DROP TABLE IF EXISTS `publication`;
CREATE TABLE IF NOT EXISTS `publication` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `pub` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `publication`
--

INSERT INTO `publication` (`id`, `username`, `pub`) VALUES
(1, 'admin', 'Welcoom To ISIMGO'),
(2, 'wassim', 'bonjour'),
(3, 'wassim', 'hello'),
(4, 'wassim', 'test'),
(5, 'wassim', 'hi'),
(6, 'wael', 'bonjour ISIMG'),
(7, 'wassim', 'samdi soir'),
(8, 'wassim', 'bnjr'),
(9, 'wassim', 'kk');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `idusers` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`idusers`, `username`, `password`) VALUES
(1, 'admin', 'admin'),
(2, 'wassim', 'wassim'),
(3, 'amin', 'amin'),
(4, 'mohamed', 'mohamed'),
(5, 'mostafa', 'mostafa'),
(6, 'ali', 'ali'),
(7, 'salah', 'salah'),
(8, 'omar', 'omar'),
(9, 'houda', 'houda'),
(10, 'wael', 'wael'),
(11, 'emna', 'emna'),
(12, 'mayssem', 'mayssem'),
(13, 'ala', 'ala'),
(14, 'hanen', 'hanen');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
