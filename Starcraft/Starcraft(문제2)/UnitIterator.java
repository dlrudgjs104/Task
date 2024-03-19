import java.util.Random;

class UnitIterator implements Iterator {
    static Random random = new Random();
    private int count; // 생성된 유닛의 개수
    private final int maxCount; // 생성할 최대 유닛의 개수
    private int kindSelect;

    public UnitIterator(int kindSelect) {
        this.kindSelect = kindSelect;
        this.count = 0;
        
        if(kindSelect == 1){
            this.maxCount = 5;
        }
        else if(kindSelect == 2){
            this.maxCount = 4;
        }
        else{
            this.maxCount = 8;
        }
    }

    @Override
    public boolean hasNext() {
        return count < maxCount;
    }

    @Override
    public Unit next() {
        if (!hasNext()) {
        }

        // kindSelect 값에 따라 유닛 생성
        int unitType;
        switch (kindSelect) {
            case 1:
                unitType = random.nextInt(6) + 11; // Marine ~ Valkyrie
                break;
            case 2:
                unitType = random.nextInt(6) + 21; // Zealot ~ Corsair
                break;
            case 3:
                unitType = random.nextInt(6) + 31; // Zergling ~ Guardian
                break;
            default:
                unitType = 0; // 잘못된 kindSelect 값인 경우
        }
        count++;

        switch (unitType) {
            case 11:
                return new Marine();
            case 12:
                return new Tank();
            case 13:
                return new Goliath();
            case 14:
                return new Wraith();
            case 15:
                return new Valkyrie();
            case 16:
                return new BattleCruzer();

            case 21:
                return new Zealot();
            case 22:
                return new Dragoon();
            case 23:
                return new HighTempler();
            case 24:
                return new Scout();
            case 25:
                return new Corsair();
            case 26:
                return new Carrier();
                
            case 31:
                return new Zergling();
            case 32:
                return new Hydralisk();
            case 33:
                return new Ultralisk();
            case 34:
                return new Mutalisk();
            case 35:
                return new Guardian();
            case 36:
                return new Queen();

            default:
                return null; // 일어날 수 없는 경우
        }
    }
}