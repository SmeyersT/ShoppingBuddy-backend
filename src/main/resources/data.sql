INSERT INTO SHOPPING_CART
VALUES (5000, NOW(), TRUE, FALSE);
INSERT INTO SHOPPING_CART
VALUES (5001, NOW(), TRUE, FALSE);
INSERT INTO SHOPPING_CART
VALUES (5002, NOW(), FALSE, FALSE);

INSERT INTO USERS
VALUES (5000, NOW(), 'Tom@Test.com', 'Tom', 'https://vignette.wikia.nocookie.net/westworld/images/5/56/Robert_Ford.jpg/revision/latest/scale-to-width-down/1000?cb=20180619173041&path-prefix=nl', 'Test', 5000);
INSERT INTO USERS
VALUES (5001, NOW(), 'Elon@Musk.com', 'Elon', 'https://upload.wikimedia.org/wikipedia/commons/e/ed/Elon_Musk_Royal_Society.jpg', 'Musk', 5001);

INSERT INTO GROUPS
VALUES (5000, 'https://1000logos.net/wp-content/uploads/2020/09/SpaceX-Logo-500x313.png', 'SpaceX', 5002);

INSERT INTO GROUP_MEMBER
VALUES (5000, 0, 5000, 5001);
