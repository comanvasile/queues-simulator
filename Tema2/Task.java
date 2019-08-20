
public class Task implements Comparable<Task>{
	private int arrivalTime;
	private int processingTime;
	private int finishTime;
	public Task(int arrivalTime,int processingTime)
	{
		this.arrivalTime=arrivalTime;
		this.processingTime=processingTime;
		finishTime=this.arrivalTime+this.processingTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	public Task() {
		
	}
	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	@Override
	public String toString()
	{
		String s=new String("Taskul cu arrivalTime "+this.getArrivalTime()+" si processingTime "+this.getProcessingTime()+" \n");
		return s;
	}
	@Override
	public int compareTo(Task t)
	{
		if(this.getArrivalTime()>t.getArrivalTime())
			return 1;
		else if(this.getArrivalTime()<t.getArrivalTime())
			return -1;
		else return 0;
	}
}
