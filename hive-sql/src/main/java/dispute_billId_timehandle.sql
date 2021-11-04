SELECT bill_id,first_judge_tm,first_judge_erp,b_fisrt_judge_tm_last_proof_tm,b_fisrt_judge_tm_last_proof_erp
from
(
SELECT  bill_id,
        erp,
        operate_type,
        first_judge_tm,
        first_judge_erp,
        first_value(if(operate_type='proof' and create_time < first_judge_tm ,create_time,'')) over(PARTITION by bill_id order by if(operate_type='proof' and create_time < first_judge_tm , 0 ,1) ASC , create_time DESC)  as b_fisrt_judge_tm_last_proof_tm ,
        first_value(if(operate_type='proof' and create_time < first_judge_tm ,erp,'')) over(PARTITION by bill_id order by if(operate_type='proof' and create_time < first_judge_tm , 0 ,1) ASC , create_time DESC)  as b_fisrt_judge_tm_last_proof_erp
from
(
SELECT  bill_id,
        erp,
        operate_type,
        create_time,
        first_value(if(operate_type='judge',create_time,'')) over(PARTITION by bill_id order by if(operate_type='judge',0,1) ASC , create_time ASC ) as first_judge_tm ,
        first_value(if(operate_type='judge',erp,'')) over(PARTITION by bill_id order by if(operate_type='judge',0,1) ASC , create_time ASC ) as first_judge_erp
from dev.dispute_proof_judge
) aaa ) bbb
group by bill_id,first_judge_tm,first_judge_erp,b_fisrt_judge_tm_last_proof_tm,b_fisrt_judge_tm_last_proof_erp