-- Create default admin user
-- Password: admin123 (encoded with BCrypt)
INSERT INTO users (username, email, password_hash, first_name, last_name, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'admin',
    'admin@airline.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'System',
    'Administrator',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON DUPLICATE KEY UPDATE 
    email = VALUES(email),
    password_hash = VALUES(password_hash),
    first_name = VALUES(first_name),
    last_name = VALUES(last_name);

-- Assign ADMIN role to the default admin user
INSERT INTO user_roles (user_id, role_id, assigned_at)
SELECT u.id, r.id, CURRENT_TIMESTAMP
FROM users u
CROSS JOIN roles r
WHERE u.username = 'admin' AND r.name = 'ADMIN'
ON DUPLICATE KEY UPDATE assigned_at = VALUES(assigned_at);

-- Create default manager user
-- Password: manager123 (encoded with BCrypt)
INSERT INTO users (username, email, password_hash, first_name, last_name, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'manager',
    'manager@airline.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'Flight',
    'Manager',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON DUPLICATE KEY UPDATE 
    email = VALUES(email),
    password_hash = VALUES(password_hash),
    first_name = VALUES(first_name),
    last_name = VALUES(last_name);

-- Assign MANAGER role to the default manager user
INSERT INTO user_roles (user_id, role_id, assigned_at)
SELECT u.id, r.id, CURRENT_TIMESTAMP
FROM users u
CROSS JOIN roles r
WHERE u.username = 'manager' AND r.name = 'MANAGER'
ON DUPLICATE KEY UPDATE assigned_at = VALUES(assigned_at);

-- Create default agent user
-- Password: agent123 (encoded with BCrypt)
INSERT INTO users (username, email, password_hash, first_name, last_name, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'agent',
    'agent@airline.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'Booking',
    'Agent',
    true,
    true,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON DUPLICATE KEY UPDATE 
    email = VALUES(email),
    password_hash = VALUES(password_hash),
    first_name = VALUES(first_name),
    last_name = VALUES(last_name);

-- Assign AGENT role to the default agent user
INSERT INTO user_roles (user_id, role_id, assigned_at)
SELECT u.id, r.id, CURRENT_TIMESTAMP
FROM users u
CROSS JOIN roles r
WHERE u.username = 'agent' AND r.name = 'AGENT'
ON DUPLICATE KEY UPDATE assigned_at = VALUES(assigned_at);