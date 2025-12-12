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

    public Server (String hostname, String ipAddress, int cpuCores, int ramGB, int diskGB) {
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
        System.out.println("===========================");
        System.out.println(" IP: " + ipAddress);
        System.out.println(" CPU: " + cpuCores + " cores");
        System.out.println(" RAM: " + ramGB + "GB");
        System.out.println(" DISK: " + diskGB + "GB");
        System.out.println(" STATUS: " + (isRunning ? "RUNNING 🟢" : "STOPPED 🛑"));
        System.out.println("╚════════════════════════════════╝");
    }

}
// now here we are going to make some classes like WEBSERVER, DATABASE_SERVER, LOAD_BALANCER etc:

class WebServer extends Server { 
    private int port;
    private int activeConnections;
    private String webFramework;
    
    public WebServer(String hostname, String ipAddress, int cpuCores, int ramGB, int diskGB,int port) {
    super(hostname, ipAddress, cpuCores, ramGB, diskGB);
    this.port = port;
    this.activeConnections = 0;
    this.webFramework = "Sora";
    }

    @Override
    public void start() {
        super.start();
        System.out.println("🌐 Web server listening on port " + port);
    }

    public void handleRequest (String clientIP) {
        if (isRunning) {
            activeConnections++;
            cpuUsagePercet +=5;
            memoryUsesPercent +=3;
            System.out.println(" Handling request from " + clientIP + " (Active: " + activeConnections + ")");
        }
        else {
            System.out.println("Server DOWN - Cannot handle request");
        }
    }

    public void serverStaticFile(String filename) { 
        if (isRunning) {
            System.out.println("Serving file: " + filename);
        }
    }

    @Override
    public void displayinfo() {
        super.displayinfo();
        System.out.println("Type: WEB SERVER 🌐");
        System.out.println("Port: " + port);
        System.out.println("Framework: " + webFramework);
        System.out.println("Active Connections: " + activeConnections);
    }
}

//DATABASE SERVER - TO STORE DATA
class DatabaseServer extends Server {

}