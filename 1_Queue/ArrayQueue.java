package queue;

public class ArrayQueue extends AbstractQueue {
    private final int START_SIZE = 10;
    private int n = START_SIZE;
    private int head = 0;
    private Object[] elements;

    public ArrayQueue() {
        elements = new Object[START_SIZE];
    }

    public ArrayQueue(int newZeroSize) {
        elements = new Object[newZeroSize];
        n = START_SIZE;
    }

    protected void enqueueImpl(Object item) {
        checkCapacity();
        elements[(size + head) % n] = item;
    }

    public void push(Object item) {
        assert item != null;
        checkCapacity();
        head = (head + n - 1) % n;
        elements[head] = item;
        size++;
    }

    protected Object elementImpl() {
        return elements[head];
    }

    public Object peek() {
        assert size > 0;
        return elements[(size + head - 1) % n];
    }

    protected void deleteFirstImpl() {
        head = (head + 1) % n;
    }

    public Object remove() {
        assert size() > 0;
        size--;
        return elements[(size + head) % n];
    }

    protected void clearImpl() {
        n = START_SIZE;
        elements = new Object[n];
        head = 0;
    }

    private void checkCapacity() {
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

    protected AbstractQueue copy() {
        ArrayQueue result = new ArrayQueue(n);
        result.size = size;
        result.n = n;
        result.head = head;
        System.arraycopy(elements, 0, result.elements, 0, n);
        return result;
    }
}
