import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Playground {
    static List<Unit> userUnits = new ArrayList<>();
    static List<Unit> computerUnits = new ArrayList<>();
    static int userKindSelectNo;
    static String[] kindName = {"Terran", "Protos", "Zerg"};
    // Random 객체 생성
    static Random random = new Random();
    static int computerKindSelectNo = random.nextInt(3) + 1;
    static Scanner s = new Scanner(System.in);

    /*
     * 종족 선택 메서드
     */
    public static void kindSelect(){
        while(true){
            try {
                System.out.println("1: Terran, 2: Protos, 3: Zerg");
                System.out.printf("종족을 선택해주세요(1~3): ");

                userKindSelectNo = Integer.parseInt(s.nextLine().trim());
                if(userKindSelectNo >= 1 && userKindSelectNo <= 3){
                    break;
                }
                else{
                    throw new Exception();
                }
                
            } catch (Exception e) {
                System.out.println("잘못된 값을 입력했습니다.\n");
            }
        }
    }

    /*
     * 유닛 생성 메서드
     */
    public static void createUnit(){
        // 랜덤하게 컴퓨터 유닛을 생성하고 리스트에 추가
        UnitIterator unitIterator1 = new UnitIterator(computerKindSelectNo);

        while (unitIterator1.hasNext()) {
            computerUnits.add(unitIterator1.next());
        }

         // 랜덤하게 유저 유닛을 생성하고 리스트에 추가
        UnitIterator unitIterator2 = new UnitIterator(userKindSelectNo);

        while (unitIterator2.hasNext()) {
            userUnits.add(unitIterator2.next());
        }             
    }

    /*
     * 현재 유닛 상황을 보여주는 메서드
     */
    public static void displayUnit(){
        Collections.sort(computerUnits);
        Collections.sort(userUnits);

        System.out.println("===================================================");
        System.out.println("적군: " + kindName[computerKindSelectNo - 1]);

        // 생성된 컴퓨터 유닛의 정보 출력
        for(int i = 0; i < computerUnits.size(); i++){
            System.out.println(i + ". " + computerUnits.get(i));
        }

        System.out.println("\n아군: " + kindName[userKindSelectNo - 1]);

        // 생성된 유저 유닛의 정보 출력
        for(int i = 0; i < userUnits.size(); i++){
            System.out.println(i + ". " + userUnits.get(i));
        }
        System.out.println("===================================================");
    }
    
    /*
     * 유저가 공격할 유닛을 선택하고 공격을 실행하는 메서드
     */
    public static void userAttackSelect(){
        while(true){
            try {
                System.out.print("\n공격을 수행할 아군 유닛과 공격할 적군 유닛을 선택하세요(ex)[2 1]");
                String selectUnitNo = s.nextLine().trim();

                // 입력값을 공백을 기준으로 나누기
                String[] targets = selectUnitNo.split(" "); 

                // 두 개의 정수로 변환
                int target1 = Integer.parseInt(targets[0].trim());
                int target2 = Integer.parseInt(targets[1].trim());

                // 입력값과 유닛번호의 유효성 검사
                if ((target1 >= 0 && target1 <= userUnits.size()) && (target2 >= 0 && target2 <= computerUnits.size()) && (userUnits.get(target1).attackAble(computerUnits.get(target2))) ) {
                    System.out.println("↓↓↓ 유저 유닛: " + target1 + "번이 컴퓨터 유닛: " + target2 + "번 공격 ↓↓↓");
                    userUnits.get(target1).attack(computerUnits.get(target2));
                    break;
                }
                else{
                    throw new Exception();
                }
   
            } catch (Exception e) {
                System.out.println("잘못된 값을 입력했습니다.");
            }   
        }  
    }

    /*
     * 컴퓨터가 공격할 유닛을 선택하고 공격을 실행하는 메서드
     */

    public static void computerAttackSelect(){
        while(true){
            int target1 = random.nextInt(computerUnits.size());
            int target2 = random.nextInt(userUnits.size());

            try {
                if(computerUnits.get(target1).attackAble(userUnits.get(target2))){
                    System.out.println("↓↓↓ 컴퓨터 유닛: " + target1 + "번이 유저 유닛: " + target2 + "번 공격 ↓↓↓");
                    computerUnits.get(target1).attack(userUnits.get(target2));
                    break;
                }
                else{
                    throw new Exception();
                }

            } catch (Exception e) {
            
            }
        }
    }

    /*
     * 결과를 출력하는 메서드
    */
    public static boolean result(){
        UnitRemoveIterator unitRemoveIterator1 = new UnitRemoveIterator(computerUnits);

        while(unitRemoveIterator1.hasNext()){
            unitRemoveIterator1.next();
        }

        UnitRemoveIterator unitRemoveIterator2 = new UnitRemoveIterator(userUnits);

        while(unitRemoveIterator2.hasNext()){
            unitRemoveIterator2.next();
        }
        
        int computerGroundUnitCount = 0;
        int userGroundUnitCount = 0;
        
        for(int i = 0; i < computerUnits.size(); i++){
            if( (!computerUnits.get(i).flyable) && (!computerUnits.get(i).specialAttack) ){
                computerGroundUnitCount++;
            }
        }

        for(int i = 0; i < userUnits.size(); i++){
            if( (!userUnits.get(i).flyable) && (!userUnits.get(i).specialAttack) ){
                userGroundUnitCount++;
            }
        }
        
        // 컴퓨터 유닛이 전부 없거나 유저는 공중 유닛 또는 특수 유닛이 있고 컴퓨터는 지상 유닛만 있는 경우
        if(computerUnits.size() == 0 || (computerGroundUnitCount == computerUnits.size()) && (userGroundUnitCount != userUnits.size()) ){
            displayUnit();
            System.out.println("승리했습니다!!");
            return true;
        }   // 유저 유닛이 전부 없거나 컴퓨터는 공중 유닛 또는 특수 유닛이 있고 유저는 지상 유닛만 있는 경우
        else if(userUnits.size() == 0 || (computerGroundUnitCount != computerUnits.size()) && (userGroundUnitCount == userUnits.size()) ){
            displayUnit();
            System.out.println("패배했습니다!!");
            return true;
        }
        else{
            displayUnit();
            return false;
        }

        
         
    }
     
    
    public static void main(String[] args) {
        kindSelect();
        createUnit();
        displayUnit();

        while(true){
            userAttackSelect();
            if(result()){break;}
            computerAttackSelect();
            if(result()){break;}
        }
        
        s.close();

    }
}

