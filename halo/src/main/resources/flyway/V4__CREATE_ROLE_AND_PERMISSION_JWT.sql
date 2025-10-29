CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE api_endpoints (
    id BIGINT PRIMARY KEY,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL
);

CREATE TABLE permissions (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE endpoint_permissions (
    id BIGINT PRIMARY KEY,
    endpoint_id BIGINT REFERENCES api_endpoints(id),
    permission_id BIGINT REFERENCES permissions(id)
);

CREATE TABLE acl (
    id BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    permission_id BIGINT REFERENCES permissions(id),
    granted BOOLEAN DEFAULT TRUE
);