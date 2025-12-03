-- DROP theo đúng thứ tự phụ thuộc
DROP TABLE IF EXISTS acl_override CASCADE;
DROP TABLE IF EXISTS endpoint_permission CASCADE;
DROP TABLE IF EXISTS endpoint CASCADE;
DROP TABLE IF EXISTS role_permission CASCADE;
DROP TABLE IF EXISTS permission CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS users CASCADE;

---------------------------------------------------------
-- USERS
---------------------------------------------------------
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

---------------------------------------------------------
-- ROLE
---------------------------------------------------------
CREATE TABLE role (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

---------------------------------------------------------
-- USER_ROLE (Many-to-Many)
---------------------------------------------------------
CREATE TABLE user_role (
    user_id   BIGINT NOT NULL,
    role_id   BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id)  ON DELETE CASCADE
);

---------------------------------------------------------
-- PERMISSION
---------------------------------------------------------
CREATE TABLE permission (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    category VARCHAR(100),
    description TEXT
);

---------------------------------------------------------
-- ROLE_PERMISSION (Many-to-Many)
---------------------------------------------------------
CREATE TABLE role_permission (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);

---------------------------------------------------------
-- ENDPOINT
---------------------------------------------------------
CREATE TABLE endpoint (
    id BIGINT PRIMARY KEY,
    path VARCHAR(300) NOT NULL,
    http_method VARCHAR(10) NOT NULL,
    module VARCHAR(100),
    service VARCHAR(100)
);

---------------------------------------------------------
-- ENDPOINT_PERMISSION (Many-to-Many)
---------------------------------------------------------
CREATE TABLE endpoint_permission (
    endpoint_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (endpoint_id, permission_id),
    FOREIGN KEY (endpoint_id) REFERENCES endpoint(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);

---------------------------------------------------------
-- ACL_OVERRIDE (Override quyền theo user)
---------------------------------------------------------
CREATE TABLE acl_override (
    user_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    granted BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);
