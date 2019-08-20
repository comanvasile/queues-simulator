import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    private int maxTasksPerServer;
	public ConcreteStrategyTime(int maxTasksPerServer)
    {
    	this.maxTasksPerServer=maxTasksPerServer;
    }

	@Override
	public int addTask(List<Server> servers, Task t) {
		int min=Integer.MAX_VALUE;
		for(Server s: servers)
		{
			if(s.getWaitingPeriod().intValue()<min && s.getTasks2().size()<maxTasksPerServer)
			{
				min=s.getWaitingPeriod().get();
			}
		}
		for(int i=0;i<servers.size();i++)
		{
			if(min==servers.get(i).getWaitingPeriod().intValue())
			{
				servers.get(i).addTask(t,maxTasksPerServer);
				return i;
			}
		}
		return -1;
	}

}
