USE SistV_MC;
GO

-----------------------------------------------------------------------------------------------
---> MAESTRO ADMIN
INSERT INTO admins
	(names, passwords, email, states)
VALUES
	('amir', 'a@12@35', 'amir.arbieto@vallegrande.edu.pe', 'A'),
	('juan', 'ja@523', 'juan.hilares@vallegrande.edu.pe', 'A'),
	('henry', 'ha@011', 'henry.lunazco@vallegrande.edu.pe', 'A');
GO

SELECT * FROM admins
GO

-- Actualizar un administrador
UPDATE admins
SET 
    names = 'amir',
    passwords = 'a@12@35',
    email = 'amir.arbieto@vallegrande.edu.pe'
WHERE
    admin_id = 1;

-----------------------------------------------------------------------------------------------
---> MAESTRO CLIENTE
SET DATEFORMAT dmy;
INSERT INTO client
	(paternal_surname, maternal_surname, names, sex, document_type, document_number, birth_date, phone_number, whatsapp_number, reference_number, email, admin_id)
VALUES
	('De la cruz', 'Gomez', 'Luis Alberto', 'M', 'DNI', '12345678', '1990-01-15', '912345678', '912345678', '912345679', 'luis.ramirez@example.com', 1),
	('Pérez', 'López', 'Ana María', 'F', 'DNI', '87654321', '1988-04-23', '923456789', '923456789', '923456780', 'ana.perez@example.com', 2),
	('Fernandez', 'Diaz', 'Juan Carlos', 'M', 'CNE', '123456789012', '1985-07-19', '934567890', '934567890', '934567891', 'juan.fernandez@example.com', 1),
	('García', 'Martinez', 'Sofía Carolina', 'F', 'DNI', '11223344', '1992-10-12', '945678901', '945678901', '945678902', 'sofia.garcia@example.com', 2),
	('Torres', 'Vargas', 'Pedro José', 'M', 'CNE', '098765432109', '1983-03-25', '956789012', '956789012', '956789013', 'pedro.torres@example.com', 3),
	('Mendoza', 'Flores', 'Andrea Isabel', 'F', 'DNI', '22334455', '1995-06-18', '965432198', '965432198', '965432199', 'andrea.mendoza@example.com', 2),
	('Rojas', 'Castillo', 'Carlos Eduardo', 'M', 'DNI', '33445566', '1991-02-27', '975431276', '975431276', '975431277', 'carlos.rojas@example.com', 1),
	('Salas', 'Ríos', 'María Fernanda', 'F', 'DNI', '44556677', '1987-11-15', '985432198', '985432198', '985432199', 'maria.salas@example.com', 3),
	('Alvarez', 'Paredes', 'Miguel Angel', 'M', 'CNE', '445566778899', '1990-05-10', '995432276', '995432276', '995432277', 'miguel.alvarez@example.com', 1),
	('Cruz', 'Lopez', 'Javier', 'M', 'DNI', '55667788', '1984-08-21', '976543210', '976543210', '976543211', 'javier.cruz@example.com', 2),
	('Navarro', 'Suarez', 'Rosa María', 'F', 'DNI', '66778899', '1982-12-05', '987654321', '987654321', '987654322', 'rosa.navarro@example.com', 2),
	('Diaz', 'Gonzalez', 'Jorge Luis', 'M', 'CNE', '667788990011', '1981-07-30', '998765432', '998765432', '998765433', 'jorge.diaz@example.com', 3),
	('Vargas', 'Sánchez', 'Paula Beatriz', 'F', 'DNI', '77889900', '1983-09-19', '912345678', '912345678', '912345679', 'paula.vargas@example.com', 1),
	('Luna', 'Ramos', 'David Ignacio', 'M', 'DNI', '88990011', '1985-03-13', '923456789', '923456789', '923456780', 'david.luna@example.com', 2),
	('Morales', 'Valverde', 'Elena Patricia', 'F', 'DNI', '99001122', '1993-11-11', '934567890', '934567890', '934567891', 'elena.morales@example.com', 3);

