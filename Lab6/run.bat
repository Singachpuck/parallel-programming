@echo off

rem Verify if mpj package is installed in local repository
echo:
echo Verifying if mpj lib is installed inside maven repository
echo:
call mvn dependency:get -Dartifact=mpj:mpj:0.44

rem If not then install it
if %errorlevel% == 0 goto mpj_installed
echo:
echo Mpj library is not in maven repository. Trying to install it...
echo:
call mvn install:install-file -Dfile=%MPJ_HOME%/lib/mpj.jar -DgroupId=mpj -DartifactId=mpj -Dversion=0.44 -Dpackaging=jar

:mpj_installed
rem Build application
echo:
echo Start building application...
echo:

if "%MODE%"=="BLOCKING" (set PROFILE=blocking-communication)
if "%MODE%"=="NON_BLOCKING" (set PROFILE=non-blocking-communication)
if "%MODE%"=="SIMPLE" (set PROFILE=simple-blocking)
if "%MODE%"=="SIMPLE_NON_BLOCKING" (set PROFILE=simple-non-blocking)

echo Using mode: %MODE%
echo:

set PROJECT_NAME=mpi-project
call mvn clean package -Djar.finalName=%PROJECT_NAME% -P %PROFILE%

rem Run mpi application
echo:
echo Running mpi application
echo:
if not defined ROWS1 (set ROWS1=10)
if not defined COLS1 (set COLS1=10)
if not defined ROWS2 (set ROWS2=10)
if not defined COLS2 (set COLS2=10)

call %MPJ_HOME%/bin/mpjrun.bat -np 7 -jar target/%PROJECT_NAME%.jar -m1 "%ROWS1%,%COLS1%" -m2 "%ROWS2%,%COLS2%"
