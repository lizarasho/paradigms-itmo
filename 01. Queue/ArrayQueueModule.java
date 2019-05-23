package queue;

public class ArrayQueueModule {
    private static final int START_SIZE = 10;
    private static int n = START_SIZE;
    private static int size = 0;
    private static int head = 0;
    private static Object[] elements = new Object[START_SIZE];

    // tail = (size + head) % n
    // INV: elements ∈ [head; tail) || elements ∈ [head; n) ∪ [0; tail)
    // INV: ∀ item ∈ elements: item ≠ null
    // INV: head, tail ∈ [0; n)

    // PRE: item ≠ null
    public static void enqueue(Object item) {
        assert item != null;
        checkCapacity();
        elements[(size + head) % n] = item;
        size++;
    }
    // POST: (elements[n - 1] = item && tail = 0) || (elements[tail - 1] = item && 0 < tail < n)
    // POST: (∀ item ∈ elements' : item ∈ elements) && (size = size' + 1)


    // PRE: item ≠ null
    public static void push(Object item) {
        assert item != null;
        checkCapacity();
        head = (head + n - 1) % n;
        elements[head] = item;
        size++;
    }
    // POST: elements[head] = item
    // POST: (∀ item ∈ elements' : item ∈ elements) && (size = size' + 1)


    // PRE: size > 0
    public static Object element() {
        assert size > 0;
        return elements[head];
    }
    // POST: ℝ = elements[head] && size = size' && elements = elements


    // PRE: size > 0
    public static Object peek() {
        assert size > 0;
        return elements[(size + head - 1) % n];
    }
    // POST: (ℝ = elements[n - 1] && tail = 0) || (ℝ = elements[tail - 1] && 0 < tail < n)
    // POST: size = size' && elements = elements'


    // PRE: size > 0
    public static Object dequeue() {
        assert size > 0;
        Object item = elements[head];
        head = (head + 1) % n;
        size--;
        return item;
    }
    // POST: ℝ = elements[head] && size = size' - 1 && elements = elements' \ { ℝ }

    // PRE: size > 0
    public static Object remove() {
        assert size > 0;
        size--;
        return elements[(size + head) % n];
    }
    // POST: (ℝ = elements[n - 1] && tail = 0) || (ℝ = elements[tail - 1] && 0 < tail < n)
    // POST: size = size' - 1 && elements = elements' \ { ℝ }


    public static int size() {
        return size;
    }
    // POST: (ℝ = tail - head && elements ∈ [head; tail)) || (ℝ = n + tail - head && elements ∈ [head; n) ∪ [0; tail))
    // POST: size = size' && elements = elements'


    public static boolean isEmpty() {
        return size == 0;
    }
    // POST: ℝ = (size == 0) && size = size' && elements = elements'


    public static void clear() {
        elements = new Object[START_SIZE];
        head = size = 0;
        n = START_SIZE;
    }
    // POST: size = 0 && elements = null

    // PRE: elements[n]
    private static void checkCapacity() {
        if (size == n - 1) {
            Object[] newQueue = new Object[2 * n];
            for (int i = 0; i < n - 1; i++) {
                newQueue[i] = elements[head];
                head = (head + 1) % n;
            }
            elements = newQueue;
            head = 0;
            n *= 2;
        }
    }
    // POST: elements[n] || elements[2 * n]
}
