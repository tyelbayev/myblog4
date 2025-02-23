INSERT INTO post (title, content, image_url, like_count, tags, created_at) VALUES
                                                                               ('Пост1', 'Контенттт1', 'image1.jpg', 5, 'java,tag', CURRENT_TIMESTAMP),
                                                                               ('Пост2', 'Контенттт2', 'image2.jpg', 10, 'tag,tag2', CURRENT_TIMESTAMP);

INSERT INTO comment (post_id, content, created_at, updated_at) VALUES
                                                                   (1, 'я первый', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                   (1, 'второй', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                   (2, 'фывфывфв', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
