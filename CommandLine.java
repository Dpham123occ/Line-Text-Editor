import java.awt.datatransfer.SystemFlavorMap;
import java.io.*;
import java.util.*;

import javax.sound.sampled.Line;

public class CommandLine {
	
	//data member
	private String com;
	private String arg1;
	private String arg2;
	private String arg3;
	private String arg4;
	private int count;
	public CommandLine() {
		com = null;
		arg1 = null;
		arg2 = null;
		arg3 = null;
		arg4 = null;
		count = 0;
	}

	public String getCOM() {
		return com;
	}
	
	public void help() {
		System.out.println("Help");
		System.out.println("Command  Arguemnts                     Description");
		System.out.println("h                                      Display help");
		System.out.println("r        filespec                      Read a file into the current buffer");
		System.out.println("w                                      Write the current buffer to a file on disk");
		System.out.println("f        filespec                      Change the name of the current buffer");
		System.out.println("q                                      Quit the line editor");
		System.out.println("q!                                     Quit the line editor without saving");
		System.out.println("t                                      Go to the first line");
		System.out.println("b                                      Go to the last line");
		System.out.println("g        num                           Go to line num in the buffer");
		System.out.println("-                                      Go to the previous line");
		System.out.println("+                                      Go to the next line");
		System.out.println("=                                      Print the current line number");
		System.out.println("n                                      Toggle line number displayed");
		System.out.println("#                                      Print the number of lines and characters in the buffer");
		System.out.println("p                                      Print the current line");
		System.out.println("pr       start stop                    Print several lines");
		System.out.println("?        pattern                       Search backwards for a pattern");
		System.out.println("/        pattern                       Search forwards for a pattern");
		System.out.println("s        text1 text2                   Substitute all text1 with text2 on current line");
		System.out.println("sr       text1 text2 start stop        Substitute all text1 with text2 in between start and stop");
		System.out.println("d                                      Delete current line, copy into the clipboard");
		System.out.println("dr       start stop                    Delete lines between start and stop, copy into the clipboard");
		System.out.println("c                                      Copy current line into the clipboard");
		System.out.println("cr       start stop                    Copy lines between start and stop into the clipboard");
		System.out.println("pa                                     Paste the content of the clipboard above the current line");
		System.out.println("pb                                     Paste the content of the clipboard below the current line");
		System.out.println("ia                                     Insert new lines of text above the current line until \".\" appears");
		System.out.println("ic                                     Insert new lines of text at the current line until \".\" appears");
		System.out.println("ib                                     Insert new lines of text below the current line until \".\" appears");
	}
	
	public void readcmd(Scanner in) {
		String cmdLine = in.nextLine();
		StringTokenizer tok = new StringTokenizer(cmdLine, " ");

		com = tok.nextToken();
		if (com.equals("r") || com.equals("w") || com.equals("f") || com.equals("g") || com.equals("?") || com.equals("/")) {
			arg1 = tok.nextToken();
		}
		else if (com.equals("pr") || com.equals("s") || com.equals("dr") || com.equals("cr")) {
			arg1 = tok.nextToken();
			arg2 = tok.nextToken();
		}
		else if (com.equals("sr")) {
			arg1 = tok.nextToken();
			arg2 = tok.nextToken();
			arg3 = tok.nextToken();
			arg4 = tok.nextToken();
		}
	}
	
