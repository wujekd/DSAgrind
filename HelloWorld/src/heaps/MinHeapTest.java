package heaps;

import java.util.PriorityQueue;

public class MinHeapTest {
    public static void main(String[] args) {
        // Create a min-heap (PriorityQueue sorts in ascending order by default)
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Add some numbers
        pq.add(42);
        pq.add(5);
        pq.add(17);
        pq.add(23);

        // Remove and print them in sorted (ascending) order
        while (!pq.isEmpty()) {
            int smallest = pq.poll(); // removes the smallest element
            System.out.println(smallest);
        }
    }
}
