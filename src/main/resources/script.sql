CREATE DATABASE db_developer;

CREATE TABLE `departments` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`NAME` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `sellers` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`NAME` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	`email` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	`birth_date` DATETIME NULL DEFAULT NULL,
	`base_salary` DOUBLE(22,0) NULL DEFAULT NULL,
	`department_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `department_id` (`department_id`) USING BTREE,
	CONSTRAINT `sellers_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `db_java_project`.`departments` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;