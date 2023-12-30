CREATE TABLE product
(
    product_id    INT AUTO_INCREMENT PRIMARY KEY,
    product_name  VARCHAR(100) NOT NULL,
    product_price DECIMAL(10, 2) NOT NULL,
    product_state INT DEFAULT 1
);

CREATE TABLE orders
(
    order_id    INT AUTO_INCREMENT PRIMARY KEY,
    order_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_price DECIMAL(10, 2) NOT NULL,
    orders_state INT DEFAULT 1
);

CREATE TABLE order_product
(
    order_id   INT,
    product_id INT,
    quantity   INT,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    state INT DEFAULT 1
);
