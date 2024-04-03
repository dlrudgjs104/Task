package bingo;

public class Bingo {
    String[] player1 = new String[25];
    String[] player2 = new String[25];

    public Bingo() {

    }

    public String[] getBorad(String line) {
        System.out.println(" 입력받은 길이: " + line.length());
        if (line.length() == 65) {
            player1 = line.split(" ");
        }
        displayBoard();

        return player1;
    }

    public void displayBoard(){
        for(int i = 1; i <= player1.length; i++){
            System.out.printf("%5s", player1[i - 1]);
            if (i % 5 == 0){
                System.out.println();
            }
        }
    }
}
