# Web application to manage advertising categories and banners.
# The objects can be created, read, updated and deleted via REST API.

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

* [Root main page](http://localhost:8080/root/)
* You can be redirected to login page, then enter login:root password:root
* Non-authorized user can get high cost banner by category.idRequest
* For example http://localhost:8080/bid?cat=cat1&cat=cat2
* Hint: User with the same IP and User-Agent should not see the previously shown banner on the same day.

