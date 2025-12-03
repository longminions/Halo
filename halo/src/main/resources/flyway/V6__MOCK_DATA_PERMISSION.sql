
INSERT INTO users (id, username, email, password, enabled) VALUES
(1, 'admin', 'admin@example.com', 'hashed_pass_1', TRUE),
(2, 'manager', 'manager@example.com', 'hashed_pass_2', TRUE),
(3, 'staff', 'staff@example.com', 'hashed_pass_3', TRUE),
(4, 'guest', 'guest@example.com', 'hashed_pass_4', TRUE);


INSERT INTO role (id, name, description) VALUES
(1, 'ADMIN', 'System administrator with full access'),
(2, 'MANAGER', 'Manager with elevated privileges'),
(3, 'STAFF', 'Standard staff account'),
(4, 'GUEST', 'Limited guest access');


INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO permission (id, name, category, description) VALUES
(1, 'USER_READ', 'USER', 'Read user data'),
(2, 'USER_WRITE', 'USER', 'Modify user data'),
(3, 'ROLE_READ', 'ROLE', 'Read role data'),
(4, 'ROLE_WRITE', 'ROLE', 'Modify role data'),
(5, 'ENDPOINT_READ', 'ENDPOINT', 'View API endpoint'),
(6, 'ENDPOINT_WRITE', 'ENDPOINT', 'Modify API endpoint'),
(7, 'SYSTEM_CONFIG', 'SYSTEM', 'Modify system configuration');

INSERT INTO role_permission (role_id, permission_id) VALUES
-- ADMIN
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),

(2, 1), (2, 3), (2, 5),

-- STAFF
(3, 1), (3, 5),

-- GUEST
(4, 5);

