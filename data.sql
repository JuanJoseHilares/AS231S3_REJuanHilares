CREATE TABLE supplier (
    supplier_id INT GENERATED AS IDENTITY PRIMARY KEY,
    names VARCHAR2(90) NOT NULL,
    addres VARCHAR2(50) NOT NULL,
    city VARCHAR2(25) NOT NULL,
    phone_number CHAR(9),
    whatsapp_number CHAR(9) NOT NULL,
    reference_number CHAR(9),
    email VARCHAR2(90) NOT NULL,
    admin_id INT NOT NULL,
    states CHAR(1) DEFAULT 'A'
);

INSERT INTO supplier (names, addres, city, phone_number, whatsapp_number, reference_number, email, admin_id, states)  
VALUES ('Proveedor O''Higgins', 'Av. Principal 123', 'Lima', '912345678', '612345678', '622345678', 'contacto@xyz.com', 1, 'A');
