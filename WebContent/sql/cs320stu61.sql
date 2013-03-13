-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 07, 2013 at 08:53 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cs320stu61`
--

-- --------------------------------------------------------
drop table if exists pledges;
drop table if exists rewards;
drop table if exists projects;
drop table if exists users;

--
-- Table structure for table `pledges`
--

CREATE TABLE IF NOT EXISTS `pledges` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `pledeged_amount` int(32) NOT NULL DEFAULT '0',
  `reward_amount` int(32) NOT NULL DEFAULT '0',
  `user_id` int(32) DEFAULT NULL,
  `project_id` int(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `project_id` (`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `pledges`
--

INSERT INTO `pledges` (`id`, `pledeged_amount`, `reward_amount`, `user_id`, `project_id`) VALUES
(1, 100, 10, 9, 1),
(2, 200, 20, 7, 2),
(3, 300, 30, 9, 3),
(4, 400, 40, 10, 4),
(5, 500, 50, 9, 5),
(6, 600, 60, 10, 1),
(7, 1230, 1000, 9, 2),
(8, 234, 20, 9, 4),
(9, 234, 50, 10, 9),
(10, 123, 0, 10, 2);

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE IF NOT EXISTS `projects` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `target` int(32) NOT NULL,
  `duration` int(32) NOT NULL,
  `start_date` date NOT NULL,
  `author_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `title`, `description`, `target`, `duration`, `start_date`, `author_id`) VALUES
(1, 'SHOW ME THE MONEY', 'I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer. I''ve been doing it for 5 days now and I still haven''t noticed any improvement.', 10000, 30, '2013-03-24', 7),
(2, 'WHY SO SERIOUS', 'I went up to a homeless man as I came out of a pub last night and said, "What would you say if I asked you to come back to my house for a few drinks and a 3 course meal in front of the fireplace?" "I''d say yes," he replied. "Exactly," I said, shaking my head and walking away, "What the fuck is wrong with women these days?"', 100000, 365, '2013-04-26', 9),
(3, 'LEAVE YOUR SLEEP', 'My daughter was asked to draw something from her personal life for a school project. After, she showed me and I said, "Darling, where did you see pink elephant and a gorilla standing beside a waterfall?" "It''s not," she replied. "It''s mummy and Uncle Kev in the shower."', 500, 3, '2013-03-29', 10),
(4, 'EYE OF SAURON', 'Our neighbour''s dog shit in our garden so my mum told me to get a shovel and throw it over the fence. I don''t see what that solved, now we''ve got dog shit in our garden and the neighbours have our shovel. ', 5000, 18, '2013-03-27', 7),
(5, 'GREED IS GOOD', 'My boss called me into his office today. He said, "I still think you''re not too bright at all, Kev. But you have come early to work for the past 2 years. You deserve a reward." "Gee, thanks boss!" I said. "What''s my reward then?" "How does a brand new car sound?" he asked, smiling. I said, "Vrooom, vrooooom."', 9999999, 100, '2013-02-26', 9),
(9, 'p5', ' So much typing test jobs are boring, do u need a favor? Here is a story: I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer.I''ve been doing it for 5 days now and I still haven''t noticed any improvement. ', 12345, 123, '2013-03-06', 10);

-- --------------------------------------------------------

--
-- Table structure for table `rewards`
--

CREATE TABLE IF NOT EXISTS `rewards` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `amount` int(32) NOT NULL,
  `description` text NOT NULL,
  `project_id` int(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `rewards`
--

INSERT INTO `rewards` (`id`, `amount`, `description`, `project_id`) VALUES
(1, 10, 'Even though we''ve been married 14 years, I still look at my wife the same as I did on my wedding day---With regret.', 1),
(2, 50, 'My legs always feel like jelly after sex.It must be all the running beforehand.', 1),
(3, 100, 'My son''s school is closed today because "the playground is like an ice rink" So we''re off to the ice rink this afternoon.', 1),
(4, 1000, 'We were that poor when I was a child, my bath toys used to be a couple of dirty dishes and some cutlery.', 2),
(5, 5000, 'As my girlfriend started to whine, I gently patted her back, "That''s right. Let it all out." But it doesn''t matter how much air you remove, you can never get your blow-up doll back in the box.', 2),
(6, 20000, 'Whenever my wife says, "We need to talk" I never seem to get much of a chance.', 2),
(7, 1000, 'I think someone may be sending me death threats. Woke up this morning with a Tesco burger on my pillow.', 3),
(8, 5000, 'New releases this week at Blockbuster - The staff.', 3),
(9, 20000, 'Whenever my wife says, "We need to talk" I never seem to get much of a chance.', 3),
(10, 10, 'Tesco are giving treble points on your Clubcard for all burgers and petrol, starting Monday.The deal is called Only Fuel and Horses.', 4),
(11, 20, 'I can''t believe how thick the snow is out there. I asked a snowman for the time and he just stared at me.', 4),
(12, 50, 'My daughter admitted that she is having lesbian sex with her best friend. As a reward for her honesty, I bought her a video camera.', 4),
(16, 999, 'I got sacked tonight for refusing to serve some girl who''d clearly had far too many already. The fat bitch complained to my McManager.', 5),
(17, 9999, 'If women who sleep around too much were labelled "heroes" instead of "sluts", us guys would be having a lot more sex. Someone fucked up here...', 5),
(18, 99999, 'I''ve just seen Black Hawk Down in 3D. It was brilliant. Who needs HMV or Blockbusters when you live in Vauxhall?', 5),
(19, 5, 'So much typing test jobs are boring, do u need a favor? Here is a story: I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer.I''ve been doing it for 5 days now and I still haven''t noticed any improvement.', 9),
(20, 10, 'So much typing test jobs are boring, do u need a favor? Here is a story: I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer.I''ve been doing it for 5 days now and I still haven''t noticed any improvement.', 9),
(21, 25, 'So much typing test jobs are boring, do u need a favor? Here is a story: I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer.I''ve been doing it for 5 days now and I still haven''t noticed any improvement.', 9),
(22, 50, 'So much typing test jobs are boring, do u need a favor? Here is a story: I went to see the doctor about my hearing loss and he gave me some medicine and told me to take two drops a day in my beer.I''ve been doing it for 5 days now and I still haven''t noticed any improvement.', 9);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varbinary(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `users` (`user_name`,`password`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_name`, `password`) VALUES
(7, 'cs320stu31', AES_ENCRYPT('abcd','abcd')),
(9, 'cysun', AES_ENCRYPT('abcd','abcd')),
(10, 'kern', AES_ENCRYPT('kern','kern'));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pledges`
--
ALTER TABLE `pledges`
  ADD CONSTRAINT `pledge_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `pledge_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`);

--
-- Constraints for table `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `project_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `rewards`
--
ALTER TABLE `rewards`
  ADD CONSTRAINT `reward_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
