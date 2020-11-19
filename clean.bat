@ECHO off
SET mypath=%~dp0
CD /D %mypath%
ECHO %CD%
del /s *.class