package Dijks

import models.Vertex

class MinHeap {

    private static final int INIT_SIZE = 1000
    private Vertex[] heap
    private int size

    public MinHeap(){
        heap = new Vertex[INIT_SIZE]
        size = 0
    }

    public void enqueue(Vertex vert){
        if(size == heap.length){
            recreate()
        }
        heap[size] = vert
        vert.setPosInHeap(size)
        size ++
        siftUp(size-1)
    }

    public Vertex dequeue(){
        Vertex temp;
        if(isEmpty()){
            return null
        }else {
            temp = heap[0]
            heap[0] = heap[size-1]
            size--
            if(size > 0)
                siftDown(0)
        }
        temp
    }

    public Vertex peek(){
        if(!isEmpty()){
            return heap[0]
        }else {
            return null
        }
    }

    public void update(int position){
        siftUp(position)
        siftDown(position)
    }

    private void siftUp(int position){
        int parent
        Vertex temp
        if(position != 0){
            parent = parentIndex(position)
            if(heap[parent].compareTo(heap[position]) > 0){
                temp = heap[parent]
                heap[parent] = heap[position]
                heap[position] = temp
                heap[parent].setPosInHeap(position)
                heap[position].setPosInHeap(position)
                siftUp(parent)
            }
        }
    }

    private void siftDown(int position){
        int right,left,min = -1
        Vertex temp
        left = leftChildIndex(position)
        right = rightChildIndex(position)

        if(right >= size){
            if(left < size)
                min = left
        }else {
            if(heap[left].compareTo(heap[right]) <= 0){
                min = left
            }else {
                min = right
            }
        }

        if(min != -1 && heap[position].compareTo(heap[min]) > 0){
            temp = heap[min]
            heap[min] = heap[position]
            heap[min].setPosInHeap(min)
            heap[position] = temp
            heap[position].setPosInHeap(position)
            siftDown(min)
        }
    }

    public boolean isEmpty(){
        return size <= 0
    }

    private static final int leftChildIndex(int position){
        return 2 * position + 1
    }

    private static final int rightChildIndex(int position){
        return 2 * position + 2
    }

    private static final int parentIndex(int position){
        return ((position - 1) / 2)
    }

    private void recreate(){
        def backup = heap
        heap = new Vertex[heap.length * 2]
        backup.each {x->
            if(x)
                enqueue(x)
        }
    }

    public void print(){
        heap.each {x->
            if(x)
                println x.dijksDistance + ":::::::" + x.url
        }
    }

}