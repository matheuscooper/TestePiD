-- =========================================================
-- RAW MATERIALS
-- =========================================================

INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (20, 'RM020', 'Carbon Fiber', 90);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (21, 'RM021', 'Titanium Bolt', 300);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (22, 'RM022', 'Polycarbonate Sheet', 140);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (23, 'RM023', 'LED Module', 500);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (24, 'RM024', 'Lithium Battery', 200);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (25, 'RM025', 'Motor Unit', 80);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (26, 'RM026', 'Microcontroller', 150);
INSERT INTO raw_material (id, code, name, stock_quantity) VALUES (27, 'RM027', 'Touch Screen Panel', 120);


-- =========================================================
-- PRODUCTS
-- =========================================================

INSERT INTO product (id, code, name, price) VALUES (20, 'P020', 'Electric Skateboard', 4200);
INSERT INTO product (id, code, name, price) VALUES (21, 'P021', 'Smart Helmet', 1500);
INSERT INTO product (id, code, name, price) VALUES (22, 'P022', 'Portable Projector', 2800);
INSERT INTO product (id, code, name, price) VALUES (23, 'P023', 'Smart Lamp', 750);
INSERT INTO product (id, code, name, price) VALUES (24, 'P024', 'Fitness Tracker', 900);
INSERT INTO product (id, code, name, price) VALUES (25, 'P025', 'Mini Drone', 3500);


-- =========================================================
-- PRODUCT ↔ RAW MATERIAL ASSOCIATIONS
-- =========================================================

-- Electric Skateboard
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (20, 20, 4);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (20, 24, 2);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (20, 25, 1);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (20, 21, 6);


-- Smart Helmet
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (21, 22, 3);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (21, 23, 4);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (21, 26, 1);


-- Portable Projector
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (22, 23, 6);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (22, 26, 2);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (22, 24, 1);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (22, 22, 2);


-- Smart Lamp
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (23, 23, 3);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (23, 26, 1);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (23, 21, 2);


-- Fitness Tracker
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (24, 27, 1);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (24, 24, 1);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (24, 26, 1);


-- Mini Drone
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (25, 20, 2);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (25, 25, 4);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (25, 24, 2);
INSERT INTO product_raw_material (product_id, raw_material_id, required_quantity) VALUES (25, 26, 1);