INSERT INTO charity_donation.institutions (description, name) VALUES ('Pomoc dzieciom z ubogich rodzin.', 'Dbam o Zdrowie' ), ('Pomoc wybudzaniu dzieci ze śpiączki.', 'A kogo'), ('Pomoc osobom znajdującym się w trudnej sytuacji życiowej.', 'Dla dzieci'), ('Pomoc dla osób nie posiadających miejsca zamieszkania', 'Bez domu'), ('aaa', 'Fundacja jakoś');
INSERT INTO charity_donation.categories (name) values ('Ubrania, które nadają się do ponownego użycia'), ('Ubrania, do wyrzucenia'), ('Zabawki'), ('Książki'), ('Inne');
INSERT INTO charity_donation.users (active_account, email, name, password) VALUES (1, 'user@charityApp.pl', 'Jan Nowak', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'), (1, 'admin@charityApp.pl', 'Marian Nowakowski', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW');
INSERT INTO charity_donation.roles (name) values ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO charity_donation.user_roles (user_id, role_id) VALUES (1,1), (2, 2);
INSERT INTO charity_donation.donations(city, phone, pick_up_comment, pick_up_date, pick_up_time, quantity, status, street, zip_code, institution_id, user_id)
VALUES
    ('New York', '123456789', 'Please handle with care', '2023-06-05', '10:00:00', '3', 'PENDING', '123 Main St', '10001', 1, 1),
    ('Los Angeles', '987654321', 'Fragile items inside', '2023-06-06', '14:30:00', '2', 'COLLECTED', '456 Elm St', '90001', 2, 1),
    ('Chicago', '555555555', 'Handle with caution', '2023-06-07', '11:45:00', '1', 'PENDING', '789 Oak St', '60601', 3, 1),
    ('Houston', '999999999', 'Urgent pickup required', '2023-06-08', '16:15:00', '4', 'PENDING', '321 Pine St', '77001', 4, 1),
    ('Miami', '111111111', 'Delicate items inside', '2023-06-09', '09:30:00', '2', 'COLLECTED', '654 Cedar St', '33101', 5, 1);
INSERT INTO charity_donation.donation_category (donation_id, category_id) VALUES (1, 2),(2,1), (3,3), (4,2), (5,4);
