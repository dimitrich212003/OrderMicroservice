db = db.getSiblingDB('orders');

db.createUser({
    user: "user",
    pwd: "user",
    roles: [
        { role: "readWrite", db: "orders" }
    ]
});