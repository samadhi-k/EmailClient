package Assignment.Assignment;

import java.util.LinkedList;
import javax.mail.Message;

class MyBlockingQueue {
		
	  void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	private int maxQueueSize;
	LinkedList<Message> list;
	
	// can change the limit of the queue according to correspondence
	MyBlockingQueue(int limit){
		setMaxQueueSize(limit);
		list = new LinkedList<Message>();
	}

	synchronized void enqueue( Message mail) {
		// wait while the queue is full 
		while (list.size() == maxQueueSize) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println();
			}
		}
		list.add(mail);
		notifyAll();		
	}
	
	synchronized Message dequeue(Boolean running) {
		// wait while the queue is empty
		while(list.size() ==  0) {
			try {
				if (running)
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}
		Message m = list.removeFirst();
		notifyAll();
		return  m;
		
	}
}

