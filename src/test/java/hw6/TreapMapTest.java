package hw6;

import hw6.bst.TreapMap;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {
  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>(50);
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
  public void getPriorities() {
    Random rand = new Random(50);
    for (int i = 1; i <= 10; i++) {
      System.out.println(i + ": " + rand.nextInt());
    }
  }

  @Test
  public void insertLeftRotationSimple() {
    map.insert("1", "a");
    map.insert("2", "a");
    String[] expected = new String[]{
            "2:a:-1727040520",
            "1:a:-1160871061 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotationSimple() {
    map.insert("D", "a");
    map.insert("B", "a");
    String[] expected = new String[]{
            "B:a:-1727040520",
            "null D:a:-1160871061"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRotationComplex() {
    map.insert("1", "a");
    map.insert("2", "a");
    map.insert("3", "a");
    map.insert("4", "a");
    map.insert("5", "a");//point where left rotation is needed
    String[] expected = new String[]{
            "2:a:-1727040520",
            "1:a:-1160871061 3:a:-1657178909",
            "null null null 5:a:-1625295794",
            "null null null null null null 4:a:-765924271 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotationComplex() {
    map.insert("E", "a");
    map.insert("C", "a");//initial right rotation required
    map.insert("H", "a");
    map.insert("L", "a");
    map.insert("J", "a");//point where right rotation is triggered
    String[] expected = new String[]{
            "C:a:-1727040520",
            "null H:a:-1657178909",
            "null null E:a:-1160871061 J:a:-1625295794",
            "null null null null null null null L:a:-765924271"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertMultipleLeftRotations() {
    map.insert("1", "a");
    map.insert("2", "a");
    map.insert("3", "a");
    map.insert("4", "a");
    map.insert("5", "a");
    map.insert("6", "a");
    map.insert("7", "a");//point where multiple rotations are required
    String[] expected = new String[]{
            "7:a:-2137358024",
            "2:a:-1727040520 null",
            "1:a:-1160871061 3:a:-1657178909 null null",
            "null null null 5:a:-1625295794 null null null null",
            "null null null null null null 4:a:-765924271 6:a:2102979123 null null null null null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRotation() {
    map.insert("1", "a");
    map.insert("2", "a");
    map.insert("3", "a");
    map.insert("4", "a");
    map.insert("5", "a");
    map.insert("6", "a");
    map.remove("3");
    String[] expected = new String[]{
            "2:a:-1727040520",
            "1:a:-1160871061 5:a:-1625295794",
            "null null 4:a:-765924271 6:a:2102979123"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightRotation() {
    map.insert("1", "a");
    map.insert("2", "a");
    map.insert("3", "a");
    map.insert("4", "a");
    map.insert("5", "a");
    map.insert("6", "a");
    map.remove("5"); //requires a right rotation (followed by a left rotation)
    String[] expected = new String[]{
            "2:a:-1727040520",
            "1:a:-1160871061 3:a:-1657178909",
            "null null null 4:a:-765924271",
            "null null null null null null null 6:a:2102979123"
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