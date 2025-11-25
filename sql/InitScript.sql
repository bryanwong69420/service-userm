CREATE TABLE mcp_refresh_token (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_id BIGINT(20) NOT NULL,
    ref_token VARCHAR(255) UNIQUE NOT NULL,
    exp_date DATETIME NOT NULL,
    is_deleted TINYINT(1) DEFAULT 0,
    updated_at DATETIME NULL,
    deleted_by BIGINT(20) NULL,
    deleted_at DATETIME NULL,
    PRIMARY KEY (id),
    INDEX idx_user_id (user_id)
)

CREATE TABLE mcp_users_role (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(255),
    is_deleted TINYINT(1) DEFAULT 0,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    deleted_by BIGINT(20),
    deleted_at DATETIME(6),
    PRIMARY KEY (id)
)

CREATE TABLE mcp_users (
    id BIGINT(20) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status TINYINT(1) DEFAULT 1,
    last_login_at DATETIME NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    is_deleted TINYINT(1) DEFAULT 0,
    deleted_at TIMESTAMP NULL
)

CREATE TABLE mcp_users_role_map (
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES mcp_users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES mcp_users_role (id) ON DELETE CASCADE
)

INSERT INTO mcp_users_role (name, description) VALUES ('ROLE_ADMIN', 'Admin Role');
INSERT INTO mcp_users_role (name, description) VALUES ('ROLE_USER', 'User Role');