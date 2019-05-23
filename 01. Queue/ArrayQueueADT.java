package queue;

public class ArrayQueueADT {
    private static final int START_SIZE = 10;
    private int n = START_SIZE;
    private int size = 0;
    private int head = 0;
    private Object[] elements = new Object[START_SIZE];

    // tail = (size + head) % n
    // INV: elements ∈ [head; tail) || elements ∈ [head; n) ∪ [0; tail)
    // INV: ∀ item ∈ elements: item ≠ null
    // INV: head, tail ∈ [0; n)


    // PRE: item ≠ null
    public static void enqueue(ArrayQueueADT queue, Object item) {
        assert item != null;
        checkCapacity(queue);
        queue.elements[(queue.size + queue.head) % queue.n] = item;
        queue.size++;
    }
    // POST: (elements[n - 1] = item && tail = 0) || (elements[tail - 1] = item && 0 < tail < n)
    // POST: (∀ item ∈ elements' : item ∈ elements) && (size = size' + 1)


    // PRE: item ≠ null
    public static void push(ArrayQueueADT queue, Object item) {
        assert item != null;
        checkCapacity(queue);
        queue.head = (queue.head + queue.n - 1) % queue.n;
        queue.elements[queue.head] = item;
        queue.size++;
    }
    // POST: elements[head] = item
    // POST: (∀ item ∈ elements' : item ∈ elements) && (size = size' + 1)


    // PRE: size > 0
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }
    // POST: ℝ = elements[head] && size = size' && elements = elements


    // PRE: size > 0
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.size + queue.head - 1) % queue.n];
    }
    // POST: (ℝ = elements[n - 1] && tail = 0) || (ℝ = elements[tail - 1] && 0 < tail < n)
    // POST: size = size' && elements = elements'


    // PRE: size > 0
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object item = queue.elements[queue.head];
        queue.head = (queue.head + 1) % queue.n;
        queue.size--;
        return item;
    }
    // POST: ℝ = elements[head] && size = size' - 1 && elements = elements' \ { ℝ }


    // PRE: size > 0
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.size--;
        return queue.elements[(queue.size + queue.head) % queue.n];
    }
    // POST: (ℝ = elements[n - 1] && tail = 0) || (ℝ = elements[tail - 1] && 0 < tail < n)
    // POST: size = size' - 1 && elements = elements' \ { ℝ }


    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    // POST: (ℝ = tail - head && elements ∈ [head; tail)) || (ℝ = n + tail - head && elements ∈ [head; n) ∪ [0; tail))
    // POST: size = size' && elements = elements'


    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    // POST: ℝ = (size == 0) && size = size' && elements = elements'

    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[10];
        queue.head = queue.size = 0;
        queue.n = START_SIZE;
    }
    // POST: size = 0 && elements = null

    // PRE: elements[n]
    private static void checkCapacity(ArrayQueueADT queue) {
        if (queue.size == queue.n - 1) {
            Object[] newQueue = new Object[2 * queue.n];
            for (int i = 0; i < queue.n - 1; i++) {
                newQueue[i] = queue.elements[queue.head];
                queue.head = (queue.head + 1) % queue.n;
            }
            queue.elements = newQueue;
            queue.head = 0;
            queue.n *= 2;
        }
    }
    // POST: elements[n] || elements[2 * n]
}
