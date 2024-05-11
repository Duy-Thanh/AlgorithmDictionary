REM
REM restart_backend.bat - Helper script to restart SpringBoot Web Application
REM Copyright (C) 2016 - 2024 CyberDay Studios (Author: Nguyen Duy Thanh). All right reserved.
REM
REM USAGE:
REM       cmd.exe /c restart_backend.bat
REM OR CHANGE DIRECTORY TO ROOT DIRECTORY, THEN TYPE:
REM        restart_backend.bat
REM

@echo off

stop_backend.bat
start_backend.bat