SELECT * FROM client
GO

-- Actualizar un cliente
UPDATE client
SET 
    paternal_surname = 'De la cruz',
    maternal_surname = 'Gomez',
    names = 'Luis Alberto',
    sex = 'M',
    document_type = 'DNI',
    document_number = '12345678',
    birth_date = '1990-01-15',
    phone_number = '912345678',
    whatsapp_number = '912345678',
    reference_number = '912345679',
    email = 'luis.ramirez@example.com',
    admin_id = 1
WHERE
    client_id = 1;

-----------------------------------------------------------------------------------------------
---> MAESTRO PRODUCTO
INSERT INTO product (category_name, product_name, color, material, amount, price, descriptions, admin_id)
VALUES
    ('Muebles', 'Mesa de Nogal', 'Nogal', 'Madera', 4, 259.99, 'Mesa de centro moderna de madera de nogal, perfecta para sala de estar.', 2),
    ('Muebles', 'Escritorio Ejecutivo', 'Blanco', 'Melamina', 4, 349.50, 'Escritorio ejecutivo de melamina blanco con acabado resistente a rayaduras.', 3),
    ('Muebles', 'Armario Grande', 'Roble', 'Madera', 2, 599.99, 'Armario grande de madera de roble con puertas correderas, ideal para dormitorios.', 1),
    ('Decoración', 'Estantería de Pared', 'Blanco', 'Melamina', 2, 129.99, 'Estantería flotante de pared hecha de melamina blanca, con 3 compartimentos.', 2),
    ('Muebles', 'Cama Individual', 'Caoba', 'Madera', 2, 449.75, 'Cama individual de madera de caoba, resistente y con diseño clásico.', 1),
    ('Muebles', 'Mesa Redonda', 'Cerezo', 'Madera', 3, 300.00, 'Mesa redonda de madera de cerezo, ideal para salas de reuniones.', 2),
    ('Muebles', 'Escritorio Juvenil', 'Negro', 'Melamina', 5, 200.00, 'Escritorio juvenil de melamina con detalles negros y modernos.', 3),
    ('Muebles', 'Armario Doble', 'Pino', 'Madera', 3, 450.00, 'Armario doble de madera de pino, ideal para habitaciones de niños.', 1),
    ('Decoración', 'Estantería Esquinera', 'Negro', 'Metal', 4, 75.00, 'Estantería esquinera de metal negro, perfecta para decorar cualquier rincón.', 2),
    ('Muebles', 'Cama Matrimonial', 'Abedul', 'Madera', 3, 550.00, 'Cama matrimonial de madera de abedul, con un diseño elegante.', 1),
    ('Muebles', 'Mesa de Comedor', 'Roble', 'Madera', 6, 500.00, 'Mesa de comedor grande de madera de roble, para 8 personas.', 2),
    ('Muebles', 'Escritorio Pequeño', 'Gris', 'Melamina', 4, 150.00, 'Escritorio pequeño de melamina gris, ideal para oficinas.', 3),
    ('Muebles', 'Armario Triple', 'Cedro', 'Madera', 3, 800.00, 'Armario triple de madera de cedro, con puertas correderas.', 1),
    ('Decoración', 'Estantería Modular', 'Blanco', 'Melamina', 5, 100.00, 'Estantería modular de melamina blanca, configurable para diferentes espacios.', 2),
    ('Muebles', 'Cama King Size', 'Ebano', 'Madera', 1, 700.00, 'Cama king size de madera de ébano, con un diseño lujoso.', 1);

SELECT * FROM product
GO

-- Actualizar un producto
UPDATE product
SET 
	category_name = 'Muebles',
    product_name = 'Mesa de Centro',
    color = 'Nogal',
    material = 'Madera',
    amount = 4,
    price = 259.99,
    descriptions = 'Mesa de centro moderna de madera de nogal',
    admin_id = 2
WHERE
    product_id = 1; 

-----------------------------------------------------------------------------------------------
---> MAESTRO SUPPLIER
INSERT INTO supplier
	(names, addres, city, phone_number, whatsapp_number, reference_number, email, admin_id)
