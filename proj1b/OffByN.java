public class OffByN implements CharacterComparator{

    private int num;

    public OffByN(int num) {
        this.num = num;
    }

    @Override
    public boolean equalChars(char x, char y){
        int diff = x - y;

        return Math.abs(diff) == num;
    }
}
