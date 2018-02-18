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