import java.util.*;

public class DoublyChainedTree<T> {

//    private static final char BLANK = ' ';
//    private static final char VERTICAL_BAR = '|';
//    private static final char HORIZONTAL_BAR = '-';
//    private static final char LEFT_PARENTHESIS = '[';
//    private static final char RIGHT_PARENTHESIS = ']';
    private static final char BLANK = '　';
    private static final char VERTICAL_BAR = '｜';
    private static final char HORIZONTAL_BAR = 'ー';
    private static final char LEFT_PARENTHESIS = '【';
    private static final char RIGHT_PARENTHESIS = '】';

    private static class Node<T> {
        char c;
        Node<T> child;
        Node<T> sibling;
        T value;
    }

    private Node<T> root = new Node<>();
    private int size = 0;

    public void put(String key, T value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        if (key.length() == 0) throw new IllegalArgumentException();

        Node<T> parent = this.root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (parent.child == null) {
                parent.child = new Node<>();
                parent.child.c = c;
                parent = parent.child;
            } else if (c < parent.child.c) {
                Node<T> node = new Node<>();
                node.c = c;
                node.sibling = parent.child;
                parent.child = node;
                parent = parent.child;
            } else {
                Node<T> node = parent.child;
                while (node.sibling != null && node.sibling.c <= c) {
                    node = node.sibling;
                }
                if (node.c != c) {
                    Node<T> tmp = new Node<>();
                    tmp.c = c;
                    tmp.sibling = node.sibling;
                    node.sibling = tmp;
                    node = tmp;
                }
                parent = node;
            }
            if (i == key.length() - 1) {
                if (parent.value == null) size++;
                parent.value = value;
            }
        }
    }

    public T remove(String key) {
        Objects.requireNonNull(key);
        if (key.length() == 0) throw new IllegalArgumentException();

        Node<T> parent = this.root;
        Node<T> prevRemovedNode = this.root;
        boolean isPrevRemovedNodeSibling = false;
        for (int i = 0; i < key.length(); i++) {
            if (parent.child == null) {
                return null;
            }
            char c = key.charAt(i);
            Node<T> node = parent.child;
            if (node.c == c && (node.sibling != null || parent.value != null)) {
                prevRemovedNode = parent;
                isPrevRemovedNodeSibling = false;
            }
            while (node.sibling != null && node.sibling.c <= c) {
                prevRemovedNode = node;
                isPrevRemovedNodeSibling = true;
                node = node.sibling;
            }
            if (node.c != c) {
                return null;
            }
            if (i == key.length() - 1) {
                if (node.value == null) return null;

                size--;
                T foundValue = node.value;
                node.value = null;

                if (node.child == null) {
                    if (isPrevRemovedNodeSibling) {
                        prevRemovedNode.sibling = prevRemovedNode.sibling.sibling;
                    } else {
                        prevRemovedNode.child = prevRemovedNode.child.sibling;
                    }
                }
                return foundValue;
            }
            parent = node;
        }
        return null;
    }

    public T get(String key) {
        Objects.requireNonNull(key);
        if (key.length() == 0) throw new IllegalArgumentException();

        Node<T> node = this.root;
        for (int i = 0; i < key.length(); i++) {
            if (node.child == null) {
                return null;
            }
            char c = key.charAt(i);
            node = node.child;
            while (node.sibling != null && node.sibling.c <= c) {
                node = node.sibling;
            }
            if (node.c != c) {
                return null;
            }
            if (i == key.length() - 1) {
                return node.value;
            }
        }
        return null;
    }

    public List<T> commonPrefixSearch(String key) {
        Objects.requireNonNull(key);
        if (key.length() == 0) throw new IllegalArgumentException();

        List<T> list = new ArrayList<>();
        Node<T> node = this.root;
        for (int i = 0; i < key.length(); i++) {
            if (node.child == null) {
                break;
            }
            char c = key.charAt(i);
            node = node.child;
            while (node.sibling != null && node.sibling.c <= c) {
                node = node.sibling;
            }
            if (node.c != c) {
                break;
            }
            if (node.value != null) {
                list.add(node.value);
            }
        }
        return list;
    }

    public List<T> predictiveSearch(String key) {
        Objects.requireNonNull(key);
        if (key.length() == 0) throw new IllegalArgumentException();

        List<T> list = new ArrayList<>();
        Node<T> node = this.root;
        for (int i = 0; i < key.length(); i++) {
            if (node.child == null) {
                return list;
            }
            char c = key.charAt(i);
            node = node.child;
            while (node.sibling != null && node.sibling.c <= c) {
                node = node.sibling;
            }
            if (node.c != c) {
                return list;
            }
        }

        list.addAll(predictiveSearchRecursive(node));
        return list;
    }

    private List<T> predictiveSearchRecursive(Node<T> parent) {
        List<T> list = new ArrayList<>();
        if (parent.value != null) list.add(parent.value);
        if (parent.child == null) return list;
        Node<T> node = parent.child;
        while (true) {
            list.addAll(predictiveSearchRecursive(node));
            if (node.sibling == null) break;
            node = node.sibling;
        }
        return list;
    }

    public String toString() {
        if (this.root.child == null) return "";
        SquareSize squareSize = calcSquareSize(this.root.child);
        char[][] buffer = new char[squareSize.y * 2 - 1][squareSize.x * 4 - 1];
        for (int i = 0; i < buffer.length; i++) {
            Arrays.fill(buffer[i], BLANK);
        }
        recursiveForBuffer(buffer, 0, 0, this.root.child);
        StringBuilder sb = new StringBuilder();
        for (char[] line : buffer) {
            sb.append(new String(line)).append("\n");
        }
        return sb.toString();
    }

    private int recursiveForBuffer(char[][] buffer, int x, int y, Node<T> node) {
        buffer[y][x + 1] = node.c;
        if (node.value != null) {
            buffer[y][x] = LEFT_PARENTHESIS;
            buffer[y][x + 2] = RIGHT_PARENTHESIS;
        }
        int sumY = 1;
        if (node.child != null) {
            buffer[y][x + 3] = HORIZONTAL_BAR;
            sumY = Math.max(sumY, recursiveForBuffer(buffer, x + 4, y, node.child));
        }
        if (node.sibling != null) {
            for (int i = 1; i < 2 * sumY; i++) {
                buffer[y + i][x + 1] = VERTICAL_BAR;
            }
            sumY += recursiveForBuffer(buffer, x, y + 2 * sumY, node.sibling);
        }
        return sumY;
    }

    private SquareSize calcSquareSize(Node<T> parent) {
        SquareSize childSquareSize = parent.child != null ? calcSquareSize(parent.child) : new SquareSize(0, 0);
        SquareSize siblingSquareSize = parent.sibling != null ? calcSquareSize(parent.sibling) : new SquareSize(0, 0);

        return new SquareSize(Math.max(childSquareSize.x + 1, siblingSquareSize.x), Math.max(childSquareSize.y, 1) + siblingSquareSize.y);
    }

    private static class SquareSize {
        int x;
        int y;

        SquareSize(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int size() {
        return this.size;
    }

    public static void main(String[] args) {
//        DoublyChainedTree<Integer> tree = new DoublyChainedTree<>();
//        tree.put("A", 15);
//        tree.put("to", 7);
//        tree.put("tea", 3);
//        tree.put("ted", 4);
//        tree.put("ten", 12);
//        tree.put("i", 11);
//        tree.put("in", 5);
//        tree.put("inn", 9);
//        System.out.println(tree);
//
//        tree.put("ina", 0);
//        tree.put("inari", 0);
//        tree.put("innda", 0);
//        tree.put("instance", 0);
//        tree.remove("A");
//        tree.remove("i");
//        tree.remove("inari");
//        tree.remove("inn");
//        tree.remove("tea");
//        tree.remove("to");
//        System.out.println(tree);

        Random random = new Random();
        DoublyChainedTree<Integer> tree = new DoublyChainedTree<>();
        for (int i = 0; i < 100; i++) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < 20; j++) {
                s.append((char) ('ハ' + random.nextInt(j + 1)));
                if (random.nextInt(j + 1) < j / 2) break;
            }
            tree.put(s.toString(), 0);
        }
        System.out.println(tree.toString());
    }
}
