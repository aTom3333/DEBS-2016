package fise2.hpp.ok.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

public class CircularListTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    ArrayList<Integer> l = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    CircularList<Integer> cl = new CircularList<>(l);
    CircularList<Integer> clempty = new CircularList<>();

    @Test
    public void construction() {
        ArrayList<Integer> a = new ArrayList<>();

        // default construction
        Assert.assertEquals(0, clempty.size());
        a.clear();
        for (Integer e : clempty) {
            a.add(e);
        }
        Assert.assertEquals(0, a.size());
        // from list
        Assert.assertEquals(10, cl.size());
        a.clear();
        for (Integer e : cl) {
            a.add(e);
        }
        Assert.assertArrayEquals(a.toArray(), l.toArray());
        // from one element list
        CircularList<Integer> clempty2 = new CircularList<>(Collections.singletonList(0));
        Assert.assertEquals(1, clempty2.size());
        Assert.assertEquals(Integer.valueOf(0), clempty2.curr());
        Assert.assertEquals(Integer.valueOf(0), clempty2.next());
        Assert.assertEquals(Integer.valueOf(0), clempty2.curr());
        // copy const
        CircularList<Integer> clcopy = new CircularList<>(cl);
        Assert.assertEquals(10, clcopy.size());
        a.clear();
        for (Integer e : clcopy) {
            a.add(e);
        }
        Assert.assertArrayEquals(a.toArray(), l.toArray());
    }

    @Test
    public void addAfter() {
        CircularList<Integer> clcopy = new CircularList<>(cl);
        clcopy.addAfter(100);
        Assert.assertEquals(11, clcopy.size());
        Assert.assertEquals(Integer.valueOf(8), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(9), clcopy.curr());
        Assert.assertEquals(Integer.valueOf(100), clcopy.advanceForward());
        Assert.assertEquals(Integer.valueOf(9), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(0), clcopy.next());
        Assert.assertEquals(Integer.valueOf(0), clcopy.advanceForward());
        Assert.assertEquals(Integer.valueOf(100), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(1), clcopy.next());
    }

    @Test
    public void addAfterEmpty() {
        CircularList<Integer> clemptycopy = new CircularList<>(clempty);
        clemptycopy.addAfter(100);
        Assert.assertEquals(1, clemptycopy.size());
        Assert.assertEquals(Integer.valueOf(100), clemptycopy.prev());
        Assert.assertEquals(Integer.valueOf(100), clemptycopy.curr());
        Assert.assertEquals(Integer.valueOf(100), clemptycopy.next());
    }

    @Test
    public void addAfterUnique() {
        CircularList<Integer> clunique = new CircularList<>(Collections.singletonList(0));
        clunique.addAfter(100);
        Assert.assertEquals(2, clunique.size());
        Assert.assertEquals(Integer.valueOf(100), clunique.prev());
        Assert.assertEquals(Integer.valueOf(0), clunique.curr());
        Assert.assertEquals(Integer.valueOf(100), clunique.advanceForward());
        Assert.assertEquals(Integer.valueOf(0), clunique.advanceForward());
    }

    @Test
    public void addBefore() {
        CircularList<Integer> clcopy = new CircularList<>(cl);
        clcopy.addBefore(100);
        Assert.assertEquals(11, clcopy.size());
        Assert.assertEquals(Integer.valueOf(0), clcopy.next());
        Assert.assertEquals(Integer.valueOf(9), clcopy.curr());
        Assert.assertEquals(Integer.valueOf(100), clcopy.advanceBackward());
        Assert.assertEquals(Integer.valueOf(8), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(9), clcopy.next());
        Assert.assertEquals(Integer.valueOf(8), clcopy.advanceBackward());
        Assert.assertEquals(Integer.valueOf(7), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(100), clcopy.next());
    }

    @Test
    public void addBeforeEmpty() {
        CircularList<Integer> clemptycopy = new CircularList<>(clempty);
        clemptycopy.addBefore(100);
        Assert.assertEquals(1, clemptycopy.size());
        Assert.assertEquals(Integer.valueOf(100), clemptycopy.prev());
        Assert.assertEquals(Integer.valueOf(100), clemptycopy.curr());
        Assert.assertEquals(Integer.valueOf(100), clemptycopy.next());
    }

    @Test
    public void addBeforeUnique() {
        CircularList<Integer> clunique = new CircularList<>(Collections.singletonList(0));
        clunique.addBefore(100);
        Assert.assertEquals(2, clunique.size());
        Assert.assertEquals(Integer.valueOf(100), clunique.prev());
        Assert.assertEquals(Integer.valueOf(0), clunique.curr());
        Assert.assertEquals(Integer.valueOf(100), clunique.advanceForward());
        Assert.assertEquals(Integer.valueOf(0), clunique.advanceForward());
    }

    @Test
    public void removeCurr() {
        // cas vide
        Assert.assertFalse(clempty.removeCurr());

        // cas un élément
        CircularList<Integer> clunique = new CircularList<>(Collections.singletonList(0));
        Assert.assertTrue(clunique.removeCurr());
        Assert.assertTrue(clunique.isEmpty());
        Assert.assertEquals(0, clunique.size());
        exception.expect(NullPointerException.class);
        clunique.curr();

        // cas nominal
        CircularList<Integer> clcopy = new CircularList<>(cl);
        Assert.assertTrue(clcopy.removeCurr());
        Assert.assertEquals(9, clcopy.size());
        Assert.assertEquals(Integer.valueOf(8), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(0), clcopy.curr());
        Assert.assertEquals(Integer.valueOf(1), clcopy.next());
    }

    @Test
    public void advanceForward() {
        CircularList<Integer> clcopy = new CircularList<Integer>(cl);
        Assert.assertEquals(Integer.valueOf(0), clcopy.advanceForward());
        Assert.assertEquals(Integer.valueOf(9), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(0), clcopy.curr());
        Assert.assertEquals(Integer.valueOf(1), clcopy.next());
    }

    @Test
    public void advanceBackward() {
        CircularList<Integer> clcopy = new CircularList<>(cl);
        Assert.assertEquals(Integer.valueOf(8), clcopy.advanceBackward());
        Assert.assertEquals(Integer.valueOf(7), clcopy.prev());
        Assert.assertEquals(Integer.valueOf(8), clcopy.curr());
        Assert.assertEquals(Integer.valueOf(9), clcopy.next());
    }

    @Test
    public void next() {
        Assert.assertEquals(Integer.valueOf(0), cl.next());
    }

    @Test
    public void prev() {
        Assert.assertEquals(Integer.valueOf(8), cl.prev());
    }

    @Test
    public void curr() {
        Assert.assertEquals(Integer.valueOf(9), cl.curr());
    }

    @Test
    public void setCurrent() {
        CircularList<Integer> clcopy = new CircularList<>(cl);
        cl.setCurrent(100);
        Assert.assertEquals(Integer.valueOf(100), clcopy.curr());
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(clempty.isEmpty());
        CircularList<Integer> clcopy = new CircularList<>(clempty);
        Assert.assertTrue(clcopy.isEmpty());
    }

    @Test
    public void forEach() {
        // cas vide
        ArrayList<Integer> a = new ArrayList<Integer>();
        clempty.forEach(a::add);
        Assert.assertArrayEquals(Collections.emptyList().toArray(), a.toArray());
        // cas un élément
        a.clear();
        CircularList<Integer> clunique = new CircularList<>(Collections.singletonList(0));
        clunique.forEach(a::add);
        Assert.assertArrayEquals(Arrays.asList(0).toArray(), a.toArray());
        Assert.assertEquals(Integer.valueOf(0), clunique.curr());
        // cas nominal
        a.clear();
        cl.forEach(a::add);
        Assert.assertArrayEquals(cl.toArray(), a.toArray());
        Assert.assertEquals(Integer.valueOf(9), cl.curr());
    }

    @Test
    public void forEachRemaining() {
        Iterator<Integer> iterator = cl.iterator();
        ArrayList<Integer> a = new ArrayList<Integer>();
        iterator.forEachRemaining(a::add);
        Assert.assertArrayEquals(Arrays.asList(9, 0, 1, 2, 3, 4, 5, 6, 7, 8).toArray(), a.toArray());
        Assert.assertEquals(Integer.valueOf(9), cl.curr());

        a.clear();
        iterator = cl.iterator();
        iterator.next();
        iterator.forEachRemaining(a::add);
        Assert.assertArrayEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8).toArray(), a.toArray());
        Assert.assertEquals(Integer.valueOf(9), cl.curr());
    }

    @Test
    public void removeIf() {
        CircularList<Integer> clemptycopy = new CircularList<>(clempty);
        clemptycopy.removeIf(p -> {
            return p == 2;
        });
        Assert.assertEquals(0, clemptycopy.size());
        Assert.assertTrue(clemptycopy.isEmpty());

        CircularList<Integer> clunique = new CircularList<>(new ArrayList<Integer>(Collections.singletonList(2)));
        clunique.removeIf(p -> {
            return p == 2;
        });
        Assert.assertEquals(0, clunique.size());
        Assert.assertTrue(clunique.isEmpty());
        Assert.assertArrayEquals(Collections.emptyList().toArray(), clunique.toArray());

        CircularList<Integer> clnom = new CircularList<>(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 2, 2, 5, 6, 7, 8, 9)));
        clnom.removeIf(p -> {
            return p == 2;
        });
        Assert.assertEquals(7, clnom.size());
        Assert.assertArrayEquals(Arrays.asList(9, 0, 1, 5, 6, 7, 8).toArray(), clnom.toArray());

        CircularList<Integer> clnom2 = new CircularList<>(new ArrayList<Integer>(Arrays.asList(8, 1, 2, 3, 4, 5, 6, 7, 8, 8)));
        clnom2.removeIf(p -> {
            return p == 8;
        });
        Assert.assertEquals(7, clnom2.size());
        Assert.assertArrayEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7).toArray(), clnom2.toArray());
    }

    @Test
    public void iteratorNextNoSuchElementException() {
        Iterator<Integer> iterator = clempty.iterator();
        exception.expect(NoSuchElementException.class);
        iterator.next();
    }

    @Test
    public void iteratorRemove() {
        // TODO
    }

    @Test
    public void toArray() {
        Assert.assertArrayEquals(clempty.toArray(), Collections.emptyList().toArray());
        Assert.assertArrayEquals(Arrays.asList(9, 0, 1, 2, 3, 4, 5, 6, 7, 8).toArray(), cl.toArray());
        Assert.assertEquals(Integer.valueOf(9), cl.curr());
    }

    @Test
    public void equals() {
        Assert.assertFalse(clempty.equals(Collections.emptyList().toArray()));
        Assert.assertTrue(clempty.equals(new CircularList<Integer>(Collections.emptyList())));

        CircularList<Integer> clcopy1 = new CircularList<>(cl);
        CircularList<Integer> clcopy2 = new CircularList<>(cl);
        CircularList<Integer> clother1 = new CircularList<>(new ArrayList<Integer>(Arrays.asList(1, 0, 2, 3, 4, 5, 6, 7, 8, 9)));
        CircularList<Integer> clother2 = new CircularList<>(new ArrayList<Integer>(Arrays.asList(100, 100, 100, 100, 100, 100, 100, 100, 100, 100)));
        Assert.assertTrue(clcopy1.equals(clcopy2));
        Assert.assertEquals(Integer.valueOf(9), clcopy1.curr());
        Assert.assertEquals(Integer.valueOf(9), clcopy2.curr());
        clcopy1.advanceForward();
        Assert.assertTrue(clcopy1.equals(clcopy2));
        Assert.assertEquals(Integer.valueOf(0), clcopy1.curr());
        Assert.assertEquals(Integer.valueOf(9), clcopy2.curr());
        clcopy1.advanceForward();
        Assert.assertTrue(clcopy1.equals(clcopy2));
        Assert.assertEquals(Integer.valueOf(1), clcopy1.curr());
        Assert.assertEquals(Integer.valueOf(9), clcopy2.curr());

        Assert.assertFalse(clcopy1.equals(clother1));
        Assert.assertEquals(Integer.valueOf(1), clcopy1.curr());
        Assert.assertEquals(Integer.valueOf(9), clother1.curr());
        Assert.assertFalse(clcopy1.equals(clother2));
        Assert.assertEquals(Integer.valueOf(1), clcopy1.curr());
        Assert.assertEquals(Integer.valueOf(100), clother2.curr());
    }
}