import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoublyChainedTreeTest {
    @Test
    public void test() {
        DoublyChainedTree<Integer> tree = new DoublyChainedTree<>();
        tree.put("A", 15);
        tree.put("to", 7);
        tree.put("tea", 3);
        tree.put("ted", 4);
        tree.put("ten", 12);
        tree.put("i", 11);
        tree.put("in", 5);
        tree.put("inn", 9);
        assertEquals(8, tree.size());
        assertEquals(15, (int) tree.get("A"));
        assertEquals(7, (int) tree.get("to"));
        assertEquals(3, (int) tree.get("tea"));
        assertEquals(4, (int) tree.get("ted"));
        assertEquals(12, (int) tree.get("ten"));
        assertEquals(11, (int) tree.get("i"));
        assertEquals(5, (int) tree.get("in"));
        assertEquals(9, (int) tree.get("inn"));
        tree.put("A", 14);
        assertEquals(14, (int) tree.get("A"));
        assertEquals(8, tree.size());
        assertEquals(14, (int) tree.remove("A"));
        assertNull(tree.get("A"));
        assertEquals(7, tree.size());
        assertNull(tree.remove("B"));
        assertEquals(7, tree.size());
        assertThat(tree.commonPrefixSearch("inn"), CoreMatchers.hasItems(11, 5, 9));
        assertThat(tree.commonPrefixSearch("to"), CoreMatchers.hasItems(7));
        assertTrue(tree.commonPrefixSearch("B").isEmpty());
        assertThat(tree.predictiveSearch("t"), CoreMatchers.hasItems(7, 3, 4, 12));
        assertThat(tree.predictiveSearch("to"), CoreMatchers.hasItems(7));
        assertThat(tree.predictiveSearch("te"), CoreMatchers.hasItems(3, 4, 12));
        assertTrue(tree.predictiveSearch("B").isEmpty());
    }

    @Test
    public void testToString() {
        DoublyChainedTree<Integer> tree = new DoublyChainedTree<>();
        tree.put("A", 15);
        tree.put("to", 7);
        tree.put("tea", 3);
        tree.put("ted", 4);
        tree.put("ten", 12);
        tree.put("i", 11);
        tree.put("in", 5);
        tree.put("inn", 9);
        String actual = tree.toString();
        String expected = "[A]\n" +
                        " |\n" +
                        "[i]-[n]-[n]\n" +
                        " |\n" +
                        " t - e -[a]\n" +
                        "     |   |\n" +
                        "     |  [d]\n" +
                        "     |   |\n" +
                        "     |  [n]\n" +
                        "    [o]\n";
        assertEquals(actual, expected);
    }
}