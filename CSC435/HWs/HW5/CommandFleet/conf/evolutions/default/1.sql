# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table BATTLES (
  DateStarted               timestamp,
  DateEnded                 timestamp,
  BlueWin                   boolean,
  Turns                     integer,
  KnockedBlue               integer,
  LostBlue                  integer,
  KnockedRed                integer,
  LostRed                   integer,
  FiredBlue                 integer,
  HitBlue                   integer,
  FiredRed                  integer,
  HitRed                    integer,
  BlueUser                  varchar(255),
  RedUser                   varchar(255))
;

create table COMMENTS (
  Poster                    varchar(255),
  Target                    varchar(255),
  Timestamp                 timestamp,
  Msg                       varchar(255))
;

create table CUSTOMIZATIONS (
  username                  varchar(255) not null,
  timestamp                 timestamp,
  profpic                   varchar(255),
  backpic                   varchar(255),
  constraint pk_CUSTOMIZATIONS primary key (username))
;

create table FRIENDS (
  FriendOne                 varchar(255),
  FriendTwo                 varchar(255))
;

create table USERS (
  username                  varchar(255) not null,
  timestamp                 timestamp,
  display                   varchar(255),
  password                  varchar(255),
  constraint pk_USERS primary key (username))
;

create sequence CUSTOMIZATIONS_seq;

create sequence USERS_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists BATTLES;

drop table if exists COMMENTS;

drop table if exists CUSTOMIZATIONS;

drop table if exists FRIENDS;

drop table if exists USERS;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists CUSTOMIZATIONS_seq;

drop sequence if exists USERS_seq;

