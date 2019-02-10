package Semaphore;

class Producer extends Thread{
  SemaphoreBuffer _b;
  Semaphore _mx; Semaphore _ready; Semaphore _spaces;

  public Producer(SemaphoreBuffer buff, Semaphore mutEx,
                  Semaphore itemsReady, Semaphore spacesLeft){
    _b = buff;
    _mx = mutEx;
    _ready = itemsReady;  _spaces = spacesLeft;
  }
  public void run() {
    for (int i=1; i<=100; ++i) {
      try {
        _spaces.acquire();       // wait for spaces
        _mx.acquire();           // wait for mutual exclusion
        _b.place(i);          // place an item
        //        System.out.println(_b);
        System.out.println("SEAT " + i + " IS AVAILABLE.");
        _mx.release();         // release mutual exclusion
        _ready.release();      // signal the producer
        Thread.sleep(500);
      } catch(InterruptedException e) { }
    }
  }
}

class Consumer1 extends Thread {
  SemaphoreBuffer _b;
  Semaphore _mx; Semaphore _ready; Semaphore _spaces;

  public Consumer1(SemaphoreBuffer buff, Semaphore mutEx,
                  Semaphore itemsReady, Semaphore spacesLeft) {
    _b=buff; _mx=mutEx; _ready=itemsReady; _spaces=spacesLeft;
  }
  public void run() {
    int item = -999;
    for (int i=1; i <= 60; ++i) {
      try {
        _ready.acquire();          // wait for items ready
        _mx.acquire();             // wait for mutual exclusion
        //        System.out.println(_b);
        item = _b.take();      // taking an item
        System.out.println("Seat " + item + " booked by office 1.");
        _mx.release();           // release mutual exclusion
        _spaces.release();       // signal the producer
        Thread.sleep(1500);
      } catch(InterruptedException e) { }
    }
  }
}

class Consumer2 extends Thread {
	  SemaphoreBuffer _b;
	  Semaphore _mx; Semaphore _ready; Semaphore _spaces;

	  public Consumer2(SemaphoreBuffer buff, Semaphore mutEx,
	                  Semaphore itemsReady, Semaphore spacesLeft) {
	    _b=buff; _mx=mutEx; _ready=itemsReady; _spaces=spacesLeft;
	  }
	  public void run() {
	    int item = -999;
	    for (int i=1; i <= 40; ++i) {
	      try {
	        _ready.acquire();          // wait for items ready
	        _mx.acquire();             // wait for mutual exclusion
	        //        System.out.println(_b);
	        item = _b.take();      // taking an item
	        System.out.println("Seat " + item + " booked by office 2.");
	        _mx.release();           // release mutual exclusion
	        _spaces.release();       // signal the producer
	        Thread.sleep(1000);
	      } catch(InterruptedException e) { }
	    }
	  }
	}

class Semaphore {
  // Implementation for demonstration purposes
  private int _value;
  public Semaphore (int initial) { _value = initial; }
  synchronized public void release() {
    ++_value;
    this.notify();
  }
  synchronized public void acquire()
    throws InterruptedException {
    while (_value == 0) this.wait();
    --_value;
  }
}  // Semaphore

public class SemaphoreBuffer {
  public static final int BUFFSIZE = 5; // constant
  private int[] _b = new int[BUFFSIZE];
  private int _nextIn, _nextOut;

  public SemaphoreBuffer() { _nextIn = 0; _nextOut = 0; }

  public  void place(int item){ // not synchronized
    _b[_nextIn] = item;
    _nextIn = ++_nextIn % BUFFSIZE;
  }

  public int take(){ // not synchronized
    int item = _b[_nextOut];
    _nextOut = ++_nextOut % BUFFSIZE;
    return item;
  }

  public String toString() {
    StringBuilder st = new StringBuilder("[");
    for (int i=0; i<BUFFSIZE; ++i) {
      st.append(_b[i]);
      if (i+1<BUFFSIZE) st.append(" ");
    }
    st.append("]");
    return st.toString();
  }

  public static void main(String[] args){
    SemaphoreBuffer buff = new SemaphoreBuffer();
    Semaphore MutEx = new Semaphore(1); // for mutual exclusion
    Semaphore ItemsReady = new Semaphore(0);
    Semaphore SpacesLeft = new Semaphore(5);
    Producer p = new Producer(buff, MutEx, ItemsReady, SpacesLeft);
    Consumer1 c1 = new Consumer1(buff, MutEx, ItemsReady, SpacesLeft);
    Consumer2 c2 = new Consumer2(buff, MutEx, ItemsReady, SpacesLeft);
    
    p.start(); c1.start(); c2.start();
  }
}
