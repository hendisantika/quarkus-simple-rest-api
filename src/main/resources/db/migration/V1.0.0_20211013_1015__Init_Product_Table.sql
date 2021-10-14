CREATE TABLE product
(
    id          serial NOT NULL,
    name        varchar(255),
    description varchar(255),
    PRIMARY KEY (id)
);

CREATE SEQUENCE product_id_seq START WITH 6 INCREMENT BY 1;
