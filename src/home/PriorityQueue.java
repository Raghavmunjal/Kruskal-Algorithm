package home;

import java.util.Comparator;

public class PriorityQueue<T>{

    private int cap;
    private int curr_size;
    private T pq[];
    private Comparator<T> comparator;

    PriorityQueue(int n){
        cap = n;
        pq = (T[])new Object[n];
        curr_size = 0;
    }

    PriorityQueue(int n, Comparator<T> cmp) {
        comparator = cmp;
        pq = (T[]) new Object[n];
        curr_size = 0;
        cap  = n;
    }

    private int left(int i){
        return 2*i + 1;
    }
    private int right(int i){
        return 2*i + 2;
    }
    private int parent(int i){
        return (i-1)/2;
    }

    // insertion
    public void add(T val){

        if(curr_size == cap){
            return;
        }

        pq[curr_size] = val;
        int i = curr_size;

        // swim
        while(i!=0 && greater(parent(i),i)){
            swap(parent(i),i);
            i = parent(i);
        }
        curr_size++;

    }

    // extract minimum value
    public T poll(){

        if (curr_size <= 0){
            return null;
        }

        if (curr_size == 1)
        {
            curr_size--;
            return pq[0];
        }
        T temp = pq[0];
        swap(0,curr_size-1);

        curr_size--;

        heapify(0);
        return temp;
    }

    private void heapify(int i) {

        int lt = left(i);
        int rt = right(i);
        int smallest = i;
        if (lt < curr_size && greater(i,lt))
            smallest = lt;
        if (rt < curr_size && greater(smallest,rt))
            smallest = rt;
        if (smallest != i)
        {
            swap(i,smallest);
            // sink
            heapify(smallest);
        }

    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<T>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void swap(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    public boolean isEmpty(){
        return curr_size == 0;
    }

    public int size(){
        return curr_size;
    }


}