	public void read(Buffer buf) {
			//check if buffer is empty
		if (!buf.isEmpty()) {
			buf.flush();
		}
		else try {
			buf.setFilespec(arg1);
			Scanner readFile = new Scanner(new File(arg1));
			while (readFile.hasNextLine()) {
				buf.insertLast(readFile.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR - FILE DOES NOT EXISTS");
		}
		count++;
	}
	
	public void writeD(Buffer buf) {
		if(buf.isEmpty()) {
			System.out.println("ERROR - BUFFER IS EMPTY");
			buf.setDirty(false);
			return;
		} else {
			int i = buf.getDLL().getIndex();
			try (FileWriter out = new FileWriter(arg1)) {
				buf.getDLL().seek(0);
				for(int k = 0; k < buf.getDLL().getSize(); k ++) {
					out.write(buf.getDLL().getData() + "\n");
					buf.getDLL().next();
				}
				out.close();
				buf.getDLL().seek(i);
				buf.setDirty(false);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR");
				buf.setDirty(false);
			}
		}
		count++;
	}

	public void changeBufferName(Buffer buf) {
		buf.setFilespec(arg1);
		buf.setDirty(true);
		count++;
	}
	
	public void quit(Buffer buf, Scanner in) {
		boolean dirty = buf.getDirty();
		if (dirty) {
			System.out.println("Do you want to save the file? y/n");
			switch(in.next()) {
			case "y":
				int i = buf.getDLL().getIndex();
				try (FileWriter out = new FileWriter(buf.getFilespec())) {
					buf.getDLL().seek(0);
					for(int k = 0; k < buf.getDLL().getSize(); k ++) {
						out.write(buf.getDLL().getData() + "\n");
						buf.getDLL().next();
					}
					out.close();
					buf.getDLL().seek(i);
					buf.setDirty(false);	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR");
					buf.setDirty(false);
				}
				break;
			case "n": 
				break;
			}
		}
		count++;
	}
	
	public void forceQuit() {
		count++;
		//do nothing
	}
	
	public void top(Buffer buf) {
		if (buf.isEmpty()) { 
			System.out.println("BUFFER IS EMPTY");
		} else
		buf.getDLL().first();
		count++;
	}
	
	public void bottom(Buffer buf) {
		if (buf.isEmpty()) { 
			System.out.println("BUFFER IS EMPTY");
		} else
		buf.getDLL().last();
		count++;
	}
	
	public void goToLine(Buffer buf) {
		int num = Integer.parseInt(arg1) - 1;
		buf.getDLL().seek(0);
		if (num < 0 || num > buf.getDLL().getSize() - 1) {
			System.out.println("ERROR - INDICES OUT OF BOUND, num MUST BE [1 - " + (buf.getDLL().getSize()) + "]");
			return;
		}
		else 
			buf.getDLL().seek(num);
		count++;
	}
	
	public void goToPrev(Buffer buf ) {
		if(buf.isEmpty()) {
			System.out.println("ERROR - EMPTY");
		}else if (buf.getDLL().atFirst()) {
			System.out.println("ERROR - ALREADY AT FIRST LINE");
		}else	
			buf.getDLL().previous();
		count++;
	}
	
	public void goToNext(Buffer buf) {
		if(buf.isEmpty()) {
			System.out.println("ERROR - EMPTY");
		}else if (buf.getDLL().atLast()) {
			System.out.println("ERROR - ALREADY AT LAST LINE");
		}else	
			buf.getDLL().next();
		count++;
	}
	
	public void printNumLine(Buffer buf) {
		System.out.println("Current line: " + (buf.getDLL().getIndex() + 1));
		count++;
	}
	
	public void showLineNumber(Buffer buf) {
		int curIndex = buf.getDLL().getIndex();
		buf.getDLL().seek(0);
		for (int i = 0; i < buf.getDLL().getSize(); i++) {
			String l = buf.getDLL().getData();
			buf.getDLL().setData((buf.getDLL().getIndex()+1) + " " + l);
			buf.getDLL().next();
		}
		buf.getDLL().seek(curIndex);
		count++;
	}
	
	public void printLine(Buffer buf) {
		if (buf.isEmpty()) 
			System.out.println("ERROR- EMPTY BUFFER");
		else
			System.out.println(buf.getLine());
		count++;
	}
	
	public void printRange(Buffer buf) {
		int curIndex = buf.getDLL().getIndex();
		int start = Integer.parseInt(arg1) - 1;
		int stop = Integer.parseInt(arg2) - 1;
		if (buf.isEmpty()) {
			System.out.println("ERROR - BUFFER IS EMPTY");
		}
		else if (start < 0 || stop > buf.getDLL().getSize() - 1) {
			System.out.println("ERROR - INDICES OUT OF BOUND, num must be [1 - " + (buf.getDLL().getSize())+ "]");
		} else {
			buf.getDLL().seek(0);
			for (; start <= stop; start++) {
				printLine(buf);
				buf.getDLL().next();
			}
		}
		buf.getDLL().seek(curIndex);
		count++;	
	}
	
	public void searchBack(Buffer buf) {
		boolean found = false;
		int OGINDEX = buf.getDLL().getIndex();
		int curIndex = buf.getDLL().getIndex();
		if (buf.getDLL().atFirst()) {
			System.out.println("ALREADY AT FIRST");
		} else {
			
			for (; 0 <= curIndex; curIndex--) {
				if(buf.getDLL().getData().contains(arg1)) {
					found = true;
					break;
				}
				buf.getDLL().previous();
			}
		}
		if (!found) {
			System.out.println("STRING " + arg1 + " NOT FOUND");
			buf.getDLL().seek(OGINDEX);
		}
		count++;
	}
	
	public void searchFoward(Buffer buf) {
		boolean found = false;
		int OGINDEX = buf.getDLL().getIndex();
		int curIndex = buf.getDLL().getIndex();
		if (buf.getDLL().atLast()) {
			System.out.println("ALREADY AT LAST");
		} else {
			for (; curIndex <= buf.getDLL().getSize() - 1; curIndex++) {
				if(buf.getDLL().getData().contains(arg1)) {
					found = true;
					return;
				} 
				buf.getDLL().next();
			}
		}
		if (!found) {
			System.out.println("STRING " + arg1 + " NOT FOUND");
			buf.getDLL().seek(OGINDEX);
		}
		count++;
	}
	
	public void replace(Buffer buf) {
		String temp = buf.getDLL().getData();
		String replace = temp.replaceAll(arg1, arg2);
		buf.getDLL().setData(replace);
		buf.setDirty(true);
		count++;
	}
	
	public void replaceRange(Buffer buf) {
		int start = Integer.parseInt(arg3);
		int stop = Integer.parseInt(arg4);
		for (;start < stop; start++) {
			buf.getDLL().seek(start);
			String temp = buf.getDLL().getData();
			String replace = temp.replaceAll(arg1, arg2);
			buf.getDLL().setData(replace);
		}
		buf.setDirty(true);
		count++;
	}
	
	public void deleteLine(Buffer buf, Buffer clipboard) {
		clipboard.flush();
		if (buf.isEmpty()) {
			System.out.println("ERROR - EMPTY BUFFER");
		} else {
		int index = buf.getDLL().getIndex();
		buf.getDLL().seek(0);
		buf.getDLL().seek(index);
		clipboard.getDLL().insertLast(buf.getLine());
		buf.getDLL().deleteAt();
		}
		buf.setDirty(true);
		count++;
	}
	
	public void deleteRange(Buffer buf, Buffer clipboard) {
		clipboard.flush();
		int start = Integer.parseInt(arg1);
		int stop = Integer.parseInt(arg2);
		if (buf.isEmpty()) {
			System.out.println("ERROR - EMPTY BUFFER");
		} else {
		int index = buf.getDLL().getIndex();
		buf.getDLL().seek(0);
		buf.getDLL().seek(index);
		for (; start <= stop; start++) {
			clipboard.getDLL().insertLast(buf.getLine());
			buf.getDLL().deleteAt();
			System.out.println("Cut element: " + clipboard.getDLL().getData());
			}
		}
		buf.setDirty(true);
		count++;
	}
	
	public void copyLine(Buffer buf, Buffer clipboard) {
		if (buf.isEmpty()) {
			System.out.println("BUFFER IS EMPTY");
		} else {
		clipboard.flush();
		clipboard.insertLast(buf.getLine());
		System.out.println("C: " + buf.getLine());
		}
		count++;
	}
	
	public void copyRange(Buffer buf, Buffer clipboard) {
		clipboard.flush();
		int start = Integer.parseInt(arg1) - 1;
		int stop = Integer.parseInt(arg2) - 1;
		if (buf.isEmpty()) {
			System.out.println("ERROR - EMPTY BUFFER");
			return;
		} else if (start < 0 || stop > buf.getDLL().getSize() - 1) {
			System.out.println("ERROR - INDICES OUT OF BOUND");
		}
		else {
		while (start <= stop) {
			buf.getDLL().seek(start);
			clipboard.getDLL().insertLast(buf.getLine());
			start++;
			}
		}
		clipboard.getDLL().seek(0);
		for (int i = 0; i < clipboard.getDLL().getSize(); i++) {
			clipboard.getDLL().next();
		}
		count++;
	}
	public void pasteAbove(Buffer buf, Buffer clipboard) {
		if (clipboard.isEmpty()) {
			System.out.println("CLIPBOARD IS EMPTY");
			return;
		} else {
			if (buf.getDLL().atFirst()) {
				int size = clipboard.getDLL().getSize();
				for (int i = size - 1; i >= 0; i --) {
					clipboard.getDLL().seek(i);
					buf.getDLL().insertFirst(clipboard.getLine());
					clipboard.getDLL().previous();
				}
			}
			else {
				int size = clipboard.getDLL().getSize();
				for (int i = size - 1; i >= 0; i --) {
					clipboard.getDLL().seek(i);
					buf.getDLL().insertAt(clipboard.getLine());
					clipboard.getDLL().previous();
				}
			}
		}
		buf.setDirty(true);
		count++;
	}
	
	public void pasteBelow(Buffer buf, Buffer clipboard) {
		if (clipboard.isEmpty()) {
			System.out.println("CLIPBOARD IS EMPTY");
			return;
		} else {
			if (buf.getDLL().atLast()) {
				clipboard.getDLL().seek(0);
				int size = clipboard.getDLL().getSize();
				for (int i = 0; i < size; i ++) {
					buf.getDLL().insertLast(clipboard.getLine());
					clipboard.getDLL().next();
				}
			}
			else {
				clipboard.getDLL().seek(0);
				int size = clipboard.getDLL().getSize();
				for (int i = 0; i < size; i++) {
					buf.getDLL().insertAfter(clipboard.getLine());
					buf.getDLL().previous();
					clipboard.getDLL().next();
				}
			}
		buf.setDirty(true);
		}
		count++;
	}
	
	public void insertAbove(Buffer buf, Scanner in) {
		DLList<String> inLines = new DLList<String>();
		String input = in.nextLine();
		while (input != null) {
			if (input.equals("."))
				break;
			else {
				inLines.insertLast(input);
			}
			input = in.nextLine();
		}
		int size = inLines.getSize();
		for (int i = size - 1; i >= 0; i--) {
			inLines.seek(i);
			buf.getDLL().insertAt(inLines.getData());
		}
		buf.setDirty(true);
		count++;
	}
	
	public void insertCurrent(Buffer buf, Scanner in) {
		DLList<String> inLines = new DLList<String>();
		String input = in.nextLine();
		while (input != null) {
			if (input.equals("."))
				break;
			else {
				inLines.insertLast(input);
			}
			input = in.nextLine();
		}
		int size = inLines.getSize();
		for (int i = size - 1; i >= 0; i--) {
			inLines.seek(i);
			buf.getDLL().insertAt(inLines.getData());
		}
		buf.setDirty(true);
		count++;
	}

	public void insertBelow(Buffer buf, Scanner in) {
		while (in.hasNextLine()) {
			String input = in.nextLine();
			if (input.equals("."))
				return;
			else {
				if (buf.getDLL().atLast()) {
					buf.getDLL().insertLast(input);
				} else {
					buf.getDLL().insertAfter(input);
				}
			}
		}
		
		buf.setDirty(true);
		count++;
	}
	
	public void printNUMCHAR(Buffer buf) {
		int line = 0;
		int ch = 0;
		int curIndex = buf.getDLL().getIndex();
		buf.getDLL().seek(0);
		for (int i = 0; i < buf.getDLL().getSize(); i ++) {
			line++;
			for (int k = 0; k < buf.getLine().length(); k ++) {
				ch++;
			}
			buf.getDLL().next();
		}
		buf.getDLL().seek(curIndex);
		System.out.println("Line numbers: " + line);
		System.out.println("Char numbers: " + ch);
		count++;
	}
	public void printInfo(Buffer buf) {
		System.out.println("LTE:"+buf.getFilespec()+":"+count+":"+(buf.getDLL().getIndex()+1));
	}
}
