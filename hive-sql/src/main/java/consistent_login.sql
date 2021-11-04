SELECT username
FROM
	(
		SELECT
			aaa5.username
		FROM
			(
				SELECT
					aaa4.username,
					aaa4.flag,
					COUNT(1) AS cnt
				FROM
					(
						SELECT
							username,
							dtime,
							last_dtime,
							SUM(IF(diff in (1,2), 0, 1)) over(partition BY username order by dtime ASC) AS flag
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
													from_unixtime( CAST(CAST(ts AS BIGINT) / 1000 as BIGINT), 'yyyy-MM-dd') AS dtime
												FROM
													dev.user_login
												GROUP BY
													username,
													from_unixtime( CAST(CAST(ts AS BIGINT) / 1000 as BIGINT), 'yyyy-MM-dd')
											)
											aaa1
									)
									aaa2
							)
							aaa3
					)
					aaa4
				GROUP BY
					aaa4.username,
					aaa4.flag
				HAVING
					COUNT(1) >= 3
			)
			aaa5
	) aaa6
GROUP BY username