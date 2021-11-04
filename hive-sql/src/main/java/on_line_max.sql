SELECT  MAX(aggr_uv)
from (
SELECT  SUM(if(flag='login',1,-1)) over(order by dtime ASC) as aggr_uv
from (
SELECT  id,
        'login' as flag,
        stt as dtime
from dev.live_tbl

UNION ALL

SELECT  id,
        'logout' as flag,
        edt
from dev.live_tbl
) aaa ) bbb