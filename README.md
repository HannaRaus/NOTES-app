# GOIT NOTES

### Functionality

Service for storing text notes with the ability to send these notes to other users.

1. Note
   1. Create
   2. Edit
   3. Delete
   4. Share
   
User can create note with 'Public' or 'Private' status. 
 
User can share public notes, and they will be available for not registered users. 
In the same time notes with private access are available only for the registered users.

### Project info

Run the application on port 9999.
For a local purpose inmemory H2 database is using. User **local** profile for running app on you local environment.

For production environment the application is deployed on the heroku server. The **prod** profile is using for this purpose.
##### Address: https://goitnotesapp.herokuapp.com/
MySQL database is using on production environment.
Flyway framework is using for migration.

   