@echo off
cd /d "%~dp0"

if exist yugabyte.env (
  for /f "usebackq eol=# tokens=1,* delims==" %%a in ("yugabyte.env") do (
    set "%%a=%%b"
  )
)

if "%YUGABYTE_JDBC_URL%"=="" (
  echo.
  echo No hay credenciales de YugabyteDB configuradas.
  echo.
  echo 1. Crea un clúster en https://cloud.yugabyte.com
  echo 2. Descarga credentials.txt
  echo 3. Copia yugabyte.env.example como yugabyte.env
  echo 4. Pega HOST y password en yugabyte.env
  echo.
  pause
  exit /b 1
)

echo Conectando a YugabyteDB...
echo Usuario: %YUGABYTE_DB_USER%
call mvnw.cmd spring-boot:run
