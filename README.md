
1) posts management - create,read,update and delete posts. provide pagination and sorting support
operations :
create POST REST API
get single post REST API
get all Posts REST API
get all posts REST API with pagination and sorting
update Post REST API
delete Post REST API

2) comments management - create,read,update and delete comments for blog post

1) create comment REST API -> /posts/{post-id}/comments
2) get single comment REST API -> /posts/{post-id}/comments/{comment-id}
3) get all comments REST API -> /posts/{post-id}/comments
4) update comment REST API -> /posts/{post-id}/comments
5) delete comment REST API -> /posts/{post-id}/comments/{comment-id}


3) auth and authorization - register,login and security

1) secure REST APIs using Database Authentication
2) build Login/Signin REST API
3) Build Register/SignUp REST API
4) Use JWT (Json web token) token based authentication to secure the REST APIs
5) Implement Role based security- ADMIN and User roles


4) category management- create,read,update and delete category
create category REST API
get single category REST API
get all categories REST API
update category REST API
delete category REST API
get posts by category REST API

SPRING BOOT APPLICATION ARCHITECTURE:
 

Postman client -  it is used to check and send the requests to backend
DTO – it is the data between client and controller of Json type
Controller – REST API functionalities (API layer)
Service – main business logic
DAO Repo – all code related to database is in layer of application
Which is connected to database and it is the persistence logic,it communicates with database

