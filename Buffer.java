import java.util.*;

public class Buffer {
	//data member
	private String filespec;
	private boolean dirty;
	private DLList<String> lines;

	
	//default constructor
	public Buffer() {
		filespec = "";
		dirty = false;
		lines = new DLList<String>();
	}
	
	//overloaded constructor
	public Buffer(Buffer E) {
		filespec = E.filespec;
		dirty = true;
		lines = new DLList<String>(E.lines);
	}
	
	//get filespec
	public String getFilespec() {
		return filespec;	
	}
	
	//set filespec
	public void setFilespec(String s) {
		filespec = s;
	}
		
	//get dirty bit
	public boolean getDirty() {
		return dirty;
	}
	//set dirty bit
	public void setDirty(boolean b) {
		dirty = b;
	}
	
	//getLine
	public String getLine() {
		return (lines.getData());
	}
	
	//getDLList
	public DLList<String> getDLL() {
		return lines;
	}
	
	//insert at
	public void insertAt(String line) {
		lines.insertAt(line);
	}
	//insert first
	public void insertFirst(String line) {
		lines.insertFirst(line);
	}
	//insert last line
	public void insertLast(String line) {
		lines.insertLast(line);
	}
	
	//flush - for clipboard
	public void flush() {
		lines = new DLList<String>();
	}
	
	//isEmpty 
	public boolean isEmpty() {
		return(lines.isEmpty());
	}
	
	
}
