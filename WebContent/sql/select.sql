SELECT p.title, u.user_name, p.start_date 
FROM projects p inner join users u on p.author_id = u.id;

SELECT p.title, u.user_name, p.start_date, datediff(date_add(p.start_date,interval p.duration day),curdate()) 
FROM projects p inner join users u on p.author_id = u.id 
ORDER BY datediff(date_add(p.start_date,interval p.duration day),curdate()) ASC;

SELECT p.title, u.user_name, p.start_date, DATEDIFF( DATE_ADD( p.start_date, INTERVAL p.duration
DAY ) , CURDATE( ) ) 
FROM projects p
INNER JOIN users u ON p.author_id = u.id
WHERE DATEDIFF( DATE_ADD( p.start_date, INTERVAL p.duration
DAY ) , CURDATE( ) ) >=0
ORDER BY DATEDIFF( DATE_ADD( p.start_date, INTERVAL p.duration
DAY ) , CURDATE( ) ) ASC;

SELECT amount, description
FROM rewards
WHERE project_id =1
ORDER BY amount;

SELECT u.user_name
FROM pledges pl
INNER JOIN projects prj ON pl.project_id = prj.id
INNER JOIN users u ON pl.user_id = u.id
WHERE prj.title =  'SHOW ME THE MONEY'

SELECT u.user_name
FROM pledges pl
INNER JOIN projects prj ON pl.project_id = prj.id
INNER JOIN users u ON pl.user_id = u.id
WHERE prj.title =  'SHOW ME THE MONEY'
AND pl.reward_amount >= 1

SELECT SUM( pl.pledeged_amount ) , CONCAT( (
SUM( pl.pledeged_amount ) / prj.target ) *100,  '%'
)
FROM pledges pl
INNER JOIN projects prj ON pl.project_id = prj.id
WHERE prj.title =  'SHOW ME THE MONEY'
