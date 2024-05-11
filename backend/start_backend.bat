REM
REM start_backend.bat - Helper script to start SpringBoot Web Application
REM Copyright (C) 2016 - 2024 CyberDay Studios (Author: Nguyen Duy Thanh). All right reserved.
REM
REM USAGE:
REM       cmd.exe /c start_backend.bat
REM OR CHANGE DIRECTORY TO ROOT DIRECTORY, THEN TYPE:
REM        start_backend.bat
REM

@echo off

SET CURRENT_DIRECTORY=%~dp0

java -version 2> %CURRENT_DIRECTORY%\temp.txt
findstr /C:"version" %CURRENT_DIRECTORY%\temp.txt > nul
if %errorlevel%==0 (
    echo Java is installed. Continue
) else (
    echo Java isn't installed. Please install JDK to continue
    exit /b 1
)

del /f /s /q %CURRENT_DIRECTORY%\temp.txt

gradlew bootRun
