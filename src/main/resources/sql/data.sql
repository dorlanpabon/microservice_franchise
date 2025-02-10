CREATE SCHEMA IF NOT EXISTS `franchise`;

USE `franchise`;

CREATE TABLE IF NOT EXISTS `franchise` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `branch` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(50) NOT NULL ,
    `franchise_id` INT NOT NULL ,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `product` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(50) NOT NULL ,
    `stock` INT NOT NULL ,
    `branch_id` INT NOT NULL ,
    PRIMARY KEY (`id`)
);
