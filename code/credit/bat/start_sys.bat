@cd ..
call mvn clean:clean package -P build tomcat7:run-war-only -f pom-sys-server.xml
@pause