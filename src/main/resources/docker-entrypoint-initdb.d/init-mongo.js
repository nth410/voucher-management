db.createUser({
    user: "test",
    pwd: "test",
    roles: [
        {
            role: "dbAdmin",
            db: "voucher-management"
        }
    ]
});