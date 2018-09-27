@Echo off
echo Apply Script: COPY
echo %1
echo F|xcopy /y /s /f /q "%1" "C:\Users\chris\Test Servers\Spigot 1.9\plugins\AdditionsAPI.jar"
echo F|xcopy /y /s /f /q "%1" "C:\Users\chris\Test Servers\Spigot 1.12.2\plugins\AdditionsAPI.jar"