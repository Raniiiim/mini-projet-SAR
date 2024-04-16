package tasklist;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServeurTask implements RemoteList {
    private List<String> tasks;

    public ServeurTask() {
        tasks = new ArrayList<>();
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
    	if (index >= 0 && index < tasks.size()) {
        tasks.remove(index);
    	}
    }

    public List<String> getTasks() {
        return tasks;
    }

    public static void main(String[] args) {
        try {
            ServeurTask obj = new ServeurTask();
            RemoteList stub = (RemoteList) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Rem", stub);

            System.out.println("Server is ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
