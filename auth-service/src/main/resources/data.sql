-- Ensure the 'users' table exists matching your JPA Entity layout
CREATE TABLE IF NOT EXISTS users (
                                     user_id UUID PRIMARY KEY,
                                     user_email VARCHAR(255) UNIQUE NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_role VARCHAR(50) NOT NULL
    );

-- Insert the admin user safely if they don't already exist
INSERT INTO users (user_id, user_email, user_password, user_role)
SELECT '223e4567-e89b-12d3-a456-426614174006',
       'testuser@test.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu',
       'ADMIN'
    WHERE NOT EXISTS (
    SELECT 1
    FROM users
    WHERE user_id = '223e4567-e89b-12d3-a456-426614174006'
       OR user_email = 'testuser@test.com'
);