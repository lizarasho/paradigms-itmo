package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    protected void enqueueImpl(Object item) {
        if (size == 0) {
            head = tail = new Node(item, null);
        }
        else {
            tail.next = new Node(item, null);
            tail = tail.next;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void deleteFirstImpl() {
        head = head.next;
        if (size == 1) {
            tail = null;
        }
    }

    protected void clearImpl() {
        head = null;
    }

    protected AbstractQueue copy() {
        LinkedQueue result = new LinkedQueue();
        Node curHead = head;
        while (curHead != null) {
            result.enqueue(curHead.value);
            curHead = curHead.next;
        }
        return result;
    }

    private class Node {
        Object value;
        Node next;

        public Node(Object value, Node next) {
            assert value != null;
            this.value = value;
            this.next = next;
        }
    }
}