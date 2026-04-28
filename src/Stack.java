class Stack<T> {

    private int nStackPointer;
    private Object[] theStack = null;

    @SuppressWarnings("unchecked")
    public T pop() {
        T obj = null;
        if (nStackPointer > 0) {
            obj = (T) theStack[nStackPointer - 1];
            theStack[nStackPointer-- -1] = null;
        }
        return obj;
    }

    public void push( T tobj ){
        theStack[nStackPointer++] = tobj;
    }

    public void empty(){
        while(nStackPointer>0){
            pop();
        }
    }

    public Stack( int stack_size ) {
        theStack = new Object[stack_size];
        nStackPointer = 0;
    }
}