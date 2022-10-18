public class TestDLList {

    public static void main(String[] args) {
        System.out.println("Testing");
        
        boolean ok;
        DLList<Integer> intlist = new DLList<Integer>();
        DLList<String> strlist = new DLList<String>();
        //DLList<Integer> intlist1 = new DLList<Integer>();
        
        for (int i = 0; i < 10; i++) {
            intlist.insertFirst(i);
        }
        
        for(int i = 0; i < 10; i++) {
            strlist.insertLast(Integer.toString(i));
        }
        
        intlist.seek(5);
        intlist.insertAt(1020);
        intlist.insertAfter(2021);
        System.out.println(intlist.getSize());
        
        intlist.seek(0);
        int size = intlist.getSize();
        
        for (int i = 0; i < size; i++) {
        	System.out.println("intlist Index = " + intlist.getIndex() + " Data = " + intlist.getData());
        	intlist.next();
        }
     
        //intlist.deleteFirst();
        //intlist.deleteLast();
        
        
        /*intlist.seek(0);
        while(intlist.getIndex() < intlist.getSize()-1) {
            System.out.println("intlist Index = " + intlist.getIndex() + " Data = " + intlist.getData());
            intlist.next();
        }
        System.out.println(">>>>>>>>>>>>>>>");
        
        intlist1 = new DLList<Integer>(intlist);
        intlist1.seek(0);
        while(intlist1.getIndex() < intlist1.getSize()-1) {
            System.out.println("intist1 Index1 = " + intlist1.getIndex() + " Data = " + intlist1.getData());
            intlist1.next();
        }
        
        System.out.println(">>>>>>>>>>>>>>>");
        
        intlist.seek(5);
        System.out.println(intlist.getIndex());
        intlist.deleteAt();
        intlist.seek(0);
        while(intlist.getIndex() < intlist.getSize()) {
            System.out.println("intlist Index del = " + intlist.getIndex() + " Data = " + intlist.getData());
            intlist.next();
        }
        
        System.out.println(">>>>>>>>>>>>>>>");
        
        intlist1.seek(0);
        while(intlist1.getIndex() < intlist1.getSize() - 1) {
            System.out.println("intist1 Index1 after= " + intlist1.getIndex() + " Data = " + intlist1.getData());
            intlist1.next();
        }
        System.out.println(">>>>>>>>>>>>>>>");
        
        strlist.seek(0);
        strlist.seek(5);
        
        strlist.deleteAt();*/
        
        /*System.out.println("cur index: " + strlist.getIndex());
    
        System.out.println("cur index: " + strlist.getIndex());
        System.out.println("cur data: " + strlist.getData());
        
        strlist.seek(0);
        while(strlist.getIndex() < strlist.getSize()) {
            System.out.println("Index str = " + strlist.getIndex() + " Data = " + strlist.getData());
            strlist.next();
        }
        
        System.out.println("cur index: " + strlist.getIndex()); */
        
        
        //System.out.println(">>>>>>>>>>>>>>>");
        //System.out.println(strlist.getIndex());
        
        //strlist.seek(0);
        //strlist.seek(5);
        //if(strlist.atFirst()) {
        	//System.out.println("yup, is " + strlist.getIndex());
        //} else {
        	//System.out.println("nope, is " + strlist.getIndex());
        //}
        
    }
    
}
