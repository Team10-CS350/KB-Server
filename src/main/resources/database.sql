CREATE TABLE User(
    id serial PRIMARY KEY,
    name VARCHAR(256) NOT NULL
)

CREATE TABLE Event(
    id serial PRIMARY KEY,
    title VARCHAR(50), NOT NULL
    author serial references User(id)
)