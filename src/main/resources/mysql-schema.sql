SET FOREIGN_KEY_CHECKS=0;
delete from matches;
delete from rounds;
delete from participants;
delete from leagues;
INSERT INTO `leagues` (`id`, `current_round_id`, `max_participants`, `total_participants`, `status`, `the_champion_id`) VALUES
(1, NULL, 12, 20, 'NEW', NULL);
INSERT INTO `participants` (`id`, `league_id`, `first_name`, `last_name`, `email`, `phone_number`, `age`) VALUES
(2, 1, 'Mohamed', 'Mahdy', 'mohamed_abd_el_moaty@yahoo.com', '01124954957', 29),
(3, 1, 'Mohamed', 'Fathy', 'mohamed_abd_el_moaty1@yahoo.com', '01124954958', 28),
(4, 1, 'Ahmed', 'Ibrahim', 'mohamed_abd_el_moaty2@yahoo.com', '01124954959', 27),
(5, 1, 'Ibrahim', 'Ahmed', 'mohamed_abd_el_moaty3@yahoo.com', '01124954910', 27),
(6, 1, 'Mohsen', 'Mohamed', 'mohamed_abd_el_moaty4@yahoo.com', '01124954911', 27),
(7, 1, 'Mohamed', 'Mohsen', 'mohamed_abd_el_moaty5@yahoo.com', '01124954912', 27),
(8, 1, 'Mahdy', 'Mohamed', 'mohamed_abd_el_moaty6@yahoo.com', '01124954913', 27),
(9, 1, 'Fathy', 'Mohamed', 'mohamed_abd_el_moaty7@yahoo.com', '01124954914', 28),
(10, 1, 'Ayman', 'Mohamed', 'mohamed_abd_el_moaty8@yahoo.com', '01124954915', 28),
(11, 1, 'Mohamed', 'Ayman', 'mohamedsniver@gmail.com', '01124954916', 28),
(12, 1, 'Yousef', 'Hassan', 'mohamed_abd_el_moaty10@yahoo.com', '01124954917', 28),
(13, 1, 'Hassan', 'Yousef', 'mohamed_abd_el_moaty11@yahoo.com', '01124954918', 28),
(14, 1, 'Zidan', 'Mohamed', 'mohamed_abd_el_moaty12@yahoo.com', '01124954919', 28),
(15, 1, 'Mohamed', 'Zidan', 'mohamed_abd_el_moaty13@yahoo.com', '01124954920', 28),
(16, 1, 'Hussien', 'Mohamed', 'mohamed_abd_el_moaty14@yahoo.com', '01124954921', 28),
(17, 1, 'Mohamed', 'Hussien', 'mohamed_abd_el_moaty15@yahoo.com', '01124954922', 28),
(18, 1, 'Ahmed', 'Mohamed', 'mohamed_abd_el_moaty16@yahoo.com', '01124954923', 29),
(19, 1, 'Mohamed', 'Ahmed', 'mohamed_abd_el_moaty17@yahoo.com', '01124954924', 29),
(20, 1, 'Mohamed', 'Samir', 'mohamed_abd_el_moaty18@yahoo.com', '01124954925', 29),
(21, 1, 'test', 'test', 'test@yahoo.com', '01234567891', 29);
SET FOREIGN_KEY_CHECKS=1;