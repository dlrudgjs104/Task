-- 복원한 DatamotionMovieDatabase에서, 아래 질의들을 SQL Query 식으로 작성후 Query를 제출해주세요
-- 01. 영화 '퍼스트 맨'의 제작 연도, 영문 제목, 러닝 타임, 플롯을 출력하세요.
-- SELECT ReleaseYear, Title, RunningTime, Plot 
-- FROM movie
-- WHERE KoreanTitle = '퍼스트 맨';

-- 02. 2003년에 개봉한 영화의 한글 제목과 영문 제목을 출력하세요
-- SELECT KoreanTitle, Title
-- FROM movie
-- WHERE ReleaseYear = '2003';

-- 03. 영화 '글래디에이터'의 작곡가를 고르세요
-- SELECT Name AS 작곡가
-- FROM person AS p
-- JOIN appear AS a ON p.personID = a.personID
-- JOIN role AS r ON a.RoleID = r.RoleID
-- JOIN movie AS m ON a.movieID = m.movieID
-- WHERE m.KoreanTitle = '글래디에이터' AND r.RoleKorName = '작곡';

-- 04. 영화 '매트릭스' 의 감독이 몇명인지 출력하세요
-- SELECT COUNT(Name) AS 감독수
-- FROM person AS p
-- JOIN appear AS a ON p.personID = a.personID
-- JOIN role AS r ON a.RoleID = r.RoleID
-- JOIN movie AS m ON a.movieID = m.movieID
-- WHERE m.KoreanTitle = '매트릭스' AND r.RoleKorName = '감독';

-- 05. 감독이 2명 이상인 영화를 출력하세요
-- SELECT Title
-- FROM movie As m 
-- JOIN appear AS a ON a.movieID = m.movieID 
-- JOIN role AS r ON a.RoleID = r.RoleID
-- JOIN person AS p ON p.personID = a.personID
-- GROUP BY Title
-- HAVING COUNT(p.Name) >= 2;

-- 06. '한스 짐머'가 참여한 영화 중 아카데미를 수상한 영화를 출력하세요
-- SELECT Title
-- FROM movie AS m
-- JOIN appear AS a ON a.movieID = m.movieID 
-- JOIN person AS p ON p.personID = a.personID
-- JOIN awardinvolve AS ai ON ai.AppearID = a.AppearID
-- JOIN Winning AS w ON w.winningID = ai.winningID
-- WHERE p.KoreanName = '한스 짐머' AND w.WinorNot = 'Winner'; 

-- 07. 감독이 '제임스 카메론'이고 '아놀드 슈워제네거'가 출연한 영화를 출력하세요
-- SELECT Title
-- FROM movie AS m
-- JOIN appear AS a1 ON a1.movieID = m.movieID
-- JOIN role AS r1 ON a1.RoleID = r1.RoleID
-- JOIN person AS p1 ON a1.PersonID = p1.PersonID AND p1.KoreanName = '제임스 카메론' AND r1.RoleKorName = '감독'
-- JOIN appear AS a2 ON a2.movieID = m.movieID
-- JOIN role AS r2 ON a2.RoleID = r2.RoleID
-- JOIN person AS p2 ON a2.PersonID = p2.PersonID AND p2.KoreanName = '아놀드 슈워제네거' AND r2.RoleKorName = '배우';

-- 08. 상영시간이 100분 이상인 영화 중 레오나르도 디카프리오가 출연한 영화를 고르시오
-- SELECT DISTINCT Title
-- FROM movie AS m
-- JOIN appear AS a ON m.movieID = a.movieID
-- JOIN person AS p ON a.personID = p.personID
-- WHERE m.RunningTime >= 100 AND p.KoreanName = '레오나르도 디카프리오';

-- 09. 청소년 관람불가 등급의 영화 중 가장 많은 수익을 얻은 영화를 고르시오
-- SELECT KoreanTitle
-- FROM movie AS m
-- JOIN gradeinkorea AS gok ON gok.GradeInKoreaID = m.GradeInKoreaID
-- WHERE gok.GradeInKoreaName = '청소년 관람불가'
-- ORDER BY BoxOfficeWWGross DESC
-- LIMIT 1;

