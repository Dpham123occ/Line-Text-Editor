import java.util.*;

class LTE{
    public static void main(String[] args) {
    	
    	Buffer editor = new Buffer();
    	Buffer clipboard = new Buffer();

    	Scanner in = new Scanner(System.in);
    	
    	CommandLine cmd = new CommandLine();
    	
    	
    	boolean done = false;
    	
    	while (!done) {
    		cmd.readcmd(in);
    		switch(cmd.getCOM()) {
    		
    		case "h": 
    			cmd.help();
    			cmd.printInfo(editor);
    			break;
    		case "r":
    			cmd.read(editor);
    			cmd.printInfo(editor);
    			break;
    		case "w":
    			cmd.writeD(editor);
    			cmd.printInfo(editor);
    			break;
    		case "f":
    			cmd.changeBufferName(editor);
    			cmd.printInfo(editor);
    			break;
    		case "q":
    			cmd.quit(editor, in);
    			done = true;
    			break;
    		case "q!":
    			cmd.forceQuit();
    			done = true;
    			break;
    		case "t": 
    			cmd.top(editor);
    			cmd.printInfo(editor);
    			break;
    		case "b":
    			cmd.bottom(editor);
    			cmd.printInfo(editor);
    			break;
    		case "g":
    			cmd.goToLine(editor);
    			cmd.printInfo(editor);
    			break;
    		case "-":
    			cmd.goToPrev(editor);
    			cmd.printInfo(editor);
    			break;
    		case "+":
    			cmd.goToNext(editor);
    			cmd.printInfo(editor);
    			break;
    		case "=":
    			cmd.printNumLine(editor);
    			cmd.printInfo(editor);
    			break;
    		case "n":
    			cmd.showLineNumber(editor);
    			cmd.printInfo(editor);
    			break;
    		case "p":
    			cmd.printLine(editor);
    			cmd.printInfo(editor);
    			break;
    		case "pr":
    			cmd.printRange(editor);
    			cmd.printInfo(editor);
    			break;
    		case "?":
    			cmd.searchBack(editor);
    			cmd.printInfo(editor);
    			break;
    		case "/":
    			cmd.searchFoward(editor);
    			cmd.printInfo(editor);
    			break;
    		case "s":
    			cmd.replace(editor);
    			cmd.printInfo(editor);
    			break;
    		case "sr":
    			cmd.replaceRange(editor);
    			cmd.printInfo(editor);
    			break;
    		case "d":
    			cmd.deleteLine(editor, clipboard);
    			cmd.printInfo(editor);
    			break;
    		case "dr":
    			cmd.deleteRange(editor, clipboard);
    			cmd.printInfo(editor);
    			break;
    		case "c":
    			cmd.copyLine(editor, clipboard);
    			cmd.printInfo(editor);
    			break;
    		case "cr":
    			cmd.copyRange(editor, clipboard);
    			cmd.printInfo(editor);
    			break;
    		case "pa":
    			cmd.pasteAbove(editor, clipboard);
    			cmd.printInfo(editor);
    			break;
    		case "pb":
    			cmd.pasteBelow(editor, clipboard);
    			cmd.printInfo(editor);
    			break;
    		case "ia":
    			cmd.insertAbove(editor, in);
    			cmd.printInfo(editor);
    			break;
    		case "ic": 
    			cmd.insertCurrent(editor, in);
    			cmd.printInfo(editor);
    			break;
    		case "ib":
    			cmd.insertBelow(editor, in);
    			cmd.printInfo(editor);
    			break;
    		case "#": 
    			cmd.printNUMCHAR(editor);
    			cmd.printInfo(editor);
    			break;
    		}	
    	}
    }
}