@ECHO off
SET mypath=%~dp0
CD /D %mypath%
call ./clean
javac Monsters_and_Heroes.java -encoding utf-8