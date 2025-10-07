@echo off
REM Navigate to the directory where this batch file is located
cd /d "%~dp0"

REM Run Maven tests from the current directory
mvn test