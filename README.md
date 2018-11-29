# LaureaJava
Java project for Laurea University

To build this program you have to downlaod the last update of [mongo-java-driver](http://central.maven.org/maven2/org/mongodb/mongo-java-driver/3.9.1/) and add it in your project dependencies.
On IntelliJ go in File > Project Structure > Modules > Add > From JARs or Directories... and select the ".jar" you just downloaded.

About the program, it allow you to get random recipe (the content is in french). You can add it to the favorite, load your favorites by double clicking on it and remove it if you don't like it anymore.

The GUI is made with Java Swing. The datas are fetch by webscrapping on [marmiton.org](https://www.marmiton.org/) with JSoup and stored in a Mongo database. To access to the database, Mongo-JavaDdriver is used.
