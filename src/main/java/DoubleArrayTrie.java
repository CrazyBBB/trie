import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleArrayTrie {
//    private static final int CHAR_SIZE = 85;
//    private static int convertToCharIndex(char c) {
//        return c - 'あ';
//    }
    private static final int CHAR_SIZE = 26;
    private static int convertToCharIndex(char c) {
        return c - 'a';
    }

    private int[] base;
    private int[] check;

    public DoubleArrayTrie(int[] base, int[] check) {
        this.base = base;
        this.check = check;
    }

    public int parent(int x) {
        return check[x];
    }

    public List<Integer> getChildren(int x) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < CHAR_SIZE; i++) {
            if (base[x] + i == -1) continue;
            if (check[base[x] + i] == x) list.add(base[x] + i);
        }
        return list;
    }

    public int traverse(int x, char c) {
        int candidate = base[x] + 1 + convertToCharIndex(c);
        if (check[candidate] == x) return candidate;
        return -1;
    }

    public static void main(String[] args) {
        int[] base = new int[100];
        int[] check = new int[100];
        Arrays.fill(base, -1);
        Arrays.fill(check, -1);
        base[0] = 0;
        base[1] = 3;
        base[4] = 2;
        check[1] = 0;
        check[2] = 0;
        check[3] = 4;
        check[4] = 0;
        check[5] = 1;
        check[6] = 1;
        DoubleArrayTrie trie = new DoubleArrayTrie(base, check);

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(0);
        while (!q.isEmpty()) {
            int x = q.poll();
            List<Integer> list = trie.getChildren(x);
            for (int child : list) {
                System.out.println(x + " -> " + child);
                q.add(child);
            }
        }
        System.out.println();

        System.out.println(trie.traverse(0, 'a'));
        System.out.println(trie.traverse(0, 'b'));
        System.out.println(trie.traverse(0, 'c'));
        System.out.println(trie.traverse(0, 'd'));
        System.out.println(trie.traverse(1, 'a'));
        System.out.println(trie.traverse(1, 'b'));
        System.out.println(trie.traverse(1, 'c'));
        System.out.println(trie.traverse(2, 'a'));
        System.out.println(trie.traverse(3, 'a'));
        System.out.println(trie.traverse(4, 'a'));
        System.out.println(trie.traverse(5, 'a'));
    }
}