VALUES
	('Muebles del Norte', 'Av. Principal 123', 'Lima', '912345678', '918765432', '912345678', 'contacto@mueblesnorte.com', 3),
	('Madera & Melamina S.A.', 'Calle Secundaria 456', 'Cusco', '976543210', '976543210', '976543210', 'info@maderamelamina.com', 3),
	('Mueblería El Artesano', 'Jr. Tercera 789', 'Arequipa', '934567890', '934567890', '934567890', 'ventas@elartesano.com', 1),
	('Decoraciones y Muebles', 'Av. Cuarta 321', 'Trujillo', '945678901', '945678901', '945678901', 'contacto@decoracionesymuebles.com', 1),
	('Muebles Modernos', 'Calle Quinta 654', 'Piura', '923456789', '923456789', '923456789', 'info@mueblesmodernos.com', 2),
	('Fábrica de Muebles La Casa', 'Av. San Martín 456', 'Lima', '932165478', '932165478', '932165479', 'contacto@fmlacasa.com', 2),
	('Melamina Perú', 'Jr. Cuarta 876', 'Cusco', '965432198', '965432198', '965432199', 'ventas@melaminaperu.com', 3),
	('Diseños Elegantes', 'Av. Quinta 543', 'Arequipa', '934567890', '934567890', '934567891', 'info@diseñoselegantes.com', 1),
	('Innovamuebles', 'Calle Sexta 234', 'Piura', '945678912', '945678912', '945678913', 'contacto@innovamuebles.com', 2),
	('Muebles Rústicos', 'Av. Sétima 765', 'Trujillo', '923456789', '923456789', '923456780', 'info@mueblesrusticos.com', 1),
	('WoodArt', 'Jr. Octava 987', 'Cusco', '945678901', '945678901', '945678902', 'contacto@woodart.com', 3),
	('Mueblería Colonial', 'Av. Novena 123', 'Lima', '932165478', '932165478', '932165479', 'ventas@muebleriacolonial.com', 2),
	('Fábrica de Sofás', 'Calle Décima 876', 'Arequipa', '976543210', '976543210', '976543211', 'info@fabricadesofas.com', 1),
	('Camas & Decor', 'Av. Onceava 543', 'Piura', '923456789', '923456789', '923456780', 'ventas@camasydecor.com', 2),
	('Muebles Funcionales', 'Jr. Doceava 654', 'Trujillo', '945678901', '945678901', '945678902', 'info@mueblesfuncionales.com', 3);

SELECT * FROM supplier
GO

-- Actualizar un proveedor
UPDATE supplier
SET 
    names = 'Muebles del Norte',
    addres = 'Av. Principal 123',
    city = 'Lima',
    phone_number = '912345678',
    whatsapp_number = '918765432',
    reference_number = '912345678',
    email = 'contacto@mueblesnorte.com',
    admin_id = 1
WHERE
    supplier_id = 1; 
GO

-----------------------------------------------------------------------------------------------
---> TABLA MATERIA PRIMA
INSERT INTO raw_material (material_name, color, amount, price, description, states)
VALUES
    ('Madera', 'Madera Natural', 100, 50.00, 'Madera para fabricación de muebles', 'A'),
    ('Tela', 'Azul', 200, 30.00, 'Tela para sofás', 'A'),
    ('Cuero', 'Negro', 300, 100.00, 'Cuero para sillas', 'A');
GO

-----------------------------------------------------------------------------------------------
---> TRANSACCIONAL SALE
INSERT INTO sale (client_id, sale_date, price_total) 
VALUES
    (1, '2024-10-25', 550.00),
    (2, '2024-10-26', 300.00),
    (3, '2024-10-27', 800.00),
    (4, '2024-10-28', 450.00),
    (5, '2024-10-29', 129.99),
    (6, '2024-10-29', 700.00),
    (7, '2024-10-30', 150.00),
    (8, '2024-10-30', 259.99),
    (9, '2024-10-31', 200.00),
    (10, '2024-10-31', 450.00),
    (11, '2024-11-01', 349.50),
    (12, '2024-11-02', 500.00),
    (13, '2024-11-02', 449.75),
    (14, '2024-11-03', 129.99),
    (15, '2024-11-03', 600.00);

