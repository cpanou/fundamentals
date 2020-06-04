
INSERT INTO `employees_registry`.`company`
(
`name`,
`tax_number`)
VALUES
('E-Value',
'0000666000');

INSERT INTO `employees_registry`.`department`
(
`name`,
`sector`,
`company_id`)
VALUES
(
'IT',
'TECHNOLOGY',
1);
INSERT INTO `employees_registry`.`department`
(
`name`,
`sector`,
`company_id`)
VALUES
(
'Pre-Sales',
'FINANCE',
1);

INSERT INTO `employees_registry`.`employee`
(
`username`,
`address`,
`firstname`,
`hire_date`,
`leave_date`,
`lastname`,
`phone`,
`position`,
`department_id`)
VALUES
(
'Kwstas123',
'KIFISIAS 1, 158 49',
'Kwstas',
'2004-04-12 11:04:03.713850000',
'2018-05-18 15:46:12.658956',
'Pappas',
'6900000000',
'Senior Developer',
1);
INSERT INTO `employees_registry`.`employee`
(
`username`,
`address`,
`firstname`,
`hire_date`,
`lastname`,
`phone`,
`position`,
`department_id`)
VALUES
(
'Panagiotis123',
'SMYRNIS 189, 145 49',
'Panagiotis',
'2000-01-01 00:00:00',
'Kotsolis',
'6900000000',
'Junior Developer',
1);
INSERT INTO `employees_registry`.`employee`
(
`username`,
`address`,
`firstname`,
`hire_date`,
`leave_date`,
`lastname`,
`phone`,
`position`,
`department_id`)
VALUES
(
'Aleksandros123',
'MESOGIWN 5, 131 88',
'Aleksandros',
'2018-05-18 12:46:12.658956'
'2020-05-28 09:16:43.897568',
'Polikarpos',
'6900000000',
'Junior Developer',
1);
INSERT INTO `employees_registry`.`employee`
(
`username`,
`address`,
`firstname`,
`hire_date`,
`lastname`,
`phone`,
`position`,
`department_id`)
VALUES
(
'Manwlis123',
'HPEIROU 24, 175 49',
'Manwlis',
'2010-10-05 14:00:02.563256000',
'Manolopoulos',
'6900000000',
'Solution Architect',
2);
INSERT INTO `employees_registry`.`employee`
(
`username`,
`address`,
`firstname`,
`hire_date`,
`lastname`,
`phone`,
`position`,
`department_id`)
VALUES
(
'Stelios123',
'FILOLAOU 67, 116 22',
'Stelios',
'2020-05-28 09:16:43.897568000',
'Karadimas',
'6900000000',
'Enterprise Architect',
2);
