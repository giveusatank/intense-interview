SELECT  CONCAT_WS('\t',collect_set(con_r)) as content
from (
SELECT  CONCAT(aa.dtim,':',ROUND(COUNT(bb.userId) / COUNT(1) ,4 ) * 100 ,'%') as con_r
from
(
SELECT   dtime,
         userId
from dev.user_left_ratio
group by dtime,
         userId
) aa
left join
(
SELECT   dtime,
         userId
from dev.user_left_ratio
group by dtime,
         userId
) bb
on  DATE_ADD(aa.dtime,1) = bb.dtime
and aa.userId = bb.userId
group by aa.dtime ) ccc