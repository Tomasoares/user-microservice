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
		"password": "$2a$10$hjqiJJUfcPK2i30aDMj2LeN4ewrTbFMwNjR/eQIxzVcJYB/p52XuS",
		"address": "SC-401",
		"telephone": "37468533467",
		"idUserType": 1
	}
)