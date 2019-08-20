import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
	private BlockingQueue<Task> tasks;
	private AtomicInteger waitingPeriod;
	
	public Server(int maxTasksPerServer)
	{
		waitingPeriod=new AtomicInteger(0);
		tasks=new LinkedBlockingQueue<Task>(maxTasksPerServer);	
	}
	public void addTask(Task newTask,int maxTasksPerServer)
	{
		if(tasks.size()<maxTasksPerServer)
		{
			tasks.add(newTask);
			newTask.setFinishTime(newTask.getArrivalTime()+newTask.getProcessingTime()+waitingPeriod.intValue());
			waitingPeriod.addAndGet(newTask.getProcessingTime());
		}
	}
	public void run()
	{
		while(true)
		{
			try 
			{
			if(tasks.isEmpty()==false)
			{
			Task t=new Task();
			t=tasks.element();
			int time=t.getProcessingTime();
			while(time!=0)
			{
				Thread.sleep(1000);
				time--;
				t.setProcessingTime(time);
				waitingPeriod.decrementAndGet();
			}
			tasks.take();
			}
			}
			catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	public Task[] getTasks()
	{
		Task[] t=new Task[tasks.size()];
		tasks.toArray(t);
		return t;
	}
	public AtomicInteger getWaitingPeriod() {
		return waitingPeriod;
	}
	public BlockingQueue<Task> getTasks2() {
		return tasks;
	}
	
	
}
