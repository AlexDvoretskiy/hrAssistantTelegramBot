SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password char(80) NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  phone varchar(15) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
  REFERENCES users (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
  REFERENCES roles (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS company_info;

CREATE TABLE company_info (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500) NOT NULL,
    contacts VARCHAR(250),
    active BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS candidates;

CREATE TABLE candidates (
  id INT NOT NULL AUTO_INCREMENT,
  chat_id INT NOT NULL,
  stage_id INT NOT NULL,
  first_name VARCHAR(250),
  middle_name VARCHAR(250),
  last_name VARCHAR(250),
  date_of_birth DATE,
  email VARCHAR(50),
  phone VARCHAR(15),
  apply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  cv_file_name VARCHAR(50),
  last_update DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS vacancies;

CREATE TABLE vacancies (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  requirements VARCHAR(2000) NOT NULL,
  conditions VARCHAR(2000) NOT NULL,
  salary DOUBLE,
  assignment_id INT,
  active BOOLEAN DEFAULT TRUE,
  create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT FK_ASSIGNMENT_IDcompany_info FOREIGN KEY (assignment_id)
  REFERENCES assignments (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS assignments;

CREATE TABLE assignments (
  id INT NOT NULL AUTO_INCREMENT,
  file_name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  active BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS candidates_vacancies;

CREATE TABLE candidates_vacancies (
  candidate_id int NOT NULL,
  vacancy_id int NOT NULL,
  PRIMARY KEY (candidate_id,vacancy_id),
  CONSTRAINT FK_CANDIDATE_VACANCY_ID FOREIGN KEY (candidate_id)
  REFERENCES candidates (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_VACANCY_CANDIDATE_ID FOREIGN KEY (vacancy_id)
  REFERENCES vacancies (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS assignment_results;

CREATE TABLE assignment_results (
  id INT NOT NULL AUTO_INCREMENT,
  candidate_id int NOT NULL,
  vacancy_id int NOT NULL,
  file_name VARCHAR(250) NOT NULL,
  passed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT FK_CANDIDATE_ASSIGNMENT_ID FOREIGN KEY (candidate_id)
  REFERENCES candidates (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_ASSIGNMENT_CANDIDATE_ID FOREIGN KEY (vacancy_id)
  REFERENCES vacancies (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

