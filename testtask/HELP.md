# Web application

### Prepare to start

You must follow these steps to run the web application correctly:

* git clone to your IDEA
* Maven->ProjectName->Lifecycle->clean,install
* Restart IDEA 
* Maven->ProjectName->Plugins->frontend:npm,webpack
* [Download MySQL](https://cdn.mysql.com/archives/mysql-installer/mysql-installer-community-5.7.37.0.msi)
* Change application.properties->spring.datasource
* Hint: You need to create your data table in MySQL and insert data table name in spring.datasource.url instead "testtask"
* Run TesttaskApplication

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

