// Here we are going to create a Mini/Small real life data centre simulation.
class Server {
    protected String hostname;
    protected String ipAddress;
    protected int cpuCores;
    protected int ramGB;
    protected int diskGB;
    protected boolean isRunning;
    protected int cpuUsagePercet;
    protected int memoryUsesPercent;

    public Server (String hostname, String ipAddress, int cpuCores, int ramGB, int diskGB, boolean isRunning, int cpuUsagePercet, int memoryUsesPercent) {
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.cpuCores = cpuCores;
        this.ramGB = ramGB;
        this.diskGB = diskGB;
        this.isRunning = false;
        this.cpuUsagePercet = 0;
        this.memoryUsesPercent = 0;

        System.out.println(" Server provisioned: " + hostname);
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            cpuUsagePercet = 10;
            memoryUsesPercent = 15;
            System.out.println("🚀 " + hostname + " STARTED");
        }
        else {
            System.out.println("⚠️ " + hostname + " already running");
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            cpuUsagePercet = 0;
            memoryUsesPercent = 0;
            System.out.println("🛑 " + hostname + " STOPPED");
        }
        else {
            System.out.println("⚠️ " + hostname + " already stopped");
        }
    }

    public void restart() {
        System.out.println("Restarting " + hostname + "~~~~");
        stop();
        try {
            Thread.sleep(1000); //it's Simulate restart delay
        } 
        catch (InterruptedException e) {}
        start();
    }

    public void checkHealth() {
        System.out.println("\nHEALTH CHECK: " + hostname);
        System.out.println("Status: " + (isRunning ? "🟢 UP" : "🛑 DOWN"));
        System.out.println("CPU Usage: " + cpuUsagePercet + "%");
        System.out.println("Memory Usage: " + memoryUsesPercent + "%");

        if (cpuUsagePercet > 80 ) {
            System.out.println("⚠️ HIGH CPU ALERT!");
        }
        if (memoryUsesPercent > 80) {
            System.out.println("⚠️ HIGH MEMORY ALERT!");
        }
    }

    public void displayinfo() {
        System.out.println("\n===========================");
        System.out.println(" " + hostname);
        System.out.println("");
    }

}