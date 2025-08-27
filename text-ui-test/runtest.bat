@ECHO OFF

REM compile the code into the bin folder
dir /s /b ..\src\main\java\jettvarkis\*.java > sources.txt
javac -d ..\bin @sources.txt
del sources.txt
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin jettvarkis.JettVarkis < input.txt > ACTUAL.TXT
FC ACTUAL.TXT EXPECTED.TXT
