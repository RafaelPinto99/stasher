CREATE TABLE user_account (
    id UUID PRIMARY KEY,
    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE UNIQUE INDEX idx_user_account_email ON user_account (LOWER(email));

CREATE TABLE company (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) UNIQUE,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE UNIQUE INDEX idx_company_name ON company (LOWER(name));

CREATE TABLE job_application (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES user_account(id) ON DELETE CASCADE,
    company_id UUID NOT NULL REFERENCES company(id),
    role VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    status VARCHAR(20) NOT NULL CHECK (status IN ('SAVED', 'APPLIED', 'INTERVIEWING', 'OFFER', 'REJECTED')),
    applied_date DATE,
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    UNIQUE(user_id, url)
);

CREATE INDEX idx_job_application_company_id ON job_application (company_id);

CREATE TABLE refresh_token (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES user_account(id) ON DELETE CASCADE,
    token_hash VARCHAR(64) NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    revoked_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_refresh_token_user_id ON refresh_token (user_id);