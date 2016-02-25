SHOW DATABASES;
CREATE DATABASE shopping_cart;
USE shopping_cart;

CREATE TABLE IF NOT EXISTS User (
  username    VARCHAR(20) NOT NULL,
  password    VARCHAR(20) NOT NULL,
  firstName   VARCHAR(20),
  lastName    VARCHAR(20),
  address     VARCHAR(100),
  phoneNumber VARCHAR(100),
  isLoggedin  BOOLEAN,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS Product (
  productId    INT         NOT NULL,
  productName  VARCHAR(30) NOT NULL,
  sellingPrice FLOAT,
  cost         FLOAT,
  quantity     INT,
  imagePath    VARCHAR(100),
  catagory     VARCHAR(30),
  username     VARCHAR(20) NOT NULL,
  sold         BOOLEAN,
  PRIMARY KEY (productID),
  FOREIGN KEY (username) REFERENCES User (username)
);

CREATE TABLE IF NOT EXISTS ShoppingCartItem (
  username         VARCHAR(20) NOT NULL,
  productId        INT         NOT NULL,
  quantitySelected INT         NOT NULL,
  totalPrice       FLOAT       NOT NULL,
  PRIMARY KEY (username, productId),
  FOREIGN KEY (username) REFERENCES User (username),
  FOREIGN KEY (productId) REFERENCES Product (productId)
);

CREATE TABLE IF NOT EXISTS SoldItem (
  transactionId    INT         NOT NULL AUTO_INCREMENT,
  username         VARCHAR(20) NOT NULL,
  buyername        VARCHAR(20) NOT NULL,
  productId        INT         NOT NULL,
  quantitySelected INT,
  totalPrice       FLOAT,
  date             DATE,
  PRIMARY KEY (transactionId),
  FOREIGN KEY (username) REFERENCES User (username),
  FOREIGN KEY (productId) REFERENCES Product (productId),
  FOREIGN KEY (buyername) REFERENCES User (username)
);

INSERT INTO User (username, password, firstName, lastName, address, phoneNumber, isLoggedin)
VALUES ('ghuang2013', '232323', 'Guan', 'Huang', 'FAU', '321-230-2657', TRUE);

INSERT INTO User (username, password, firstName, lastName, address, phoneNumber, isLoggedin)
VALUES ('michael2013', '232323', 'Michael', 'Perez', 'FAU', '333-222-111', FALSE);

SELECT *
FROM User;

INSERT INTO User VALUES ('matthew33', '333', 'Matthew', 'Sleigh', 'FAU', '123-456-789', FALSE);

SELECT *
FROM User
WHERE username = 'ghuang2013'
      AND password = '232323'
      AND firstname = 'guan';

