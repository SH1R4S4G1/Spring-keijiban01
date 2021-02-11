CREATE TABLE IF NOT EXISTS site_user (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(60) NOT NULL,
	email VARCHAR(254) NOT NULL,
	password VARCHAR(255) NOT NULL,
    admin BOOLEAN NOT NULL,
    role CHAR(16) NOT NULL,
    active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS comment (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	content VARCHAR(40) NOT NULL,
	user_name VARCHAR(60) NOT NULL,
	post_date_time DATETIME NOT NULL,
	parent_comment_id BIGINT NULL,
	FOREIGN KEY (user_name) REFERENCES site_user(user_name)
	);
	
