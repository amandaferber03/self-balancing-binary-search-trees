package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  public void correctSize() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    assertEquals(3, map.size());
  }

  @Test
  public void testInsert() {
    map.insert("2", "b");
    assertEquals(1, map.size());
    assertEquals("b", map.get("2"));
  }

  @Test
  public void testGet() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    assertEquals("b", map.get("2"));
  }

  @Test
  public void hasWhenTrue() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    assertTrue(map.has("1"));
  }

  @Test
  public void hasWhenFalse() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    assertFalse(map.has("4"));
  }

  @Test
  public void testRemove() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    map.remove("2");
    assertEquals(2, map.size());
    assertFalse(map.has("2"));
  }

  @Test
  public void testPut() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    map.put("2", "Z");
    assertEquals("Z", map.get("2"));
  }

  @Test
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotation() {
    map.insert("7", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        15:a
     */

    map.insert("5", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        15:a,
        10:b null
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        10:b,
        6:c 15:a
     */



    String[] expected = new String[]{
            "5:b",
            "3:c 7:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotation() {
    map.insert("3", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        6:a
     */

    map.insert("7", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        6:a,
        null 15:b
     */

    map.insert("5", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        10:b,
        6:c 15:a
     */

    String[] expected = new String[]{
            "5:c",
            "3:a 7:b"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotation() {
    map.insert("7", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        15:a
     */

    map.insert("3", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        15:a,
        6:b null
     */

    map.insert("5", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        10:b,
        6:c 15:a
     */

    String[] expected = new String[]{
            "5:c",
            "3:b 7:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotationComplex() {
    map.insert("E", "a");
    map.insert("C", "a");
    map.insert("H", "a");
    map.insert("A", "a");
    map.insert("D", "a");
    map.insert("G", "a");
    map.insert("L", "a");
    map.insert("B", "a");
    map.insert("F", "a");
    map.insert("I", "a");
    map.insert("M", "a");
    map.insert("K", "a");
    map.insert("J", "a");//point where right left rotation is needed
    String[] expected = new String[]{
            "E:a",
            "C:a H:a",
            "A:a D:a G:a L:a",
            "null B:a null null F:a null J:a M:a",
            "null null null null null null null null null null null null I:a K:a null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotationComplex() {
    map.insert("E", "a");
    map.insert("C", "a");
    map.insert("I", "a");
    map.insert("A", "a");
    map.insert("D", "a");
    map.insert("H", "a");
    map.insert("K", "a");
    map.insert("B", "a");
    map.insert("F", "a");
    map.insert("J", "a");
    map.insert("L", "a");
    map.insert("G", "a");//point where left right rotation is needed
    String[] expected = new String[]{
            "E:a",
            "C:a I:a",
            "A:a D:a G:a K:a",
            "null B:a null null F:a H:a J:a L:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightRotation() {
    map.insert("E", "a");
    map.insert("B", "a");
    map.insert("I", "a");
    map.insert("A", "a");
    map.insert("D", "a");
    map.insert("G", "a");
    map.insert("J", "a");
    map.insert("C", "a");
    map.insert("F", "a");
    map.insert("H", "a");
    map.remove("J");
    String[] expected = new String[]{
            "E:a",
            "B:a G:a",
            "A:a D:a F:a I:a",
            "null null C:a null null null H:a null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightLeftRotation() {
    map.insert("E", "a");
    map.insert("B", "a");
    map.insert("I", "a");
    map.insert("A", "a");
    map.insert("D", "a");
    map.insert("G", "a");
    map.insert("J", "a");
    map.insert("C", "a");
    map.insert("F", "a");
    map.insert("H", "a");
    map.remove("A");
    String[] expected = new String[]{
            "E:a",
            "C:a I:a",
            "B:a D:a G:a J:a",
            "null null null null F:a H:a null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRightRotation() {
    map.insert("E", "a");
    map.insert("C", "a");
    map.insert("H", "a");
    map.insert("A", "a");
    map.insert("D", "a");
    map.insert("F", "a");
    map.insert("J", "a");
    map.insert("G", "a");
    map.insert("B", "a");
    map.insert("K", "a");
    map.insert("I", "a");
    map.remove("D");
    String[] expected = new String[]{
            "E:a",
            "B:a H:a",
            "A:a C:a F:a J:a",
            "null null null null null G:a I:a K:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRotation() {
    map.insert("D", "a");
    map.insert("B", "a");
    map.insert("G", "a");
    map.insert("A", "a");
    map.insert("C", "a");
    map.insert("E", "a");
    map.insert("I", "a");
    map.insert("F", "a");
    map.insert("H", "a");
    map.insert("J", "a");
    map.remove("E");
    map.remove("F");//the second removal triggers the left rotation
    String[] expected = new String[]{
            "D:a",
            "B:a I:a",
            "A:a C:a G:a J:a",
            "null null null null null H:a null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertExceptionNull() {
    try {
      map.insert(null, "a");
      fail("insert() does not throw an IllegalArgumentException when k is null");
    } catch (IllegalArgumentException ex) {
      System.out.println("insert() correctly throws IllegalArgumentException when k is null");
    }
  }

  @Test
  public void insertExceptionAlreadyMapped() {
    map.insert("1", "a");
    try {
      map.insert("1", "b");
      fail("insert() inserts a key into the AVLTreeMap that has already been mappped");
    } catch (IllegalArgumentException ex) {
      System.out.println("insert() correctly throws IllegalArgumentException when k is already mapped");
    }
  }

  @Test
  public void removeExceptionNull() {
    try {
      map.remove(null);
      fail("remove() does not throw an IllegalArgumentException when k is null");
    } catch (IllegalArgumentException ex) {
      System.out.println("remove() correctly throws IllegalArgumentException when k is null");
    }
  }

  @Test
  public void removeExceptionNotMapped() {
    try {
      map.remove("1");
      fail("remove() does not throw an IllegalArgumentException when k is not mapped");
    } catch (IllegalArgumentException ex) {
      System.out.println("remove() correctly throws IllegalArgumentException when k is not mapped");
    }
  }

  @Test
  public void putExceptionNull() {
    try {
      map.put(null, "a");
      fail("put() does not throw an IllegalArgumentException when k is null");
    } catch (IllegalArgumentException ex) {
      System.out.println("put() correctly throws IllegalArgumentException when k is null");
    }
  }

  @Test
  public void putExceptionNotMapped() {
    try {
      map.put("1", "b");
      fail("put() tried to update a key that has not been mappped");
    } catch (IllegalArgumentException ex) {
      System.out.println("put() correctly throws IllegalArgumentException when k is not mapped");
    }
  }

  @Test
  public void getExceptionNull() {
    try {
      map.get(null);
      fail("get() does not throw an IllegalArgumentException when k is null");
    } catch (IllegalArgumentException ex) {
      System.out.println("get() correctly throws IllegalArgumentException when k is null");
    }
  }

  @Test
  public void getExceptionNotMapped() {
    try {
      map.get("1");
      fail("get() tried to access a key that has not been mappped");
    } catch (IllegalArgumentException ex) {
      System.out.println("get() correctly throws IllegalArgumentException when k is not mapped");
    }
  }

  @Test
  public void testHasNext() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    Iterator itr = map.iterator();
    assertTrue(itr.hasNext());
    for(int i = 0; i < 3; i++) {
      itr.next();
    }
    assertFalse(itr.hasNext());
  }

  @Test
  public void testForEachLoop() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    int counter = 0;
    for (String element: map) {
      System.out.println(map.get(element));
      counter++;
    }
    assertEquals(3, counter);
  }

  @Test
  public void testNext() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    Iterator itr = map.iterator();
    String[] arr = {"1", "2", "3"};
    for(int i = 0; i < arr.length; i++) {
      assertEquals(arr[i], itr.next());
    }
  }
}
