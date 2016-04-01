package predicates;

import common.Artist;
import common.InterfacePredicate;

import java.io.Serializable;

public class FindLocation implements InterfacePredicate, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String art_temp_store;

	public boolean isEqualTo(Artist artist) {
	      
	    boolean val=false; 
	    for(int i=0;i<artist.artist_songs.length;i++)
	    {
	    	String str1=artist.artist_songs[i];
	    if(str1.equals(art_temp_store))
	         val=true;
	    }
	    return val;
	    }
	    

	public FindLocation(String artistnameTemp) {
		art_temp_store = artistnameTemp;
	}

}
