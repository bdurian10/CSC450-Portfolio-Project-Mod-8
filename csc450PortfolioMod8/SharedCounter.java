package csc450PortfolioMod8;

public class SharedCounter {
	private int count = 0;
	private final int MAX_COUNT = 20;
	
	//Synchronized keyword ensures only one thread
	//Can execute these methods at a time
	public synchronized void increment()
	{
		//Increment counter until reaching MAX_COUNT
		while(count < MAX_COUNT)
		{
			count++;
			System.out.println("Incrementing: " + count);
			//Sleep to simulate work
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
		//Notify other thread that incrementing is complete
		notify();
		
		//Wait for other thread to finish execution
		try
		{
			wait();
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	//Synchronized keyword ensures only one thread can
	//Execute these methods at a time
	public synchronized void decrement()
	{
		//Decrement counter to 0
		while(count > 0)
		{
			count--;
			System.out.println("Decrementing: " + count);
			
			//Sleep to simulate work
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
		//Notify other thread execution is complete
		notify();
	}
	
	//Main method for serially modifying a shared counter
	public static void main(String[] args)
	{
		SharedCounter counter = new SharedCounter();
		
		//Use method references to create threads with run methods
		//That call SharedCounter's increment and decrement methods
		Thread incrementThread = new Thread(counter::increment);
		Thread decrementThread = new Thread(counter::decrement);
		
		/*incrementThread starts first, with synchronized methods
		 *ensuring incrementing completes before
		 *decrementThread begins working
		 */
		incrementThread.start();
		decrementThread.start();
		
		try
		{
			incrementThread.join();
			decrementThread.join();
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}
