package queue;

import java.util.function.Predicate;
import java.util.function.Function;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    public void enqueue(Object item) {
        assert item != null;
        enqueueImpl(item);
        size++;
    }
    protected abstract void enqueueImpl(Object item);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }
    protected abstract Object elementImpl();

    public Object dequeue() {
        assert size > 0;
        Object result = element();
        deleteFirstImpl();
        size--;
        return result;
    }
    protected abstract void deleteFirstImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        clearImpl();
        size = 0;
    }
    protected abstract void clearImpl();

    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = dequeue();
            enqueue(result[i]);
        }
        return result;
    }

    public AbstractQueue filter(Predicate<Object> predicate) {
        AbstractQueue result = copy();
        for (int i = 0; i < size; i++) {
            Object curItem = result.dequeue();
            if (predicate.test(curItem)) {
                result.enqueue(curItem);
            }
        }
        return result;
    }

    public AbstractQueue map(Function<Object, Object> function) {
        AbstractQueue result = copy();
        for (int i = 0; i < size; i++) {
            result.enqueue(function.apply(result.dequeue()));
        }
        return result;
    }

    protected abstract AbstractQueue copy();
}