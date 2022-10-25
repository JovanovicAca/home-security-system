-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Lozinka za sva 3 user-a je 1234

INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_OWNER');
INSERT INTO role (name) VALUES ('ROLE_TENANT');

INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('admin', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Marko', 'Markovic',
        'admin@example.com', 1, false, false);
INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('owner', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Nikola', 'Nikolic',
        'owner@example.com', 2, false, false);
INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('tenant', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Milica', 'Mirkovic',
        'tenant@example.com', 3, false, false);

INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('owner2', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Nikola', 'Nikolic',
        'owner2@example.com', 2, false, false);
INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('tenant2', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Marija', 'Popadic',
        'tenant2@example.com', 3, false, false);

INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('owner3', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Veljko', 'Ilic',
        'owner3@example.com', 2, false, false);
INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('tenant3', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Misa', 'Neskovic',
        'tenant3@example.com', 3, false, false);

INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('owner4', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Kristina', 'Petrovic',
        'owner4@example.com', 2, false, false);
INSERT INTO users (username, password, first_name, last_name, email, role_id, account_locked, deleted)
VALUES ('tenant4', '$2a$10$VPwDVhCe69G71DmDg9iMXe3hXcS.YvhdkGf3f3GG93buXE60M73bG', 'Dusan', 'Popovic',
        'tenant4@example.com', 3, false, false);

INSERT INTO real_estate (name, deleted) VALUES ('Stan 1', false);
INSERT INTO real_estate (name, deleted) VALUES ('Stan 2', false);
INSERT INTO real_estate (name, deleted) VALUES ('Kuca 1', false);
INSERT INTO real_estate (name, deleted) VALUES ('Stan 3', false);

INSERT INTO users_real_estate (real_estate_id, user_id) VALUES (1, 2);
INSERT INTO users_real_estate (real_estate_id, user_id) VALUES (2, 2);
INSERT INTO users_real_estate (real_estate_id, user_id) VALUES (1, 1);
INSERT INTO users_real_estate (real_estate_id, user_id) VALUES (2, 1);
INSERT INTO users_real_estate (real_estate_id, user_id) VALUES (3, 1);
INSERT INTO users_real_estate (real_estate_id, user_id) VALUES (4, 1);
