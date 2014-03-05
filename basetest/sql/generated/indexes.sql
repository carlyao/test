create index IX_9B3C5965 on Account (accountId);
create index IX_BC343FCC on Account (loginUserId);

create index IX_34A3E023 on AccountHistory (accountHistoryId);
create index IX_39D9F7EB on AccountHistory (accountId);

create index IX_5D001447 on User (accountId);
create index IX_1BF4CA2E on User (loginUserId);
create index IX_1E65EC2B on User (userId);

create index IX_AF4C60AC on UserWallet (userId, walletId);
create index IX_8632331D on UserWallet (userWalletId);

create index IX_E13D1949 on UserWalletTransaction (transactionId);
create index IX_86FCD9FA on UserWalletTransaction (userId);
create index IX_34DE9A62 on UserWalletTransaction (userId, walletId);

create index IX_9935956D on Wallet_Def (walletId);