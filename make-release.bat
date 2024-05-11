SET CURRENT_DIR=%~dp0
SET BACKEND_DIR=%CURRENT_DIR%backend
SET MINIFIER_DIR=%CURRENT_DIR%minifier

CD /D %MINIFIER_DIR% && pip install -r requirements.txt && python minifier.py && CD /D %CURRENT_DIR%
