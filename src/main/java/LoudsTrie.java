import java.util.BitSet;

public class LoudsTrie {
    private BitSet bitSet;
    private char[] edges;

    public LoudsTrie(BitSet bitSet, char[] edges) {
        this.bitSet = bitSet;
        this.edges = edges;
    }

    private int rank(int position, boolean b) {
        int count = 0;
        for (int i = 0; i < position; i++) {
            if (bitSet.get(i) == b) count++;
        }
        return count;
    }

    private int select(int count, boolean b) {
        for (int i = 0; i < bitSet.size(); i++) {
            if (bitSet.get(i) == b) count--;
            if (count == 0) return i;
        }
        return -1;
    }

    public int parent(int x) {
        return rank(select(x, true), false);
    }

    public int firstChild(int x) {
        int y = select(x, false) + 1;
        if (bitSet.get(y)) return rank(y, true) + 1;
        return -1;
    }

    public int traverse(int index, char c) {
        int child = firstChild(index);
        if (child == -1) return -1;

        int childPos = select(child, true);
        while (bitSet.get(childPos)) {
            if (edges[child] == c) return child;
            child++;
            childPos++;
        }
        return -1;
    }

    public static void main(String[] args) {
        char[] edge = "xxabdbca".toCharArray();
        BitSet bitSet = BitSet.valueOf(new long[]{0b000010011011101});
                                                 /* 7654 32  1   0  */
                                                 /*     7  65 432 1 */
        LoudsTrie trie = new LoudsTrie(bitSet, edge);

        System.out.println(trie.rank(4, true));
        System.out.println(trie.select(3, false));
        System.out.println();

        System.out.println(trie.parent(1));
        System.out.println(trie.parent(2));
        System.out.println(trie.parent(3));
        System.out.println(trie.parent(4));
        System.out.println(trie.parent(5));
        System.out.println(trie.parent(6));
        System.out.println(trie.parent(7));
        System.out.println();

        System.out.println(trie.firstChild(1));
        System.out.println(trie.firstChild(2));
        System.out.println(trie.firstChild(3));
        System.out.println(trie.firstChild(4));
        System.out.println(trie.firstChild(5));
        System.out.println(trie.firstChild(6));
        System.out.println(trie.firstChild(7));
        System.out.println();

        System.out.println(trie.traverse(1, 'a'));
        System.out.println(trie.traverse(1, 'b'));
        System.out.println(trie.traverse(1, 'c'));
        System.out.println(trie.traverse(1, 'd'));
        System.out.println(trie.traverse(2, 'a'));
        System.out.println(trie.traverse(2, 'b'));
        System.out.println(trie.traverse(2, 'c'));
        System.out.println(trie.traverse(3, 'a'));
        System.out.println(trie.traverse(4, 'a'));
    }
}
