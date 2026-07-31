@ECHO OFF
SETLOCAL

IF "%~1"=="" GOTO help
IF /I "%~1"=="/?" GOTO help
IF /I "%~1"=="--help" GOTO help

IF /I "%~1"=="clean" GOTO clean
IF /I "%~1"=="test" GOTO test
IF /I "%~1"=="app" GOTO app
IF /I "%~1"=="mutation" GOTO mutation
GOTO help

:help
ECHO.
ECHO Bookstore execution utility
ECHO.
ECHO Usage:
ECHO   %~nx0 clean
ECHO   %~nx0 test
ECHO   %~nx0 app [dev^|prod]
ECHO   %~nx0 mutation
ECHO.
ECHO Commands:
ECHO   clean       Removes Maven build artifacts.
ECHO   test        Runs the Maven reactor tests.
ECHO   app         Builds and starts the application; dev is the default profile.
ECHO   mutation    Runs PIT mutation tests.
GOTO end

:clean
CALL mvn clean
GOTO end

:test
CALL mvn clean test
GOTO end

:app
SET "PROFILE=%~2"
IF "%PROFILE%"=="" SET "PROFILE=dev"
IF /I NOT "%PROFILE%"=="dev" IF /I NOT "%PROFILE%"=="prod" (
    ECHO Invalid profile: %PROFILE%
    ECHO Use dev or prod.
    EXIT /B 1
)

CALL mvn install -DskipTests
IF ERRORLEVEL 1 EXIT /B %ERRORLEVEL%

CALL mvn -f configuration spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=%PROFILE%"
GOTO end

:mutation
CALL mvn clean compile
IF ERRORLEVEL 1 EXIT /B %ERRORLEVEL%
CALL mvn install -DskipTests org.pitest:pitest-maven:mutationCoverage
GOTO end

:end
ENDLOCAL