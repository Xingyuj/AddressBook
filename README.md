# AddressBook
AddressBook

### How to start
Download repo
```bash
    https://github.com/Xingyuj/AddressBook.git
```

Go into repo root path simply run (chmod file if no permission)
```bash
    ./start.sh
```
This script will call docker-compose and run DML to insert test data.
**If insert sql failed, which may caused by container not ready. Please run this script again.** After all docker containers start, you should be able to visit http://127.0.0.1:8080/swagger-ui.html to check the system API doc.

### How to test
Run unit tests
```bash
    mvn test
```
Manually call API by using
- [Postman](https://www.getpostman.com/downloads/)
- [Swagger](http://127.0.0.1:8080/swagger-ui.html)

When doing manul test, one should firstly get a `JWT Authorization Token` from Authentication service. This endpoint needs username and password as parameters. For the convenience of testing, an `admin` user has been initialised into database. Please use this admin user to start testing and create other accounts.
```json
{
  "username":"admin",
  "password":"password"
}
```

Futhermore, according to requirements, all resource request needs to add `apiToken` as `header`, value is the token string you just get from `POST` `/login`. The rest specific parameters that each endpoint needs can be found in [API Doc](http://127.0.0.1:8080/swagger-ui.html)

### How to build

This project is using maven as dependencies management. To install dependencies you should
```bash
    mvn install
```
to get a runnable jar run
```bash
    mvn package
```
### Requirements
- [git](https://git-scm.com/downloads)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/community-edition)
- [Docker-Compose](https://docs.docker.com/compose/install/)
