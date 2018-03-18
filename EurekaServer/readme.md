Eureka Service
---------------------
	- Registration and monitoring of all services related to funcationality

Product service
--------------------
	 - Function related to product maintain and selling { spring rest, spring cloud , spring data with postgres }
	/add - post {json } -> respond {json}---- register the product
	/search /{id} -->get ---> get the information of product if its a valid product.
	/update/{prodid} - put {json } -> respond {json } -----updates the product info
	/import - post{json} -> respond {json} --- add entires realted to product stock available

	/sell - post{json } -> respond {json} and calls customer service for feedback
	/search/{name} --> get ---> search for the product using the name and returns if available

Customer service
--------------------
	- Function related to user feedback and product feed { spring rest, spring cloud , spring data with postgres }
	/insert - post{json } -> respond {json} --- register customer details and their remarks

Postgres - 
----------
	Two schemas for two services - {product and customer }

	----Created two schemas manually	
	
	
Docker - 
---------
	docker build . -t <image-name>
	
	Which will build a docker images and place that in our images 
registry with <image-name>
  
   docker images 
   	Will list out all the images in our registry
  
   docker run -d -p 8762:8761 <image-name> will start the container in port
8762  	
		-d ----> start in detach mode make sure running in background
		-p ----> port numbers right side is app port and left side is host port
		<image-name> ---> provide the image name we got from docker build 
  
   docker ps -a
   	Will list out all the running containers in our docker container registry