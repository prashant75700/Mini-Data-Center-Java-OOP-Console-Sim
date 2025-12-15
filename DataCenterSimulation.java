// Here we are going to create a Mini/Small real life data centre simulation.
class Server {
    protected String hostname;
    protected String ipAddress;
    protected int cpuCores;
    protected int ramGB;
    protected int diskGB;
    protected boolean isRunning;
    protected int cpuUsagePercent;
    protected int memoryUsesPercent;

    public Server (String hostname, String ipAddress, int cpuCores, int ramGB, int diskGB) {
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.cpuCores = cpuCores;
        this.ramGB = ramGB;
        this.diskGB = diskGB;
        this.isRunning = false;
        this.cpuUsagePercent = 0;
        this.memoryUsesPercent = 0;

        System.out.println(" Server provisioned: " + hostname);
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            cpuUsagePercent = 10;
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
            cpuUsagePercent = 0;
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
        System.out.println("CPU Usage: " + cpuUsagePercent + "%");
        System.out.println("Memory Usage: " + memoryUsesPercent + "%");

        if (cpuUsagePercent > 80 ) {
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
            cpuUsagePercent +=5;
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
    private String databaseType;
    private int maxConnections;
    private int currentConnections;

    public DatabaseServer(String hostname, String ipAddress, int cpuCores, int ramGB, int diskGB, String dbType) {
        super(hostname, ipAddress, cpuCores, ramGB, diskGB);
        this.databaseType = dbType;
        this.maxConnections = 100;
        this.currentConnections = 0;
    }

    public void executeQuery(String query) {
        if (isRunning) {
            cpuUsagePercent += 10;
            memoryUsesPercent += 5;
            System.out.println("Executing: " + query);
        }
        else {
            System.out.println("❌ Database DOWN");
        }
    }

    public void createBackup() {
        if (isRunning) {
            System.out.println("Creating backup Of " + databaseType + " database....");
            cpuUsagePercent += 20;
            System.out.println("Backup Completed!");
        }
    }

    public void optimizeDatabase() {
        System.out.println("Optimizing Database.....");
        cpuUsagePercent = 90;
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        cpuUsagePercent = 20;
        System.out.println("Optimization Complete! ");
    }

    @Override
    public void displayinfo(){
        super.displayinfo();
        System.out.println("Type: DATABASE SERVER ");
        System.out.println("DB Type: " + databaseType);
        System.out.println("Connections: " + currentConnections + "/" + maxConnections);
    }

}

// Now have to add LOAD Balancer 

class LoadBalancer extends Server{
    private WebServer[] backends;
    private int currentBackendIndex;

    public LoadBalancer( String hostname, String ipAddress, int cpuCores, int ramGB, int diskGB) {
        super(hostname, ipAddress, cpuCores, ramGB, diskGB);
        this.backends = new WebServer[3];
        this.currentBackendIndex = 0;
    }

    public void addBackend(WebServer server) {
        for (int i = 0; i < backends.length; i++ ) {
            if (backends[i] == null) {
                backends[i] = server;
                System.out.println("Backend added: " + server.hostname);
                return;
            }
        }
        System.out.println("Max Backends reached");
    }

    public void distributedTraffic(String clientIP) {
        if (!isRunning) {
            System.out.println("Load Balancer DOWN ");
            return;
        }

        //Round-Robin Distribution using here:
        WebServer backend = backends[currentBackendIndex];
        if (backend != null && backend.isRunning) {
            System.out.println("Routing " + clientIP + " --> " + backend.hostname);
            backend.handleRequest(clientIP);
            currentBackendIndex = (currentBackendIndex + 1) % backends.length;
        }
        else {
            System.out.println("No healthy backends! ");
        }
    }

    @Override
    public void displayinfo() {
        super.displayinfo();
        System.out.println("Type: LOAD BALANCER ~~~ ");
        System.out.println("Backends configured: " + countBackends());
    }

    private int countBackends() {
        int count = 0;
        for (WebServer backend : backends) {
            if (backend != null)  count++;
            
        }
        return count;

    }
}

// Our Main class ~~~~~

public class DataCenterSimulation {
    public static void main(String[] args) {
System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     DATA CENTER SIMULATION            ║");
        System.out.println("╚═══════════════════════════════════════╝\n");

        //Provision servers
        WebServer web1 = new WebServer("web-01", "121.14.2.10", 4, 16, 0500, 8080);
        WebServer web2 = new WebServer("web-02", "121.45.2.10", 4, 16, 500, 3232);
        DatabaseServer db1 = new DatabaseServer("db-01", "10.0.2.12", 8, 32, 1000, "PostgreSQL");
        LoadBalancer lb = new LoadBalancer("lb-01", "10.0.0.10", 2, 8, 100);

        // Now start the simulation: 
        System.out.println("\n========== STARTING INFRASTRUCTURE ==========");
        web1.start();
        web2.start();
        db1.start();
        lb.start();

        //Configure load balancer:
        System.out.println("\n========== CONFIGURING LOAD BALANCER ==========");
        lb.addBackend(web1);
        lb.addBackend(web2);

        //Simulateing Traffic:
        System.out.println("\n========== SIMULATING TRAFFIC ==========");
        lb.distributedTraffic("192.168.1.100");
        lb.distributedTraffic("192.165.1.101");
        lb.distributedTraffic("192.168.1.102");
        lb.distributedTraffic("192.168.1.103");
        lb.distributedTraffic("192.145.2.300");

        //Database Operations:
        System.out.println("\n========== DATABASE OPERATIONS ==========");
        db1.executeQuery("Select * FROM users WHERE id = 1");
        db1.executeQuery("Insert INTO Orders Values(...)");
        db1.createBackup();
        db1.optimizeDatabase();

        //Health checks:
        System.out.println("\n========== HEALTH CHECKS ==========");
        web1.checkHealth();
        web2.checkHealth();
        db1.checkHealth();
        lb.checkHealth();

        //Display all Server Info:
        web1.displayinfo();
        web2.displayinfo();
        db1.displayinfo();
        lb.displayinfo();

        //for Restarting a Server:
         System.out.println("\n========== MAINTENANCE ==========");
         web1.restart();

         //POLYMORPHISM Ex: Manage all Servers uniformly
         System.out.println("\n========== INFRASTRUCTURE STATUS ==========");
         Server[] infrastructure = {web1, web2, db1, lb};

         for (Server server : infrastructure) {
            System.out.println(server.hostname + ": " + (server.isRunning ? " 🟢 UP" : " 🛑 DOWN"));
         }
    }
}