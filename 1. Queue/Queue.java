package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {

    // PRE: item ≠ null
    void enqueue(Object item);
    //POST: size = size' + 1 && elements = elements' ∪ item

    // PRE: size > 0
    Object element();
    // POST: ℝ = elements[head] && size = size' && elements = elements'

    // PRE: size > 0
    Object dequeue();
    // POST: ℝ = elements[head] && size = size' - 1 && elements = elements' \ { ℝ }

    int size();
    //POST: ℝ = size

    boolean isEmpty();
    // POST: ℝ = (size == 0)

    void clear();
    // POST: size = 0 && queue = null

    // PRE: elements = queue
    Object[] toArray();
    // POST: elements = Object[]

    AbstractQueue filter(Predicate<Object> predicate);
    // POST: ℝ = queue' : ∀ item ∈ queue : predicate(item) ∈ queue'

    AbstractQueue map(Function<Object, Object> function);
    // POST: ℝ = queue' : ∀ item ∈ queue : function(item) ∈ queue'
}