-- 10. 1999년 이전에 제작된 영화의 수익 평균을 고르시오
-- SELECT AVG(BoxOfficeWWGross) AS 수익평균
-- FROM movie
-- WHERE ReleaseYear < 1999;

-- 11. 가장 많은 제작비가 투입된 영화를 고르시오.
-- SELECT Title
-- FROM movie
-- ORDER BY budget DESC
-- LIMIT 1;

-- 12. 제작한 영화의 제작비 총합이 가장 높은 감독은 누구입니까?
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.personID = p.PersonID
-- JOIN movie AS m ON m.movieID = a.MovieID
-- JOIN Role AS r ON r.RoleID = a.RoleID
-- WHERE r.RoleKorName = '감독'
-- GROUP BY Name
-- ORDER BY SUM(m.budget) DESC
-- LIMIT 1;

-- 13. 출연한 영화의 모든 수익을 합하여, 총 수입이 가장 많은 배우를 출력하세요.
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.personID = p.PersonID
-- JOIN movie AS m ON m.movieID = a.MovieID
-- JOIN Role AS r ON r.RoleID = a.RoleID
-- WHERE r.RoleKorName = '배우'
-- GROUP BY Name
-- ORDER BY SUM(m.budget) DESC
-- LIMIT 1;

-- 14. 제작비가 가장 적게 투입된 영화의 수익을 고르세요. (제작비가 0인 영화는 제외합니다)
-- SELECT BoxOfficeWWGross
-- FROM movie
-- WHERE budget != 0 
-- ORDER BY budget ASC
-- LIMIT 1;

-- 15. 제작비가 5000만 달러 이하인 영화의 미국내 평균 수익을 고르세요
-- SELECT AVG(BoxOfficeUSGross) AS 평균수익
-- FROM movie
-- WHERE budget <= 50000000; 
 
-- 16. 액션 장르 영화의 평균 수익을 고르세요
-- SELECT AVG(BoxOfficeWWGross) AS 평균수익
-- FROM movie AS m
-- JOIN moviegenre AS mg ON mg.movieID = m.movieID
-- JOIN genre AS g ON g.genreID = mg.genreID
-- WHERE g.GenreKorName = '액션';

-- 17. 드라마, 전쟁 장르의 영화를 고르세요.
-- SELECT Title
-- FROM movie AS m
-- JOIN moviegenre AS mg ON mg.movieID = m.movieID
-- JOIN genre AS g ON g.genreID = mg.genreID
-- WHERE g.GenreKorName = '드라마' OR g.GenreKorName = '전쟁'
-- GROUP BY Title;

-- 18. 톰 행크스가 출연한 영화 중 상영 시간이 가장 긴 영화의 제목, 한글제목, 개봉연도를 출력하세요.
-- SELECT Title, KoreanTitle, ReleaseYear, RunningTime
-- FROM movie AS m
-- JOIN appear AS a ON a.movieID = m.movieID
-- JOIN person AS p ON p.personID = a.personID
-- WHERE p.KoreanName = '톰 행크스'
-- ORDER BY RunningTime DESC
-- LIMIT 1;

-- 19. 아카데미 남우주연상을 가장 많이 수상한 배우를 고르시오
-- SELECT Name
-- FROM Person AS p
-- JOIN appear AS a ON a.personID = p.personID
-- JOIN Role AS r ON r.RoleID = a.RoleID
-- JOIN awardinvolve AS ai ON ai.appearID = a.appearID
-- JOIN sector AS s ON s.SectorID = ai.SectorID
-- JOIN winning AS w ON w.winningID = ai.winningID
-- WHERE s.SectorKorName = '남우주연상' AND r.RoleKorName = '배우' AND w.WinORNot = 'Winner'
-- GROUP BY Name
-- ORDER BY COUNT(Name) DESC
-- LIMIT 1;

