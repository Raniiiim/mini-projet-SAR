package tasklist;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client {
	public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RemoteList stub = (RemoteList) registry.lookup("Rem");

           // Ajouter une tâche
            stub.addTask("Task 1");
            stub.addTask("Task 2");
            

            // Supprimer une tâche
            stub.removeTask(0);
            
            // Récupérer la liste complète des tâches
            List<String> tasks = stub.getTasks();
            for (String task : tasks) {
                System.out.println(task);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
