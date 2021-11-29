package libs;

import java.util.Comparator;
import java.util.LinkedList;


public class SortedLinkedList<E> extends LinkedList<E> {
    Comparator<E> comparator;
    public SortedLinkedList (Comparator<E> c) {
        super();
        comparator = c;
    }

    @Override
    public boolean add(E elem) {
        if (this.size() == 0) {
            super.addFirst(elem);
            return true;
        }

        for (int i = 0; i < this.size(); i++) {
            if (comparator.compare(super.get(i), elem) > 0) {
                super.add(i, elem);
                return true;
            }
        }

        super.addLast(elem);
        return true;
    }
}
