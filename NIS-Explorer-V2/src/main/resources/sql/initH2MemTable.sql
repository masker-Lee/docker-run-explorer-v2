CREATE TABLE If Not Exists `accounth2mem`(
  `id` INT NOT NULL,
  `address` varchar(64),
  `publickey` varchar(64),
  INDEX `id_index` (`id`),
  INDEX `address_index` (`address`),
  INDEX `publickey_index` (`publickey`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
CREATE TABLE If Not Exists `blockh2mem`(
  `id` INT NOT NULL ,
  `harvesterid` INT,
  `harvestedinname` INT,
  `totalfee` BIGINT(20),
  `height` int(10) UNIQUE NOT NULL,
  INDEX `id_index` (`id`),
  INDEX `harvesterid_index` (`harvesterid`),
  INDEX `harvestedinname_index` (`harvestedinname`),
  INDEX `height_index` (`harvestedinname`),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;