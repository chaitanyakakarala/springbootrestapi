##Starting mongo db on the fly without service.
##mongod --dbpath=D:/mongodb/data/ --storageEngine=mmapv1
Just a quick note . The storege engine option is not required for system with
windows 10 / mac / linux. Its only for windows 7


##db.product.find({"productid":"13860428"}) --list the product by id
##db.product.insert({"productid":"testid"})

Few queries help for operating mongo db

##Monja DB
find plugin from monja db and fire it up by adding connection from dbtree view in 
left hand side pane.
Or we can use Robo mongo ui client

## Running code
java -jar data-store.jar

## Making jar 
mvn install on project folder where pom.xml exists / right click on project root of ide 
and say maven > install

## Running test cases 
mvn test on pom.xml folder / right click on project root > maven > maven test
This would trigger both integration as well unit test cases


