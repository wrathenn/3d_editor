import Jama.Matrix;

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
        Matrix m = Matrix.identity(3,3);
        m.print(3, 3);
    }
}
