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
set PROJECT_NAME=mpi-project
call mvn clean package -Djar.finalName=%PROJECT_NAME%

rem Run mpi application
echo:
echo Running mpi application
echo:
call %MPJ_HOME%/bin/mpjrun.bat -np 4 -jar target/%PROJECT_NAME%.jar
