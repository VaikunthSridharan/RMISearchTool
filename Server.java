import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.Artist;
import common.Classifier;
import common.InterfacePredicate;

/**
 * @author Vaikunth Sridharan
 * 
 */
public class Server implements Classifier {
	public static final String BINDING_NAME = "sample/HelloService";

	/**
	 * @param args
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws RemoteException,
			AlreadyBoundException, InterruptedException {
		System.out.print("Starting registry...");
		final Registry registry = LocateRegistry.createRegistry(2099);
		System.out.println(" OK");
		final Classifier service = new Server();
		Remote stub = UnicastRemoteObject.exportObject(service, 0);
		System.out.print("Binding service...");
		registry.bind(BINDING_NAME, stub);
		System.out.println(" OK");

		while (true) {
			Thread.sleep(Integer.MAX_VALUE);
		}
	}

	@Override
	public ArrayList<Artist> classify(InterfacePredicate pred)
			throws RemoteException, IOException {

		ArrayList<Artist> artistVal = new ArrayList<Artist>();
		try {

			BufferedReader br = new BufferedReader(new FileReader("Table2.in"));

			int start = 0;

			while (br.ready()) {
				String line = br.readLine();
				Artist a = new Artist();
				String[] seperate_array = line.split("<SEP>");

				a.artist_id = seperate_array[start + 1];
				a.artist_name = seperate_array[start + 2];
				a.artist_location = seperate_array[start + 3];
				a.artist_songs = seperate_array;
				if (pred.isEqualTo(a)) {
					artistVal.add(a);

				}

			}
			br.close();
			return artistVal;
		} catch (FileNotFoundException e) {
			return null;
		} catch (RemoteException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}

}
