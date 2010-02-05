create SCHEMA @DB-NAME@ AUTHORIZATION DBA;

grant @DB-NAME@.* to @DB-USERNAME@@"%" identified by "@DB-PASSWORD@";