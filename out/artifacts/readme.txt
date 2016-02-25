This application uses JDBC to establish database connection and access the MySQL database. In order to use this application, you need to import the sql file shopping_cart.sql to a new database in your local MySQL server. shopping_cart.sql contains queries for building the database schemas used in this application. It is included in the JAR directory. 

After importing the shopping_cart.sql file to the database. Go to database.java in the model package in java source file folder. In the Database class, change the db_URL, db_username, db_password properties to your MySQL server's credential.

db_URL = "jdbc:mysql://"+host name+"/"+database name

Note: After seller adds new products, the images of the newly added products might not get displayed because I am not allowed to move the image file from the file system to the jar's resource folder. In order to use that feature, jar file needs to be extracted. That feature is working correctly when I run the source code in IntelliJ IDE. I also have demonstrated that feature in the video.
