INSERT INTO posts (
    number_post,
    title,
    author,
    password,
    content,
    views,
    createdAt,
    updatedAt,
    deletedAt
)
VALUES

(
    1,
    'the 12 month member programme',
    'alex',
    'alex123',
    'Content for the 12 month member programme',
    2,
    '2026-04-22 13:49:52.345341',
    NULL,
    NULL
),

(
    2,
    'Cara implement JWT di Spring Boot?',
    'wulandari',
    'wulandari123',
    'Discussion about JWT implementation in Spring Boot',
    0,
    '2026-04-22 13:55:11.059575',
    NULL,
    NULL
),

(
    3,
    'Deleted Post',
    'system',
    'system123',
    'This post is deleted',
    0,
    '2026-04-22 14:00:00',
    NULL,
    '2026-04-22 14:10:00'
),

(
    4,
    'Workshop Backend Developer',
    'admin',
    'admin123',
    'Backend workshop event details',
    0,
    '2026-04-22 14:05:21.563009',
    NULL,
    NULL
),

(
    5,
    'Another Deleted Post',
    'system',
    'system123',
    'Deleted example',
    0,
    '2026-04-22 14:06:00',
    NULL,
    '2026-04-22 14:10:00'
),


(
    6,
    'Introduce Yourself!',
    'mariana',
    'mariana123',
    'Introduce yourself to the community',
    2,
    '2026-04-22 14:06:29.750223',
    '2026-04-22 20:43:01.527433',
    NULL
);