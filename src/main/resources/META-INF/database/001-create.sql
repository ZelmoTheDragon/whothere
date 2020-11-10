-- ====================
-- DATABASE SCRIPT
-- H2 Database
-- 001 Create 
-- ====================

CREATE TABLE absence 
(
    id VARCHAR(36) NOT NULL,
    version bigint NOT NULL,
    end_date TIMESTAMP NOT NULL,
    start_date TIMESTAMP NOT NULL,
    agent_id VARCHAR(36) NOT NULL,
    state_of_absence_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id)
);
 
    
CREATE TABLE account 
(
    id VARCHAR(36) NOT NULL,
    version bigint NOT NULL,
    locked BOOLEAN NOT NULL,
    password VARCHAR(255),
    username VARCHAR(255),
    PRIMARY KEY (id)
);
 
    
CREATE TABLE account_role 
(
    account_id VARCHAR(36) NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id, role_name)
);
 
    
CREATE TABLE agent 
(
    id VARCHAR(36) NOT NULL,
    version bigint NOT NULL,
    email VARCHAR(255) NOT NULL,
    family_name VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    given_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    rank VARCHAR(255) NOT NULL,
    short_rank VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
 
    
CREATE TABLE organization 
(
    id VARCHAR(36) NOT NULL,
    version bigint NOT NULL,
    code VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    organization_parent_id VARCHAR(36),
    PRIMARY KEY (id)
);
 
    
CREATE TABLE state_of_absence 
(
    id VARCHAR(36) NOT NULL,
    version bigint NOT NULL,
    available BOOLEAN NOT NULL,
    code VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
); 
    
ALTER TABLE absence 
    ADD CONSTRAINT UK34m3qucjyhyx7n11v3uv4jpai 
    UNIQUE (agent_id, state_of_absence_id, start_date, end_date);
 
    
ALTER TABLE account 
    ADD CONSTRAINT UK_gex1lmaqpg0ir5g1f5eftyaa1 
    UNIQUE (username);
 
    
ALTER TABLE agent 
    ADD CONSTRAINT UK_pxogqxl64ae07j2lox1tgvrlx 
    UNIQUE (email);
 
    
ALTER TABLE organization 
    ADD CONSTRAINT UK_27tbqbmjb9kf4al49ojliavjt 
    UNIQUE (code);
 
    
ALTER TABLE state_of_absence 
    ADD CONSTRAINT UK_5axa1ilukg7ag1pm39w1fv1hi 
    UNIQUE (code);
 
    
ALTER TABLE absence 
    ADD CONSTRAINT FKrsq0srukhi6tcl9bd7g13gfna 
    FOREIGN KEY (agent_id) 
    REFERENCES agent;
 
    
ALTER TABLE absence 
    ADD CONSTRAINT FKhlmtsc9axcniimwg21vdrcc33 
    FOREIGN KEY (state_of_absence_id) 
    REFERENCES state_of_absence;
 
    
ALTER TABLE account_role 
    ADD CONSTRAINT FK1f8y4iy71kb1arff79s71j0dh 
    FOREIGN KEY (account_id) 
    REFERENCES account;
 
    
ALTER TABLE organization 
    ADD CONSTRAINT FK638mq547uy8hpgkw6go72ck89 
    FOREIGN KEY (organization_parent_id) 
    REFERENCES organization;