-- 20. 아카데미상을 가장 많이 수상한 배우를 고르시오 ('수상자 없음'이 이름인 영화인은 제외합니다)
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.personID = p.PersonID
-- JOIN Role AS r ON r.RoleID = a.RoleID
-- JOIN awardinvolve AS ai ON ai.appearID = a.appearID
-- JOIN winning AS w ON w.winningID = ai.winningID
-- WHERE r.RoleKorName = '배우' AND Name != '수상자 없음' AND w.WinORNot = 'Winner'
-- GROUP BY Name
-- ORDER BY COUNT(Name) DESC
-- LIMIT 1;

-- 21. 아카데미 남우주연상을 2번 이상 수상한 배우를 고르시오
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.personID = p.PersonID
-- JOIN Role AS r ON r.RoleID = a.RoleID
-- JOIN awardinvolve AS ai ON ai.appearID = a.appearID
-- JOIN sector AS s ON s.SectorID = ai.SectorID
-- JOIN winning AS w ON w.winningID = ai.winningID
-- WHERE r.RoleKorName = '배우' AND s.SectorKorName = '남우주연상' AND w.WinOrNot = 'Winner'
-- GROUP BY Name
-- HAVING COUNT(Name) >= 2;

-- 23. 아카데미상을 가장 많이 수상한 사람을 고르세요.
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.personID = p.PersonID
-- JOIN awardinvolve AS ai ON ai.appearID = a.appearID
-- JOIN winning AS w ON w.winningID = ai.winningID
-- WHERE w.WinOrNot = 'Winner'  AND KoreanName != '수상자 없음'
-- GROUP BY Name
-- ORDER BY COUNT(Name) DESC
-- LIMIT 1;

-- 24. 아카데미상에 가장 많이 노미네이트 된 영화를 고르세요.
-- SELECT Title
-- FROM movie AS m
-- JOIN appear AS a ON a.movieID = m.movieID
-- JOIN awardinvolve AS ai ON ai.appearID = a.appearID
-- JOIN winning AS w ON w.WinningID = ai.WinningID
-- WHERE w.WinOrNot = 'Nominated'
-- GROUP BY Title
-- ORDER BY COUNT(Title) DESC
-- LIMIT 1;

-- 25. 가장 많은 영화에 출연한 여배우를 고르세요.
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.PersonID = p.PersonID
-- JOIN role AS r ON r.RoleID = a.RoleID
-- WHERE r.RoleName = 'Actress'
-- GROUP BY Name
-- ORDER BY COUNT(Name) DESC
-- LIMIT 1;

-- 26. 수익이 가장 높은 영화 TOP 10을 출력하세요.
-- SELECT Title
-- FROM movie
-- ORDER BY BoxOfficeWWGross DESC
-- LIMIT 10; 

-- 27. 수익이 10억불 이상인 영화중 제작비가 1억불 이하인 영화를 고르시오.
-- SELECT Title
-- FROM movie
-- WHERE BoxOfficeWWGross >= 1000000000 AND budget <= 100000000;

-- 28. 전쟁 영화를 가장 많이 감독한 사람을 고르세요.
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.PersonID = p.personID
-- JOIN role AS r ON r.RoleID = a.RoleID
-- JOIN movie AS m ON m.movieID = a.MovieID
-- JOIN moviegenre AS mg ON mg.MovieID = m.MovieID
-- JOIN genre AS g ON g.GenreID = mg.GenreID
-- WHERE g.GenreKorName = '전쟁' AND r.RoleKorName = '감독'
-- GROUP BY Name
-- ORDER BY COUNT(Name) DESC
-- LIMIT 1;

