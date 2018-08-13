create schema ChatProgramTui;

CREATE TABLE ChatProgramTui.UserApplication (
        userId INTEGER NOT NULL,
        username varchar(50) NOT NULL,
        password char(32) NOT NULL,
        displayName VARCHAR(50),
        isAdmin BOOLEAN,
        PRIMARY KEY (userId)
);

Select * from CHATPROGRAMTUI.USERAPPLICATION

insert into CHATPROGRAMTUI.USERAPPLICATION (USERID, USERNAME, PASSWORD) values (1, 'initAdmin', 'pw')

CREATE TABLE ChatProgramTui.Session (
	seId INTEGER NOT NULL, 
	Id_user INT,
	token char(64) NOT NULL, 
	PRIMARY KEY (seId)
)
/*Reset*/
DROP TABLE CHATPROGRAMTUI.USERAPPLICATION; 
DROP TABLE CHATPROGRAMTUI.SESSION;
Drop SCHEMA CHATPROGRAMTUI RESTRICT;
/*Create Script*/
CREATE TABLE ChatProgramTui.UserApplication(
	userId INTEGER NOT NULL,
	username varchar(50) NOT NULL,
	password char(32) NOT NULL,
	displayName VARCHAR(50),
	isAdmin BOOLEAN,
	PRIMARY KEY (userId)
);
	/*sqlCommands.add(
			"insert into CHATPROGRAMTUI.USERAPPLICATION (USERID, USERNAME, PASSWORD) values (1, 'initAdmin', 'pw')");*/
CREATE TABLE ChatProgramTui.Session (
	seId INTEGER NOT NULL,
	Id_user INT,
	token char(64) NOT NULL,
	PRIMARY KEY (seId) 
);
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(derby.connection.requireAuthentication, 'true');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.ChatProgramInitAdmin', 'ChatProgramInitAdmin');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode', 'noAccess');
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'noAccess');