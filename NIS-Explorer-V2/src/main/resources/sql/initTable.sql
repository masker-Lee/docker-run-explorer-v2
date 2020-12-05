CREATE TABLE If Not Exists `blocks`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `height` int(10) UNIQUE NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
--  UNIQUE INDEX `height_UNIQUE` (`height` ASC),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `accountmosaics`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` varchar(50) NOT NULL,
  `mosaicID` varchar(200) NOT NULL,
  `quantity` BIGINT(15) NOT NULL,
--  UNIQUE INDEX `address_INDEX` (`address`),
--  INDEX `mosaicID_INDEX` (`mosaicID`) ,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `accountremarks`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` varchar(50) UNIQUE NOT NULL,
  `remark` text,
--  UNIQUE INDEX `address_UNIQUE` (`address`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `accounts`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` varchar(50) UNIQUE NOT NULL,
  `balance` BIGINT(20) DEFAULT 0,
  `blocks` BIGINT(20) DEFAULT 0,
  `fees` BIGINT(20) DEFAULT 0,
  `label` varchar(200) DEFAULT NULL,
  `lastBlock` BIGINT(20) DEFAULT 0,
  `publicKey` varchar(80),
  `timeStamp` BIGINT(15) DEFAULT 0,
--  `remark` varchar(200),
  UNIQUE INDEX `address_UNIQUE` (`address`),
  INDEX `publickey_INDEX` (`publicKey`),
--  INDEX `balance_INDEX` (`balance`),
--  INDEX `blocks_INDEX` (`blocks`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `errormessages`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` text NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `mosaics`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `mosaicID` varchar(200) UNIQUE NOT NULL,
  `mosaicName` varchar(200) NOT NULL,
  `namespace` varchar(200) NOT NULL,
  `description` text,
  `divisibility` int(3),
  `initialSupply` BIGINT(20),
  `supplyMutable` int(3),
  `transferable` int(3),
  `levyType` int(3),
  `levyRecipient` varchar(50),
  `levyNamespace` varchar(200),
  `levyMosaic` varchar(200),
  `levyFee` BIGINT(20),
  `creator` varchar(50) NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
  `height` int(10) NOT NULL,
  `no` BIGINT(15) UNIQUE NOT NULL,
--  UNIQUE INDEX `mosaicID_UNIQUE` (`mosaicID`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `mosaictransactions`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `hash` varchar(64) NOT NULL,
  `no` BIGINT(15) UNIQUE NOT NULL,
  `sender` varchar(50) NOT NULL,
  `recipient` varchar(50) NOT NULL,
  `namespace` varchar(200) NOT NULL,
  `mosaic` varchar(200) NOT NULL,
  `quantity` BIGINT(15) NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
--  INDEX `namespace_INDEX` (`namespace`),
--  INDEX `mosaic_INDEX` (`mosaic`),
--  INDEX `timeStamp_INDEX` (`timeStamp`),
--  INDEX `sender_INDEX` (`timeStamp`),
--  INDEX `recipient_INDEX` (`timeStamp`),
--  INDEX `no_INDEX` (`timeStamp`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `namespaces`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `namespace` varchar(200) NOT NULL,
  `no` BIGINT(15) UNIQUE NOT NULL,
  `rootNamespace` varchar(200),
  `rootNamespaceID` int(10),
  `creator` varchar(50) NOT NULL,
  `height` int(10) NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
  `expiredTime` BIGINT(15) NOT NULL,
  `subNamespaces` varchar(8000),
  `mosaicNames` varchar(200),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `poll`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `timeStamp` BIGINT(15) NOT NULL,
  `creator` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `title` text NOT NULL,
  `description` text,
  `type` int(3) NOT NULL,
  `multiple` int NOT NULL,
  `doe` BIGINT(15) NOT NULL,
  `strings` text NOT NULL,
  `addresses` text NOT NULL,
  `whitelist` text NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `pollindexes`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `creator` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `title` text NOT NULL,
  `type` int(3) NOT NULL,
  `doe` BIGINT(15) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `supernodes`(
  `id` INT UNIQUE NOT NULL,
  `host` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `time` varchar(50) NOT NULL,
  `payoutAddress` varchar(50) NOT NULL,
--  INDEX `payoutAddress_INDEX` (`payoutAddress`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `supernodepayouts`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `round` int NOT NULL,
  `sender` varchar(50) NOT NULL,
  `recipient` varchar(50) NOT NULL,
  `amount` BIGINT(15) NOT NULL,
  `fee` BIGINT(20) NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
--  INDEX `round_INDEX` (`round`),
--  INDEX `timeStamp_INDEX` (`timeStamp`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `transactions`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `hash` varchar(64) UNIQUE NOT NULL,
  `height` int(10) NOT NULL,
  `sender` varchar(50),
  `recipient` varchar(50) NOT NULL,
  `amount` BIGINT(15) NOT NULL,
  `fee` BIGINT(20) NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
  `deadline` BIGINT(15) NOT NULL,
  `signature` varchar(128) NOT NULL,
  `type` int NOT NULL,
  `apostilleFlag` int,
  `mosaicTransferFlag` int,
  `aggregateFlag` int,
--  UNIQUE INDEX `hash_INDEX` (`hash`),
--  INDEX `sender_INDEX` (`hash`),
--  INDEX `recipient_INDEX` (`hash`),
--  INDEX `height_INDEX` (`height`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `unconfirmedtransactions`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `signature` varchar(128) NOT NULL,
  `timeStamp` BIGINT(15) NOT NULL,
  `deadline` BIGINT(15) NOT NULL,
  `otherHash` varchar(64),
  `detail` text NOT NULL,
--  INDEX `signature_INDEX` (`signature`),
--  INDEX `timeStamp_INDEX` (`timeStamp`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
  


