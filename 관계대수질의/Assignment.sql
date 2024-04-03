# 1. 비행기 101을 운항하는 파일럿의 이름을 구하세요.
SELECT Pilot1.PilotName 
FROM Flight 
JOIN Pilot1 ON Pilot1.PilotNo = Flight.PilotNo
WHERE AirCraftNo = 101;

# 3. 전투기를 운항하는 파일럿의 이름을 구하세요.
SELECT Pilot1.PilotName 
FROM AirCraft 
JOIN Flight ON Flight.AirCraftNo = AirCraft.AirCraftNo
JOIN Pilot1 ON Flight.PilotNo = Pilot1.PilotNo
WHERE AirCraftKind = '전투기';

# 4. 이순신이 조종하는 비행기의 종류를 구하세요.
SELECT AirCraft.AirCraftKind
FROM Pilot1
JOIN Flight ON Flight.PilotNo = Pilot1.PilotNo
JOIN AirCraft ON AirCraft.AirCraftNo = Flight.AirCraftNo
WHERE PilotName = '이순신';

# 5. 운항 스케줄이 잡혀있는 모든 파일럿의 이름을 구하세요.
SELECT Pilot1.PilotName
FROM Flight
JOIN Pilot1 ON Flight.PilotNo = Pilot1.PilotNo;

# 6. 폭격기 또는 정찰기를 운항하는 파일럿의 이름을 구하세요.
SELECT Pilot1.PilotName
FROM AirCraft
JOIN Flight ON AirCraft.AirCraftNo = Flight.AirCraftNo
JOIN Pilot1 ON Flight.PilotNo = Pilot1.PilotNo
WHERE AirCraftKind = '폭격기' OR '정찰기';

# 7. 전투기와 폭격기를 운형하는 파일럿의 이름을 구하세요.
SELECT Pilot1.PilotName
FROM AirCraft
JOIN Flight ON AirCraft.AirCraftNo = Flight.AirCraftNo
JOIN Pilot1 ON Flight.PilotNo = Pilot1.PilotNo
WHERE AirCraftKind = '폭격기' AND '정찰기';

# 8. 폭격기를 운항하지 않는 나이가 40세 이상의 파일럿의 파일럿번호를 구하세요.
SELECT Pilot1.PilotNo
FROM Pilot1
JOIN Flight ON Flight.PilotNo = Pilot1.PilotNo 
JOIN AirCraft ON AirCraft.AirCraftNo = Flight.AirCraftNo AND AirCraft.AirCraftKind !='폭격기'
WHERE PilotAge >= 40 ;