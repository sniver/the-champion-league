Steps to run project on your environment.
1- Make sure you have mysql server up and running on port 3306 and create digital_factory db.
2- Import project as maven project to your IDE.
3- If you want application to create tables 
	- uncomment "spring.jpa.hibernate.ddl-auto = update" inside application properities.
4- If you want application to run data seed to create league and list of participants  
	- set spring.datasource.runDataSeeds to true.
5- Make sure port 8080 is free to run application.
6- Make sure to set 'spring.mail.username', 'spring.mail.password' If you want try sending mail to league champion.
7- To access swagger documentation : http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config
8- enjoy :)