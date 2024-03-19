public class Unit implements Comparable<Unit>{
    protected String name;
    protected int hp;
    protected int attackPower;
    protected boolean flyable;
    protected boolean specialAttack;

    Unit(String name, int attackPower, int hp, boolean flyable, boolean specialAttack){
        this.name = name;
        this.attackPower = attackPower;
        this.hp = hp;
        this.flyable = flyable;
        this.specialAttack = specialAttack;
    }
    
    public int gethp(){
        return this.hp;
    }

    public void sethp(int hp){
         this.hp = hp;
    }

    public String getName(){
        return this.name;
    }

    public int getAttackPower(){
        return this.attackPower;
    }

    public void reduceHp(int attackPower){
        this.hp -= attackPower;
    }

    public boolean attackAble(Unit targetUnit){
        if( this.flyable || this.specialAttack || !targetUnit.flyable ){
            return true;
        }
        else{
            return false;
        }
    }

    public void attack(Unit targetUnit){
        targetUnit.reduceHp(this.getAttackPower());
    }

    @Override
    public String toString(){
        if(this.flyable){
            return this.getName() + " ([공중] 공격력: " + this.getAttackPower() + ", 현재 방어력: " + this.gethp() + ")";
        }
        else if(this.specialAttack){
            return this.getName() + " ([특수] 공격력: " + this.getAttackPower() + ", 현재 방어력: " + this.gethp() + ")";
        }
        else{
            return this.getName() + " ([지상] 공격력: " + this.getAttackPower() + ", 현재 방어력: " + this.gethp() + ")";
        }
    }

    @Override
    public int compareTo(Unit otherUnit) {
        return Integer.compare(this.hp, otherUnit.gethp());
    }
    


}

