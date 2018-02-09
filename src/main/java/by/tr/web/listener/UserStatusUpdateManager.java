package by.tr.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserStatusUpdateManager implements ServletContextListener{
    private ScheduledExecutorService scheduler;



    public UserStatusUpdateManager() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new UserStatusUpdater(), 1,12, TimeUnit.HOURS);
          }

    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }


}
