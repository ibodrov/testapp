package io.takari.orchestra.testapp;

import io.airlift.http.server.Main;
import io.airlift.http.server.WebServer;
import io.airlift.http.server.WebServerBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server {
    
    public static void main(String[] args) throws Exception {
        int port = 8001;
        
        WebServer server = new WebServerBuilder()
                .port(port)
                .serve("/").with(new TestServlet())
                .build();
        
        server.start();
    }
    
    public static class TestServlet extends HttpServlet {

        private static final String version;
        static {
            try {
                Properties props = new Properties();
                props.load(ClassLoader.getSystemResourceAsStream("version.properties"));
                version = props.getProperty("version");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setHeader("Content-Type", "text/plain");
            PrintWriter w = resp.getWriter();
            w.println(version);
            resp.flushBuffer();
        }
    }
}
