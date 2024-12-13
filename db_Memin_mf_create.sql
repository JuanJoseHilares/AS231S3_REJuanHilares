---- CREAR BASE DE DATOS
CREATE DATABASE db_Memin_mf;
GO

---- USAR BASE DE DATOS
USE db_Memin_mf;
GO

---- CREACIÓN DE LA TABLA 'products' (productos)
CREATE TABLE garments (
    id INT IDENTITY(1,1) NOT NULL,
    code CHAR(7) NOT NULL,
    name VARCHAR(25) NOT NULL,
    brand VARCHAR(20) NOT NULL,
    category VARCHAR(20) NOT NULL,
    price DECIMAL(10,5) NOT NULL,
    state CHAR(1) NOT NULL,
    CONSTRAINT garments_pk PRIMARY KEY (id)
);
GO

---- CREACIÓN DE LA TABLA 'kardex'
CREATE TABLE kardex (
    id INT IDENTITY(1,1) NOT NULL,
    products_id INT NOT NULL,
    registration_date DATE NOT NULL,
    month VARCHAR(15) NOT NULL,
    motion CHAR(6) NOT NULL,
    amount INT NOT NULL,
    unit_cost DECIMAL(10,2) NOT NULL,
    total_cost DECIMAL(10,2) NOT NULL,
    state CHAR(1) NOT NULL,
    CONSTRAINT kardex_pk PRIMARY KEY (id),
    -- Definición de la clave foránea para relacionar 'kardex' con 'products'
    CONSTRAINT fk_products_id FOREIGN KEY (products_id)
        REFERENCES garments (id)
);
GO

INSERT INTO garments (code, name, brand, category, price, state)
VALUES
    ('PCJM001', 'pantalones jean', 'Nike', 'caballero', 30.00, 'A'),
    ('PCJM002', 'camisa corta', 'Levi', 'caballero', 20.00, 'A'),
    ('PCJM003', 'gorro rojo', 'Adidas', 'caballero', 10.00, 'A');

INSERT INTO kardex (products_id, registration_date, month, motion, amount, unit_cost, total_cost, state)
VALUES
    (2, '2024-10-13', 'Noviembre', 'Venta', 2, 20.00, 40.00, 'A');



select * from garments;
select * from kardex;

UPDATE kardex SET state = 'A' WHERE id = 2;

SELECT id, code, name, brand, category, price, state FROM garments

SELECT k.id, g.name AS product_name, k.registration_date, k.month, k.motion, k.amount, k.unit_cost, k.total_cost, k.state FROM kardex k  
JOIN garments g ON k.products_id = g.id;

SELECT k.id, 
       g.name AS product_name, 
       k.registration_date, 
       k.month, 
       k.motion, 
       k.amount, 
       k.unit_cost, 
       k.total_cost, 
       k.state
FROM kardex k
JOIN garments g ON k.products_id = g.id
WHERE k.state;
