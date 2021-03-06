SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `I`.`Issue`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `I`.`Issue` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `status` VARCHAR(50) NOT NULL ,
  `title` VARCHAR(512) NOT NULL ,
  `content` TEXT NULL DEFAULT NULL ,
  `creater` VARCHAR(512) NOT NULL ,
  `owner` VARCHAR(512) NULL DEFAULT NULL ,
  `dt_create` DATETIME NOT NULL ,
  `dt_lastupdate` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
