import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
	private int maxTasksPerServer;
	public ConcreteStrategyQueue(int maxTasksPerServer)
    {
    	this.maxTasksPerServer=maxTasksPerServer;
    }
	@Override
	public int addTask(List<Server> servers, Task t) 
	{
		int min=Integer.MAX_VALUE;
		for(Server s: servers)
		{
			if(min>s.getTasks().length)
			{	
				min=s.getTasks().length;
			}
		}
		for(int i=0;i<servers.size();i++)
		{
			if(min==servers.get(i).getTasks().length)
			{
				servers.get(i).addTask(t,maxTasksPerServer);
				return i;
			}
		}
		return -1;	
	}
}
