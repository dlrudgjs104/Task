import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
public static String[][] gameArray = { {"", "", ""},
                                       {"", "", ""},
                                       {"", "", ""} };
 
    public static void user(){ 
        for( ; ; ){ 
            try{ 
                Scanner scanner = new Scanner(System.in);
                System.out.printf("사용자 턴(x y): " );
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                            
                if ( gameArray[x][y] == ""){
                    gameArray[x][y] = "O";
                    break;
                }
                else{
                    System.out.printf("다른 칸을 입력해주세요.\n");   
                }
            }
            catch(Exception e){
                e.getMessage();
                System.out.printf("잘못된 입력입니다. 다시 입력해 주세요.\n");   
            }
        }    
    }

    public static void computer(){
        int x, y;
        Random random = new Random();
        
        for( ; ; ){
            x = random.nextInt(3);
            y = random.nextInt(3);
            if ( gameArray[x][y] == ""){
                gameArray[x][y] = "X";
                System.out.println("컴퓨터 턴");
                break;
            }
        }
    }

    public static void output(){
        System.out.printf("%2s " + "ㅣ" + "%2s " + "ㅣ" + "%2s \n", gameArray[0][0], gameArray[0][1], gameArray[0][2]);
        System.out.printf("---" + "ㅣ" + "---" + "ㅣ" + "---\n");
        System.out.printf("%2s " + "ㅣ" + "%2s " + "ㅣ" + "%2s \n", gameArray[1][0], gameArray[1][1], gameArray[1][2]);
        System.out.printf("---" + "ㅣ" + "---" + "ㅣ" + "---\n");
        System.out.printf("%2s " + "ㅣ" + "%2s " + "ㅣ" + "%2s \n\n", gameArray[2][0], gameArray[2][1], gameArray[2][2]);
    }

    public static boolean result(String name){
        if ( (gameArray[0][0] == gameArray[0][1]) && (gameArray[0][1] == gameArray[0][2]) && (gameArray[0][2] != "") ||
             (gameArray[1][0] == gameArray[1][1]) && (gameArray[1][1] == gameArray[1][2]) && (gameArray[1][2] != "") ||
             (gameArray[2][0] == gameArray[2][1]) && (gameArray[2][1] == gameArray[2][2]) && (gameArray[2][2] != "") ||
             (gameArray[0][0] == gameArray[1][0]) && (gameArray[1][0] == gameArray[2][0]) && (gameArray[2][0] != "") ||
             (gameArray[0][1] == gameArray[1][1]) && (gameArray[1][1] == gameArray[2][1]) && (gameArray[2][1] != "") ||
             (gameArray[0][2] == gameArray[1][2]) && (gameArray[1][2] == gameArray[2][2]) && (gameArray[2][2] != "") ||
             (gameArray[0][0] == gameArray[1][1]) && (gameArray[1][1] == gameArray[2][2]) && (gameArray[2][2] != "") ||
             (gameArray[0][2] == gameArray[1][1]) && (gameArray[1][1] == gameArray[2][0]) && (gameArray[2][0] != "") ){

                if (name == "user"){
                    System.out.println("사용자 승리");
                }
                else{
                    System.out.println("컴퓨터 승리");
                }
                return true;
            }
        else{
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if (gameArray[i][j] == ""){
                        return false;
                    }
                }
            }
            System.out.println("무승부");
            return true;            
        }
    }

    public static void main(String[] args){
        for( ; ; ){
            user();
            output();
            if (result("user")){
                break;
            }
            computer();
            output();
            if (result("computer")){
                break;
            }          
        }
    }
}
