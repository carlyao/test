
create table Account (
	accountId BIGINT not null primary key AUTO_INCREMENT,
	loginUserId VARCHAR(75) null,
	accountName VARCHAR(75) null,
	sessionId VARCHAR(75) null,
	password VARCHAR(75) null,
	registerTime DATETIME null,
	createdBy VARCHAR(75) null,
	lastLoginTime DATETIME null,
	modifiedDate DATETIME null,
	modifiedBy VARCHAR(75) null,
	platform VARCHAR(75) null,
	ip VARCHAR(75) null
);

create table AccountHistory (
	accountHistoryId BIGINT not null primary key AUTO_INCREMENT,
	accountId BIGINT,
	sessionId VARCHAR(75) null,
	keyValue VARCHAR(75) null,
	loginTime DATETIME null,
	modifiedDate DATETIME null,
	ip VARCHAR(75) null
);

create table User (
	userId BIGINT not null primary key AUTO_INCREMENT,
	accountId BIGINT,
	loginUserId VARCHAR(75) null,
	userName VARCHAR(75) null,
	lastLoginIP VARCHAR(20) null
);

create table UserWallet (
	userWalletId BIGINT not null primary key AUTO_INCREMENT,
	userId BIGINT,
	walletId BIGINT,
	value INTEGER,
	createDate DATETIME null
);

create table UserWalletTransaction (
	transactionId BIGINT not null primary key AUTO_INCREMENT,
	userWalletId BIGINT,
	userId BIGINT,
	walletId BIGINT,
	totalSpend INTEGER,
	subject VARCHAR(75) null,
	ip VARCHAR(75) null,
	createDate DATETIME null
);

create table Version (
	version VARCHAR(75) not null primary key,
	createdDate DATETIME null
);

create table Wallet_Def (
	walletId BIGINT not null primary key,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	defaultValue INTEGER
);
