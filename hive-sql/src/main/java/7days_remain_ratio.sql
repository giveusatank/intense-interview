SELECT  ROUND(fenzi_1conv/fenmu,4) 1conver_r,
        ROUND(fenzi_2conv/fenmu,4) 2conver_r,
        ROUND(fenzi_3conv/fenmu,4) 3conver_r
from
(
SELECT  COUNT(DISTINCT(aa.userId)) as fenmu,
        SUM(if( DATEDIFF(bb.dtime,aa.dtime) = 1, 1,0)) as fenzi_1conv,
        SUM(if( DATEDIFF(bb.dtime,aa.dtime) = 2, 1,0)) as fenzi_2conv,
        SUM(if( DATEDIFF(bb.dtime,aa.dtime) = 3, 1,0)) as fenzi_3conv
from
(
SELECT  dtime,
        userId
from dev.user_left_ratio
where dtime = '2021-01-01'
group by dtime,
         userId
) aa
left join
(
SELECT  dtime,
        userId
from dev.user_left_ratio
where dtime between '2021-01-02' and '2021-01-04'
group by dtime,
         userId
) bb
on aa.userId = bb.userId  ) cc