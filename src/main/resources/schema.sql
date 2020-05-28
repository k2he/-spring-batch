CREATE DATABASE `spring_batch`;

CREATE TABLE  `spring_batch`.`user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DELIMITER //
CREATE PROCEDURE `move_stage_to_orders_table` ()
BEGIN
	DECLARE MAX_ID, MIN_ID INT DEFAULT 0;
	
	SELECT MAX(id) INTO MAX_ID
	FROM orders;
	
	IF MAX_ID IS NULL THEN
		SET MAX_ID = 0;
	END IF;
	
	SELECT MIN(id) INTO MIN_ID
	FROM orders;
	
	IF MIN_ID IS NULL THEN
		SET MIN_ID = 0;
	END IF;
	
	INSERT INTO orders (order_ref, amount, order_date, note, product_json, created_date_time)
	SELECT order_ref, amount, order_date, note, product_json, created_date_time
	FROM (
		SELECT order_ref, amount, order_date, note, product_json, created_date_time
		FROM orders_stage
	) AS s;
	
	INSERT INTO orders_history (order_ref, amount, order_date, note, product_json, created_date_time, archived_date_time)
	SELECT order_ref, amount, order_date, note, product_json, created_date_time, NOW()
	FROM (
		SELECT order_ref, amount, order_date, note, product_json, created_date_time
		FROM orders
		WHERE id >= MIN_ID AND id <= MAX_ID
	) AS od;
	
	DELETE FROM orders WHERE id >= MIN_ID AND id <= MAX_ID; 
	
END;
//

DELIMITER ;

	