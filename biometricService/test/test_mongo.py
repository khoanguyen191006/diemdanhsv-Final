from pymongo import MongoClient
import certifi

uri = "mongodb+srv://nguyenhuutrong11133_db_user:HAU6mfQJYL9oQVnf@testing.0je3tw5.mongodb.net/?retryWrites=true&w=majority"

client = MongoClient(uri, tlsCAFile=certifi.where())
print(client.list_database_names())