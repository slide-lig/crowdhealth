-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 10, 2014 at 02:01 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tweet_crowd`
--

-- --------------------------------------------------------

--
-- Table structure for table `master_tweets`
--

CREATE TABLE IF NOT EXISTS `master_tweets` (
  `unique_tweet_id` int(11) NOT NULL AUTO_INCREMENT,
  `dataset_name` varchar(255) NOT NULL,
  `tweet_id` varchar(255) NOT NULL,
  `tweet_text` varchar(255) NOT NULL,
  PRIMARY KEY (`unique_tweet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tweet_labels`
--

CREATE TABLE IF NOT EXISTS `tweet_labels` (
  `label_id` varchar(255) NOT NULL,
  `tweet_id` varchar(255) NOT NULL,
  `label` varchar(255) NOT NULL,
  `unique_tweet_id` int(11) NOT NULL,
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
