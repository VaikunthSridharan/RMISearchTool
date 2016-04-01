package common;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
/**
 * @author Vaikunth Sridharan
 *
 */
public interface Classifier extends Remote {
	/**
	 * @param pred
	 * @return
	 * @throws RemoteException
	 * @throws IOException
	 */
	public ArrayList<Artist> classify(InterfacePredicate pred) throws RemoteException,IOException;
}


