/**
 * 
 */
package files;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handles files in a directory
 * 
 * @author Marcello Fonda
 *
 */
public class FileManager {
	
	protected File dir;
	
	static protected char comment_char = '#';
	
	/**
	 * Creates a new {@code FileManager} instance on the specified directory.
	 * @param parent the parent directory
	 * @param dir the directory in which the new instance should operate
	 */
	public FileManager (File parent, String dir) {
		this.dir = new File (parent, dir);
	}
	
	/**
	 * 
	 * @return the array of strings containing the names of the files contained in the directory
	 */
	public String [] getAvailable () {
		return dir.list(new FilenameFilter () {

			@Override
			public boolean accept(File dir, String name) {
				return !name.equals(".DS_Store");
			}
			
		});

	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public File getFile (final String filename) {
		return new File (dir, filename);
	}
	
	String readLine (InputStream stream) throws IOException {
		return readUntil(stream, '\n');
	}
	
	String readWord (InputStream stream) throws IOException {
		return readUntil(stream, ' ');
	}
	
	String readUntil (InputStream stream, final char end) throws IOException {
		String s = "";
		int c = stream.read();
		c = checkComment(stream, c);
		s += scan(stream, c, end);
		return s;
	}
	
	int checkComment(InputStream stream, int c) throws IOException {
		if (c == comment_char) {
			scan (stream, c, '\n');
			c = stream.read();
			c = checkComment(stream, c);
		} return c;
	}
	
	String scan (InputStream stream, int c, final char end) throws IOException {
		String s = "";
		while(c != end && c != '\n' && c != -1) {
			s += (char)c;
			c = stream.read();
		}
		return s;
	}

}
