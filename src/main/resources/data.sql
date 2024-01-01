INSERT INTO users (
  id,
  id_number,
  first_name,
  middle_name,
  last_name,
  name_suffix,
  email,
  username,
  password,
  contact_number,
  department,
  enabled,
  role,
  deleted_on
) VALUES (
  (select next value for users_seq), -- id
  '1', -- id_number
  'Admin', -- first_name
  '', -- middle_name
  'Admin', -- last_name
  '', -- name_suffix
  'admin@example.com', -- email
  'admin', -- username
  '$2a$10$4R4g3tSFexlinUwYUXr.P.v4yA0xeUBwpF3XRycnNo/386sRGN3Qe', -- password (bcrypt hashed: 'password')
  '09223456789', -- contact_number
  'Department of Computer Studies', -- department
  TRUE, -- enabled
  'ADMIN', -- role
  '' -- deleted_on
);
