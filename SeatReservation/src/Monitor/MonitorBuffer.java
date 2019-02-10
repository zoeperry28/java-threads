package Monitor;

//Part 1 — Monitors method
//
//Write a Java program using synchronized methods and guards (ie a monitor) that solves the seat reservation 
//problem for two booking offices.
//
//Extend your solution so that it works for 5 booking offices. Increase the total seats to 500, and allow each 
//office to book 100 seats. Use Thread.sleep(100 * officeID) as the delay between bookings so that the offices 
//work at different rates. 

class Producer extends Thread {
  MonitorBuffer _b;

  public Producer(MonitorBuffer buff) {
    _b = buff;
  }

  public void run() {
    for (int i=1; i<=500; i++) {
      try {
        _b.place(i);       // place an item
        System.out.println("Producer puts ticket " + i + 
                           " in the buffer " + _b.getBID());
        Thread.sleep(500);
      } catch(InterruptedException e) { }
    }
  }
}

class Consumer1 extends Thread {
  MonitorBuffer _b;

  public Consumer1(MonitorBuffer buff) {
    _b=buff;
  }

  public void run() {
    int item=0;
    for (int i=1; i <= 100; i++) {
      try {
        item = _b.take(); // take an item
        System.out.println("Office 1 takes ticket" + item + 
                           " out of the buffer " + _b.getBID());
        Thread.sleep(100 * _b.getBID());
      } catch(InterruptedException e) { }
    }
  }
}

class Consumer2 extends Thread {
	  MonitorBuffer _b;

	  public Consumer2(MonitorBuffer buff) {
	    _b=buff;
	  }

	  public void run() {
	    int item=0;
	    for (int i=1; i <= 100; i++) {
	      try {
	        item = _b.take(); // take an item
	        System.out.println("Office 2 takes ticket" + item + 
	                           " out of the buffer " + _b.getBID());
	        Thread.sleep(100 * _b.getBID());
	      } catch(InterruptedException e) { }
	    }
	  }
	}

class Consumer3 extends Thread {
	  MonitorBuffer _b;

	  public Consumer3(MonitorBuffer buff) {
	    _b=buff;
	  }

	  public void run() {
	    int item=0;
	    for (int i=1; i <= 100; i++) {
	      try {
	        item = _b.take(); // take an item
	        System.out.println("Office 2 takes ticket" + item + 
	                           " out of the buffer " + _b.getBID());
	        Thread.sleep(100 * _b.getBID());
	      } catch(InterruptedException e) { }
	    }
	  }
	}
class Consumer4 extends Thread {
	  MonitorBuffer _b;

	  public Consumer4(MonitorBuffer buff) {
	    _b=buff;
	  }

	  public void run() {
	    int item=0;
	    for (int i=1; i <= 100; i++) {
	      try {
	        item = _b.take(); // take an item
	        System.out.println("Office 2 takes ticket " + item + 
	                           " out of the buffer " + _b.getBID());
	        Thread.sleep(100 * _b.getBID());
	      } catch(InterruptedException e) { }
	    }
	  }
	}
class Consumer5 extends Thread {
	  MonitorBuffer _b;

	  public Consumer5(MonitorBuffer buff) {
	    _b=buff;
	  }

	  public void run() {
	    int item=0;
	    for (int i=1; i <= 100; i++) {
	      try {
	        item = _b.take(); // take an item
	        System.out.println("Office 2 takes ticket" + item + 
	                           " out of the buffer " + _b.getBID());
	        Thread.sleep(100 * _b.getBID());
	      } catch(InterruptedException e) { }
	    }
	  }
	}


public class MonitorBuffer {
  public static final int BUFFSIZE = 5;
  private int[] _b = new int[BUFFSIZE];
  private final char _bid;
  private int _count;
  private int _nextIn, _nextOut;

  public MonitorBuffer(char b) { _bid=b; _count=0; _nextIn=0; _nextOut=0; }

  public char getBID () {
    return _bid;
  }

  public synchronized void place(int item)
    throws InterruptedException {
    while (_count >= BUFFSIZE) wait();
    _b[_nextIn] = item;
    ++_count;
    _nextIn = ++_nextIn % BUFFSIZE;
    notifyAll();
  }

  public synchronized int take()
    throws InterruptedException {
    int item;
    while (_count == 0) wait();
    item = _b[_nextOut];
    _nextOut = ++_nextOut % BUFFSIZE;
    --_count;
    notifyAll();
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


  public static void main(String[] args) {
    MonitorBuffer buff1=new MonitorBuffer('A');
    Producer p1 = new Producer(buff1);
    Consumer1 c1 = new Consumer1(buff1);
    Consumer2 c2 = new Consumer2(buff1);
    Consumer3 c3 = new Consumer3(buff1);
    Consumer4 c4 = new Consumer4(buff1);
    Consumer5 c5 = new Consumer5(buff1);
   
    
    p1.start(); c1.start(); c2.start(); c3.start(); c4.start(); c5.start();

    // MonitorBuffer buff2=new MonitorBuffer('B');
    // Producer p2 = new Producer(buff2);
    // Consumer c2 = new Consumer(buff2);

    // c2.start(); p2.start();
  }
}