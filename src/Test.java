import java.util.LinkedList;

class DrawList extends LinkedList<Integer> {
    @Override
    public boolean add(Integer edge) {
        if (this.size() == 0) {
            super.addFirst(edge);
            return true;
        }

        for (int i = 0; i < this.size(); i++) {
            if (super.get(i) < edge) {
                super.add(i, edge);
                return true;
            }
        }

        super.addLast(edge);
        return true;
    }
}

public class Test {
    public static void main(String[] args) {
        DrawList list = new DrawList();
        list.add(1);
        list.add(2);
        list.add(-1);
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
