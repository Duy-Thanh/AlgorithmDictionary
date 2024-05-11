REM
REM stop_backend.bat - Helper script to stop SpringBoot Web Application
REM Copyright (C) 2016 - 2024 CyberDay Studios (Author: Nguyen Duy Thanh). All right reserved.
REM
REM USAGE:
REM       cmd.exe /c stop_backend.bat
REM OR CHANGE DIRECTORY TO ROOT DIRECTORY, THEN TYPE:
REM        stop_backend.bat
REM

REM check if java.exe is running
tasklist | findstr /I "java.exe" > nul
if %errorlevel%==0 (
    taskkill /f /im java.exe
)