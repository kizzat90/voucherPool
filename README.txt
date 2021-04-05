Please find the API documents, Postman collection json & database schema in the "API documents & database schema" folder.

Using local Tomcat & MySQL
Create a database with the name "voucherdb". The application will auto create all the tables & relations
when starting up Tomcat with the .war file.
Local Tomcat configurations is in the Local_Tomcat_Config directory


Using Docker
For a quick deployment & testing, I've included a .yaml file to quickly fire up the application with database for docker.
Run "docker-compose -f voucherPool.yaml up" in the terminal where the folder containing .yaml file.
Run "docker-compose -f voucherPool.yaml down" for shutting down the containers