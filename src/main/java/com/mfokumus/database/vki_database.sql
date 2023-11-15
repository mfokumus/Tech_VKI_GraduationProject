-- DELETE DATABASE
DROP DATABASE `cars`;

-- Create Database
CREATE SCHEMA `cars` DEFAULT CHARACTER SET utf8 COLLATE utf8_turkish_ci ;

-- Set
use vki_db;

-- Table
CREATE TABLE `cars`.`vki` (
                                   `id` INT NOT NULL AUTO_INCREMENT,
                                   `boy` FLOAT NULL ,
                                   `kilo` FLOAT NULL ,
                                   `vucut_kitle_index` FLOAT NULL,
                                   `user_id` INT NULL,
                                   PRIMARY KEY (`id`))
    ENGINE = InnoDB;

-- tabloyayeni kolon eklemek
ALTER TABLE `cars`.`vki`
    ADD COLUMN `system_created_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP;

-- DELETE TABLE
DROP TABLE `cars`.`vki`;

-- TRUNCATE Table
TRUNCATE `cars`.`vki`;

-- INSERT
-- insert into database.table () values ()
INSERT INTO `cars`.`vki` (`boy`, `kilo`, `vucut_kitle_index`,`user_id`) VALUES ('1.78', '85', '22.6',"null");

-- FIND
SELECT * FROM cars.vki where id=1;

-- UPDATE
-- update database.table  set attributes="içerik"
UPDATE `cars`.`vki` SET `boy`='1.78', `kilo`='85', `vucut_kitle_index`='22.6', `user_id`="null" WHERE (`id` = '1');

-- DELETE
DELETE FROM `cars`.`vki` WHERE (`id` = '1');

-- SELECT
SELECT * FROM cars.vki;
select count(*) from `cars`.`vki`;
