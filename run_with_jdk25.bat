@echo off
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd services\account-service
.\mvnw.cmd spring-boot:run
pause
