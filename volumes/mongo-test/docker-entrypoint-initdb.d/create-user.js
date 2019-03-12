db.getSiblingDB('user-microservice')

db.createUser(
	{
		user: "creativedriver",
		pwd: "cr34t1v3dr1v3r",
		roles: [ { role: "readWrite", db: "user-microservice" } ]
	}
)