-- 29. 드라마에 가장 많이 출연한 사람을 고르세요.
-- SELECT Name
-- FROM person AS p
-- JOIN appear AS a ON a.PersonID = p.PersonID
-- JOIN role AS r ON r.RoleID = a.RoleID
-- JOIN movie AS m ON m.movieID = a.MovieID
-- JOIN moviegenre AS mg ON mg.MovieID = m.MovieID
-- JOIN genre AS g ON g.GenreID = mg.GenreID
-- WHERE g.GenreKorName = '드라마' AND (r.RoleKorName = '배우' OR r.RoleKorName = '잡역부' OR r.RoleKorName = '기타 역할' OR r.RoleKorName = '스턴트')
-- GROUP BY Name
-- ORDER BY COUNT(Name) DESC
-- LIMIT 1;

-- 30. 드라마 장르에 출연했지만 호러 영화에 한번도 출연하지 않은 사람을 고르세요.
-- SELECT p.Name
-- FROM person AS p
-- JOIN appear AS a ON a.PersonID = p.PersonID
-- JOIN Role AS r ON r.RoleID = a.RoleID
-- JOIN movie AS m ON m.MovieID = a.MovieID
-- JOIN moviegenre AS mg ON mg.MovieID = m.MovieID
-- JOIN genre AS g ON g.GenreID = mg.GenreID AND g.GenreKorName = '드라마'

-- JOIN appear AS a2 ON a2.PersonID = p.PersonID
-- JOIN Role AS r2 ON r2.RoleID = a.RoleID
-- JOIN movie AS m2 ON m2.MovieID = a2.MovieID
-- JOIN moviegenre AS mg2 ON mg2.MovieID = m2.MovieID
-- JOIN genre AS g2 ON g2.GenreID = mg2.GenreID AND g2.GenreKorName != '호러'
-- WHERE r.RoleKorName = '배우' OR r.RoleKorName = '잡역부' OR r.RoleKorName = '기타 역할' OR r.RoleKorName = '스턴트'
-- GROUP BY Name;

-- 31. 아카데미 영화제가 가장 많이 열린 장소는 어디인가요?
-- SELECT location
-- FROM awardyear
-- GROUP BY location
-- ORDER BY COUNT(location) DESC
-- LIMIT 1;

-- 33. 첫 번째 아카데미 영화제가 열린지 올해 기준으로 몇년이 지났나요?
-- SELECT year(CURDATE()) - MIN(year) AS 첫번째_아카데미_영화제_열린후_지난기간
-- FROM awardyear;

-- 34. SF 장르의 영화 중 아카데미 영화제 후보에 가장 많이 오른 영화를 구하세요.
-- SELECT Title
-- FROM movie AS m
-- JOIN moviegenre AS mg ON mg.MovieID = m.MovieID
-- JOIN genre AS g ON g.GenreID = mg.GenreID
-- JOIN appear AS a ON a.MovieID = m.MovieID
-- JOIN awardinvolve AS ai ON ai.AppearID = a.AppearID
-- JOIN winning AS w ON w.WinningID = ai.winningID
-- WHERE g.GenreName = 'Sci-Fi'
-- GROUP BY Title
-- ORDER BY COUNT(Title) DESC
-- LIMIT 1;

-- 35. 드라마 장르의 영화의 아카데미 영화제 작품상 수상 비율을 구하세요.
SELECT 
	COUNT(DISTINCT CASE WHEN w.WinOrNot = 'Winner' AND s.SectorKorName = '작품상' THEN m.MovieID ELSE NULL END) AS 드라마_수상수,
    COUNT(DISTINCT m.MovieID) AS 전체_드라마수,
    COUNT(DISTINCT CASE WHEN w.WinOrNot = 'Winner' AND s.SectorKorName = '작품상' THEN m.MovieID ELSE NULL END) / COUNT(DISTINCT m.MovieID) AS 수상비율
FROM movie AS m
JOIN appear AS a ON a.MovieID = m.MovieID
JOIN awardinvolve AS ai ON ai.AppearID = a.AppearID
JOIN winning AS w ON w.WinningID = ai.WinningID
JOIN moviegenre AS mg ON mg.MovieID = m.MovieID
JOIN genre AS g ON g.GenreID = mg.GenreID
JOIN sector AS s ON s.SectorID = ai.SectorID
WHERE g.GenreKorName = '드라마';








