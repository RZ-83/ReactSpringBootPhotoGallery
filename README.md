# ReactSpringBootPhotoGallery
Spring Boot application that serves as a backend for a photo album gallery using secured APIs with reactive spring boot security. <br>
It performs the following:
<ul>
  <li>Reactive Programming with Webflux</li>
  <li>Implementing Reactive Spring Security</li>
  <li>Asynchronous non-blocking API to import users’ galleries using below public APIs</li>
  <li>CRUD on Photos, Albums and Users</li>
  <li>API to get a user Albums and photos with sorting by updated date at the albums level first then the photos level</li>
  <li>Connecting to Mongo database</li>
</ul>

2 Users are available for this sample project
<ul>
  <li>user
	<ul>
		<li>username: user</li>
    <li>password: user</li>
    <li>role:USER</li>
	</ul>
  </li>
  
  <li>zak
		<ul>
		<li>username: zak</li>
    <li>password: zak123</li>
    <li>role:ADMIN</li>
	</ul>
  </li>
</ul>
The class used for security is SpringSecurityConfig

## Apis Available
While testing the APIs using Postman, you should fill the Authorization section by selecting the Basic Auth autorization. 

### Data APIs
These Apis can only be run by an ADMIN. It consists of 2 APIS, one for cleaning the gallery data and the other to import the gallery from the following given google APIS:
<ul>
  <li>https://jsonplaceholder.typicode.com/photos</li>
  <li>https://jsonplaceholder.typicode.com/albums</li>
  <li>https://jsonplaceholder.typicode.com/users</li>
</ul>
Related Java Class: ImportDataController

#### Clean Gallery Data
http://localhost:9292/data/cleanGallery<br>
Username: zak<br>
Password: zak123<br>
Role: ADMIN<br>


#### Import New Data
http://localhost:9292/data/importGallery<br>
Role needed: Admin<br>

### Users
<ul>
<li>http://localhost:9292/users</li>
<ul>
  <li>Gets all users from the mongodb photogallerydb</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/users/{id}</li>
<ul>
  <li>Gets a user by a given id.</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/users/admin/insert</li>
<ul>
  <li>Inserts and user with the data provided in the request body</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/users/admin/update/{id}</li>
<ul>
  <li>Updates the user for the given id with the data provided in the request body</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/users/admin/delete/{id}</li>
<ul>
  <li>Deletes a specific user by it's given id </li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/users/admin/delete</li>
<ul>
  <li>Delete All users</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/users/admin/importUsers</li>
<ul>
  <li>Import All users</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/users/getalbumsbyuserid/{userId}</li>
<ul>
  <li>Gets photos of the galleries of a given user id</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
</ul>

### Albums

<ul>
<li>http://localhost:9292/albums</li>
<ul>
  <li>Gets all albums from the mongodb photogallerydb</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/albums/{id}</li>
<ul>
  <li>Gets an album by a given id.</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/albums/admin/insertalbum</li>
<ul>
  <li>Inserts and album with the data provided in the request body</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/albums/admin/update/{id}</li>
<ul>
  <li>Updates the album for the given id with the data provided in the request body</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/albums/admin/delete/{id}</li>
<ul>
  <li>Deletes a specific album by it's given id </li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/albums/admin/delete</li>
<ul>
  <li>Delete All Albums</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/albums/admin/importAlbums</li>
<ul>
  <li>Import All Albums</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/albums/getalbumsbyuserid/{userId}</li>
<ul>
  <li>Gets albums of a given user</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/albums/getalbumsbyTitle/{title}</li>
<ul>
  <li>Gets albums containing the given title</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
</ul>

### Photos

<ul>
<li>http://localhost:9292/photos</li>
<ul>
  <li>Gets all photos from the mongodb photogallerydb</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/photos/{id}</li>
<ul>
  <li>Gets a photo by a given id.</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
<li>http://localhost:9292/photos/admin/insertphoto</li>
<ul>
  <li>Inserts and photo with the data provided in the request body</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/photos/admin/update/{id}</li>
<ul>
  <li>Updates the photo for the given id with the data provided in the request body</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/photos/admin/delete/{id}</li>
<ul>
  <li>Deletes a specific photo by it's given id </li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/photos/admin/delete</li>
<ul>
  <li>Delete All Albums</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/photos/admin/importPhotos</li>
<ul>
  <li>Import All photos</li>
  <li>Only ADMIN allowed</li>
</ul>
<li>http://localhost:9292/photos/getalbumphotos/{id}</li>
<ul>
  <li>Gets photos of a given album id</li>
  <li>USER and ADMIN roles allowed</li>
</ul>
</ul>


## MongoDB
The database photogallerydb that I am using in this project is a docker mongodb image deployed on my windows docker engine.<br>
You should have a docker engine then run the docker-compose.yaml available in this project under src folder.<br>

## SCM used
The Source code management used is git & github repository plugins implemented in intellij.<br>

## IDE used
Intellij Community Edition<br>

