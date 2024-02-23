# **ReadMe** 


## - **Rental Application** 

The back-end part of an app to connect owners and tenants. </br>
This app uses Java to run.

### **Getting Started**

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### **Prerequisites**

You need to install : 
* Java 17
* Maven 4.0.0
* MySQL 8.0.31

### **Installing** 

* [Install Java](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
* [Install Maven](https://maven.apache.org/install.html)
* [Install MySQL](https://dev.mysql.com/downloads/mysql/)

After downloading the mysql 8 installer and installing it, you will be asked to configure the password for the default root account.

#### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#web)

#### Guides
The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)


## - **Front-end App**

The front-end project uses [Angular](https://angular.io/) version 14.1.0.

### Installing

Go to https://github.com/Solau92/DevFS-Projet-3-Developpez-le-back-end-en-utilisant-Java-et-Spring.git and import the project on your computer. 

Go to the folder that contains the angular.json file and install the node-module by running the command : `npm install`.

#### Reference Documentation 

- [Angular documentation](https://angular.io/docs)
- [Npm documentation](https://docs.npmjs.com/)
- [Node JS documentation](https://nodejs.org/docs/latest/api/)

### Running the front-end app

Run the `ng serve` command.


## - **Running back-end App** 

Post installation of MySQL, Java and Maven, you will have to set up the tables and data in the database. </br>
For this, please run the sql commands present in the *script.sql* file under the *resources/sql* folder in the code base.

For demonstration purpose, you can insert data in database while running the *data.sql* file.

Import the code on your computer.

In the application.properties file you must complete the following fields : 
- security.my-jwtKey : the length of this secret key must be at least 256 bits
- picture-upload-directory : name of the directory that will be created to save pictures
- picture-upload-directory-path : path of the directory in which the previous folder will be created.

To run the app, go to the folder that contains the pom.xml file and execute the following command in which you have to replace "*%username%*" by your username and "%*password*%" by your password required to access your database : 
 `mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.datasource.username=%username% --spring.datasource.password=%password%"`

Navigate to http://localhost:4200 to access the whole app.

NB : 
- if you have run the *data.sql* file, you can use theses credentials to log in : 
   . user : user1@gmail.com
   . password : password 
- the passwords of all users in database are "password"

## - **Documentation** 

You can access the documentation of the app here : 
- http://localhost:3001/swagger/documentation
- http://localhost:3001/swagger/api-do (JSON format)

## - **Version**

1.0.0