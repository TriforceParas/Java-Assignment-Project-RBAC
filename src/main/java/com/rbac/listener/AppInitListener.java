package com.rbac.listener;

import com.rbac.util.DatabaseInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("========================================");
        System.out.println("  RBAC Demo Application Starting...");
        System.out.println("========================================");

        DatabaseInitializer.initialize();

        System.out.println("========================================");
        System.out.println("  Application Ready!");
        System.out.println("========================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("RBAC Demo Application Shutting Down...");
    }
}
