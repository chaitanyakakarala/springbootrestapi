Add the below entry in PostgreSQL\9.6\data\pg_hba.conf to make sure it accepts connections from global ip

host  all   			all 			0.0.0.0/0  				trust 

Then restart the service of database

Verify the app getting connections without hardcoding