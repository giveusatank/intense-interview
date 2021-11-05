SELECT * from (
SELECT  city,
        userid,
        price,
        row_number() over( PARTITION by city order by price DESC ) rank
from  dev.top3intop  aa1
left semi join
(
SELECT  city,
        SUM(price) as cnt
from  dev.top3intop
group by city
order by cnt DESC
limit 3
) aa2
on aa1.city = aa2.city
) bbb where bbb.rank <= 3