import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoublyChainedTreeTrieTest {
    @Test
    public void test() {
        DoublyChainedTreeTrie<Integer> trie = new DoublyChainedTreeTrie<>();
        trie.put("A", 15);
        trie.put("to", 7);
        trie.put("tea", 3);
        trie.put("ted", 4);
        trie.put("ten", 12);
        trie.put("i", 11);
        trie.put("in", 5);
        trie.put("inn", 9);
        assertEquals(8, trie.size());
        assertEquals(15, (int) trie.get("A"));
        assertEquals(7, (int) trie.get("to"));
        assertEquals(3, (int) trie.get("tea"));
        assertEquals(4, (int) trie.get("ted"));
        assertEquals(12, (int) trie.get("ten"));
        assertEquals(11, (int) trie.get("i"));
        assertEquals(5, (int) trie.get("in"));
        assertEquals(9, (int) trie.get("inn"));
        trie.put("A", 14);
        assertEquals(14, (int) trie.get("A"));
        assertEquals(8, trie.size());
        assertEquals(14, (int) trie.remove("A"));
        assertNull(trie.get("A"));
        assertEquals(7, trie.size());
        assertNull(trie.remove("B"));
        assertEquals(7, trie.size());
        assertThat(trie.commonPrefixSearch("inn"), CoreMatchers.hasItems(11, 5, 9));
        assertThat(trie.commonPrefixSearch("to"), CoreMatchers.hasItems(7));
        assertTrue(trie.commonPrefixSearch("B").isEmpty());
        assertThat(trie.predictiveSearch("t"), CoreMatchers.hasItems(7, 3, 4, 12));
        assertThat(trie.predictiveSearch("to"), CoreMatchers.hasItems(7));
        assertThat(trie.predictiveSearch("te"), CoreMatchers.hasItems(3, 4, 12));
        assertTrue(trie.predictiveSearch("B").isEmpty());
    }

    @Test
    public void testToString() {
        DoublyChainedTreeTrie<Integer> trie = new DoublyChainedTreeTrie<>();
        trie.put("A", 15);
        trie.put("to", 7);
        trie.put("tea", 3);
        trie.put("ted", 4);
        trie.put("ten", 12);
        trie.put("i", 11);
        trie.put("in", 5);
        trie.put("inn", 9);
        String actual = trie.toString();
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