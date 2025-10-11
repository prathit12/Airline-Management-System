-- Insert default roles
INSERT INTO roles (name, description) VALUES 
    ('ADMIN', 'System Administrator with full access'),
    ('MANAGER', 'Manager with administrative access to bookings and flights'),
    ('AGENT', 'Booking agent with limited access'),
    ('CUSTOMER', 'Regular customer with basic access')
ON DUPLICATE KEY UPDATE description = VALUES(description);

-- Insert default permissions
INSERT INTO permissions (name, resource, action, description) VALUES 
    -- User management
    ('USER_READ', 'user', 'read', 'View user information'),
    ('USER_WRITE', 'user', 'write', 'Create and modify users'),
    ('USER_DELETE', 'user', 'delete', 'Delete users'),
    
    -- Flight management
    ('FLIGHT_READ', 'flight', 'read', 'View flight information'),
    ('FLIGHT_WRITE', 'flight', 'write', 'Create and modify flights'),
    ('FLIGHT_DELETE', 'flight', 'delete', 'Delete flights'),
    
    -- Booking management
    ('BOOKING_READ', 'booking', 'read', 'View booking information'),
    ('BOOKING_WRITE', 'booking', 'write', 'Create and modify bookings'),
    ('BOOKING_DELETE', 'booking', 'delete', 'Delete bookings'),
    ('BOOKING_READ_ALL', 'booking', 'read_all', 'View all bookings'),
    
    -- Customer management
    ('CUSTOMER_READ', 'customer', 'read', 'View customer information'),
    ('CUSTOMER_WRITE', 'customer', 'write', 'Create and modify customers'),
    ('CUSTOMER_DELETE', 'customer', 'delete', 'Delete customers'),
    ('CUSTOMER_READ_ALL', 'customer', 'read_all', 'View all customers'),
    
    -- Payment management
    ('PAYMENT_READ', 'payment', 'read', 'View payment information'),
    ('PAYMENT_WRITE', 'payment', 'write', 'Process payments'),
    ('PAYMENT_READ_ALL', 'payment', 'read_all', 'View all payments'),
    
    -- Airport management
    ('AIRPORT_READ', 'airport', 'read', 'View airport information'),
    ('AIRPORT_WRITE', 'airport', 'write', 'Create and modify airports'),
    ('AIRPORT_DELETE', 'airport', 'delete', 'Delete airports'),
    
    -- System administration
    ('SYSTEM_ADMIN', 'system', 'admin', 'System administration access')
ON DUPLICATE KEY UPDATE description = VALUES(description);

-- Assign permissions to roles
-- ADMIN role gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'ADMIN'
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

-- MANAGER role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'MANAGER'
AND p.name IN (
    'USER_READ', 'FLIGHT_READ', 'FLIGHT_WRITE', 'BOOKING_READ', 'BOOKING_WRITE', 'BOOKING_READ_ALL',
    'CUSTOMER_READ', 'CUSTOMER_WRITE', 'CUSTOMER_READ_ALL', 'PAYMENT_READ', 'PAYMENT_READ_ALL',
    'AIRPORT_READ', 'AIRPORT_WRITE'
)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

-- AGENT role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'AGENT'
AND p.name IN (
    'FLIGHT_READ', 'BOOKING_READ', 'BOOKING_WRITE', 'CUSTOMER_READ', 'CUSTOMER_WRITE',
    'PAYMENT_READ', 'PAYMENT_WRITE', 'AIRPORT_READ'
)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);

-- CUSTOMER role permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'CUSTOMER'
AND p.name IN (
    'FLIGHT_READ', 'BOOKING_READ', 'BOOKING_WRITE', 'CUSTOMER_READ', 'PAYMENT_READ', 'PAYMENT_WRITE', 'AIRPORT_READ'
)
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id);