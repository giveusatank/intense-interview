--solution1
SELECT  username,
        MAX(cnt)
from (
SELECT  username,
         flag,
         COUNT(1) as cnt
from (
SELECT  username,
        dtime,
        last_dtime,
        SUM(if(diff in (1,2), 0,1))  over( partition by username order by dtime ASC )  as flag
FROM
(
	SELECT
		username,
		dtime,
		last_dtime,
		IF(last_dtime = '9999-99-99', 1, DATEDIFF(dtime, last_dtime)) AS diff
	FROM
		(
			SELECT
				username,
				dtime,
				lag(dtime, 1, '9999-99-99') over(partition BY username order by dtime ASC) AS last_dtime
			FROM
				(
					SELECT
						username,
						from_unixtime(CAST(CAST(ts AS BIGINT) / 1000 AS BIGINT), 'yyyy-MM-dd') AS dtime
					FROM
						dev.user_login
					GROUP BY
						username,
						from_unixtime(CAST(CAST(ts AS BIGINT) / 1000 AS BIGINT), 'yyyy-MM-dd')
				)
				aaa1
		)
		aaa2
) aaa3  ) aaa4
group by username,
         flag
) bbb
group by username


--solution2
SELECT  username,
        MAX(cnt)
from (
SELECT  username,
        DATE_SUB(dtime,drank),
        COUNT(1) as cnt
from
(
SELECT  username,
        dtime,
        row_number() over(PARTITION BY username ORDER BY dtime ASC ) as drank
FROM
(
	SELECT
		username,
		from_unixtime(CAST(CAST(ts AS BIGINT) / 1000 AS BIGINT), 'yyyy-MM-dd') AS dtime
	FROM
		dev.user_login
	GROUP BY
		username,
		from_unixtime(CAST(CAST(ts AS BIGINT) / 1000 AS BIGINT), 'yyyy-MM-dd')
)  aaa1  ) aaa2
group by username,
         DATE_SUB(dtime,drank) ) aaa3
group by username