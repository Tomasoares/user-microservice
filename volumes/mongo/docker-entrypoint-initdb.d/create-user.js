db.getSiblingDB('user-microservice')

db.createUser(
	{
		user: "creativedriver",
		pwd: "cr34t1v3dr1v3r",
		roles: [ { role: "readWrite", db: "user-microservice" } ]
	}
)

db.users.insert(
	{
		"name": "Creative Driver",
		"email": "creativedriver@gmail.com",
		"password": "cr34t1v3dr1v3r",
		"address": "SC-401",
		"telephone": "37468533467",
		"idUserType": 1
	}
)