SELECT  brand,
        MIN(stt) as start,
        MAX(edt) as ends,
        MAX(sum_discount) as max_dis
from (
SELECT  brand,
        stt,
        edt,
        SUM(if(last_stt=''  , datediff(edt,stt) ,
            if(stt >= last_stt and edt <= last_cur_max_edt ,0 ,
                if(stt >= last_cur_max_edt , datediff(edt,stt) , datediff(edt,last_cur_max_edt)))))  over(PARTITION by brand order by stt ASC ) as sum_discount
from (
SELECT  brand,
        stt,
        edt,
        last_stt,
        lag(cur_max_edt,1,'') over(PARTITION by brand order by stt ASC) as last_cur_max_edt
from (
SELECT  brand,
        stt,
        edt,
        lag(stt,1,'') over(PARTITION by brand order by stt ASC) as last_stt,
        MAX(edt) over(PARTITION by brand order by stt ASC) as cur_max_edt
from dev.discount_tbl
) aa ) cc  ) bb
group by brand