
select * from youtube_orc  order by cast(views as int) desc limit 10 ;


select  new_category,
        count(1) as cnt
from youtube_orc lateral view explode(category) tempT as new_category
group by new_category
order by cnt desc
limit 10



SELECT  new_category,
        COUNT(1) as cnt
from (select  *
from youtube_orc
order by cast(views as int)
limit 20 ) aaa1 lateral view explode(category) tempT as new_category
group by new_category



SELECT  new_category,
        COUNT(1) as cnt
from
(
SELECT  *
from youtube_orc basic
left semi join
(
SELECT new_related
from (select  *
from youtube_orc
order by cast(views as int)
limit 50 ) aaa1 lateral view explode(relatedid) tempT as new_related
group by new_related
) bbb
on basic.videoid = bbb.new_related ) aaaa1 lateral view explode(category) tempT as new_category
group by new_category
order by cnt DESC



SELECT  *
from
(
SELECT  *,
        row_number() over(PARTITION by new_category order by cast(views as BIGINT) desc ) as rank
from youtube_orc lateral view explode(category) tempT as new_category
) aaa where aaa.rank <= 10
order by new_category,rank


SELECT  *
from
(
SELECT  *
from youtube_orc
left semi join
(
SELECT  uploader,
        videos
from dev.youtube_user_orc
order by videos DESC
limit 10
) aaa
on youtube_orc.uploader = aaa.uploader
)  bbb
order by cast(views as BIGINT) DESC
limit 20