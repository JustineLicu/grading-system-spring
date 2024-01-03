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

INSERT INTO student (
  id, 
  student_number,
  first_name,
  middle_name,
  last_name,
  name_suffix,
  email,
  contact_number,
  course
) VALUES (
  (select next value for student_seq), -- id
  '2020108', -- student_number
  'Juan', -- first_name
  '', -- middle_name
  'Dela Cruz', -- last_name
  'Jr.', -- name_suffix
  'juan.delacruzjr@cvsu.edu.ph', -- email
  '09223456790', -- contact_number
  'BSIT' -- course
), (
  (select next value for student_seq), -- id
  '2020109', -- student_number
  'John', -- first_name
  '', -- middle_name
  'Doe', -- last_name
  '', -- name_suffix
  'john.doe@cvsu.edu.ph', -- email
  '09223456791', -- contact_number
  'BSCS' -- course
), (
  (select next value for student_seq), -- id
  '2020110', -- student_number
  'Jane', -- first_name
  '', -- middle_name
  'Smith', -- last_name
  '', -- name_suffix
  'jane.smith@cvsu.edu.ph', -- email
  '09223456792', -- contact_number
  'BSHM' -- course
);
