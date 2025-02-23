DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;

CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      image_url VARCHAR(255),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      like_count INT,
                      tags VARCHAR(1000)
);

CREATE TABLE comment (
                         id SERIAL PRIMARY KEY,
                         post_id INT REFERENCES post(id) ON DELETE CASCADE,
                         content TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


