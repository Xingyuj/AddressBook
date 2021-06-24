INSERT INTO sys_user values (101, 'Tusk', 'Elon', '$2a$10$Ac0bKo5Jizqys.CvUxoMzO18D6ySu0sPcPBW6B7QsRC4GLTLbdUKC', 'admin');
INSERT INTO address_book values (201, 101);
INSERT INTO address_book_customer values (201, 'Donald', '0491234567');
INSERT INTO address_book_customer values (201, 'Trump', '0492222337');
INSERT INTO sys_user_address_books values (101, 201);