-- Verificamos los registros en la tabla "sale"
SELECT * FROM sale;
GO

-- Actualizar una venta
UPDATE sale
SET 
    client_id = 1,
    sale_date = '2024-10-25',
    price_total = 550.00
WHERE
    sale_id = 1; 
GO

-----------------------------------------------------------------------------------------------
---> TABLA VENTA-DETALLE
INSERT INTO sale_detail
	(sale_id, product_id, amount)
VALUES
    (1, 1, 2),
    (1, 2, 1),
    (2, 3, 3),
    (2, 4, 2),
    (3, 5, 1),
    (3, 6, 4),
    (4, 7, 2),
    (4, 8, 3),
    (5, 9, 1),
    (5, 10, 2),
    (6, 1, 3),
    (6, 2, 2),
    (7, 3, 1),
    (7, 4, 3),
    (8, 5, 2),
    (8, 6, 1),
    (9, 7, 1),
    (9, 8, 4),
    (10, 9, 2),
    (10, 10, 1);
GO

SELECT * FROM sale_detail;
GO

-----------------------------------------------------------------------------------------------
---> TABLA COMPRAS

-- Inserciones de compras en la tabla buys
INSERT INTO buys (supplier_id, invoice, total_price)
VALUES 
    (1, 'F00001', 3500.00),
    (2, 'F00002', 2700.00),
    (3, 'F00003', 4200.00);
GO

SELECT * FROM buys;
GO



-----------------------------------------------------------------------------------------------
---> TABLA COMPRA-DETALLE
INSERT INTO buys_detail (buys_id, material_name, descriptions_material, color, amount, unit_price)
VALUES 
    (1, 'Madera Roble', 'Madera de roble sólida para estructuras', 'Marrón', 15, 150.00),
    (1, 'Pintura', 'Pintura para acabado de muebles', 'Blanco', 10, 50.00),
    (1, 'Brochas', 'Brochas de cerdas suaves para aplicar barniz', 'Negro', 25, 10.00),
    (1, 'Barniz', 'Barniz protector para madera', 'Transparente', 20, 25.00),
    (2, 'Tela de Algodón', 'Tela resistente para tapicería', 'Gris', 40, 20.00),
    (2, 'Espuma Alta Densidad', 'Espuma para relleno de asientos', 'Blanco', 50, 35.00),
    (2, 'Pegamento Industrial', 'Pegamento fuerte para ensamblaje de madera', 'Blanco', 10, 15.00),
    (3, 'Bisagras', 'Bisagras de acero para puertas de muebles', 'Plateado', 60, 5.00),
    (3, 'Tornillos', 'Tornillos de acero para ensamblaje', 'Plateado', 200, 0.50),
    (3, 'Clavos de Acero', 'Clavos resistentes para estructuras', 'Plateado', 500, 0.10),
    (3, 'Ruedas para Muebles', 'Ruedas para facilitar el movimiento de muebles', 'Negro', 20, 75.00),
    (3, 'Laca Transparente', 'Laca para acabado de superficies', 'Transparente', 30, 25.00);
GO


SELECT * FROM buys
GO

-----------------------------------------------------------------------------------------------
---> TABLA FABRICACION
INSERT INTO manufacturing (product_id, manufacturing_date, total_cost)
VALUES
    (1,'2024-10-15', 50.00),
    (2,'2024-10-13', 30.00),
    (3,'2024-10-12', 90.00);

-----------------------------------------------------------------------------------------------
---> TABLA DETALLE
INSERT INTO manufacturing_detail (manufacturing_id, raw_material_id, amount, unit_price, subtotal)
VALUES
    (1, 1, 5, 10.00, 50.00),
    (2, 2, 2, 15.00, 30.00),
    (3, 3, 3, 30.00, 90.00);