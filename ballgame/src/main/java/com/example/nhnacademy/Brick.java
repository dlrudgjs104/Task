package com.example.nhnacademy;

import java.awt.Color;

public class Brick extends BoundedBox {
    private int hp;
    private int score;

    public Brick(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        colorSelection();
    }

    public int getHP(){
        return hp; 
    }

    public int getScore(){
        return score;
    }

    public void setHP(int hp){
        this.hp = hp;
    }

    public void decreaseHP(){
        hp--;
    }

    public boolean checkHP(){
        return (hp == 0) ? true : false;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void colorSelection() {
        if(color == Color.BLUE){
            setHP(1);
            setScore(2);
        }
        else if(color == Color.GREEN){
            setHP(2);
            setScore(4);
        }
        else if(color == Color.YELLOW){
            setHP(3);
            setScore(6);
        }
        else if(color == Color.RED){
            setHP(4);
            setScore(8);
        }
        else{
            setHP(0);
            setScore(0);
        }
   
    }
}
