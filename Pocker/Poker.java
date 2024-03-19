import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Poker {
}

enum Suit {
    Spade, Heart, Diamond, Club;
}

class Card {
    private int number;
    private Suit shape;

    public Card(int number, Suit shape) {
        this.number = number;
        this.shape = shape;
    }

    private String getDisplayNumber(int number) {
        String[] displayNumber = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        return displayNumber[number - 2];
    }

    public int getNumber() {
        return this.number;
    }

    public Suit getShape() {
        return this.shape;
    }

    @Override
    public String toString() {
        return getDisplayNumber(number) + " " + shape;
    }
}

class User {
    private int userNo;
    private List<Card> hand;

    User(int userNo) {
        this.userNo = userNo;
    }

    public int getUserNo() {
        return this.userNo;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
}

class Game{
    public User findWinner(List<User> users) {
        User winner = null;
        int maxScore = -1;

        for (User user : users) {
            int score = calculateScore(user);
            if (score > maxScore) {
                maxScore = score;
                winner = user;
            }
        }

        return winner;
    }

    public int calculateScore(User user) {
        List<Card> hand = user.getHand();
        Collections.sort(hand, Comparator.comparing(Card::getNumber));

        int result = decision(hand);
        //System.out.println("정렬된 카드 리스트: " + hand);

        if (result > 40) {
            return result; // 투페어
        } else if (result > 20) {
            return result; // 원페어
        } else {
            return result; // 하이카드
        }
    }

    public int decision(List<Card> hand) {
        int pairCount = 0;
        int prevNumber = 0;
        int maxNumber = 0;

        for (Card card : hand) {
            if (prevNumber == card.getNumber()) {
                pairCount++;
                if(pairCount == 1){   
                    maxNumber = prevNumber;
                    prevNumber = 0; // 리셋하여 다음 숫자로 이동
                }
                else if(pairCount == 2){
                    if(prevNumber > maxNumber){
                        maxNumber = prevNumber;
                        prevNumber = 0; // 리셋하여 다음 숫자로 이동
                    }
                }      
            } else {
                prevNumber = card.getNumber();
            }
        }
        
        if (pairCount == 2){
            return 40 + maxNumber;
        }
        else if (pairCount == 1){
            return 20 + maxNumber;
        }
        else{
            return maxNumber;
        }
       
    }
}

class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("사용자 수 입력: ");
        int numUsers = scanner.nextInt();
        scanner.close();

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= numUsers; i++) {
            User user = new User(i);
            users.add(user);
        }

        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (int number = 2; number <= 14; number++) {
                cards.add(new Card(number, suit));
            }
        }
        Collections.shuffle(cards);

        for (User user : users) {
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                hand.add(cards.remove(0)); // 덱에서 카드를 뽑아 손에 추가
            }
            user.setHand(hand);
        }

        // 결과 출력
        for (User user : users) {
            System.out.println("Player " + user.getUserNo() + "의 카드: " + user.getHand());
        }

         // 카드 비교하여 승자 결정
         Game game = new Game();
         User winner = game.findWinner(users);
         System.out.println("승자는 Player " + winner.getUserNo() + " 입니다.");
    }

}


