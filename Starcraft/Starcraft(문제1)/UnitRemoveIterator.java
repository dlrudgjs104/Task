import java.util.List;
import java.util.Iterator;


public class UnitRemoveIterator implements Iterator<Unit> {
    private Iterator<Unit> iterator;

    public UnitRemoveIterator(List<Unit> units) {
        this.iterator = units.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Unit next() {
        Unit nextUnit = iterator.next();
        if (nextUnit.gethp() <= 0) {
            iterator.remove(); // HP가 0 이하인 유닛을 삭제
        }
        return nextUnit;
    }

    public void remove() {
        iterator.remove(); // 원본 리스트에서 유닛을 삭제
    }
}

