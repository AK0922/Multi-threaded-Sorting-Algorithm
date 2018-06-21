import java.io.*;
import java.util.*;

//Class sorts the arraylists

class Sorter implements Runnable{
	ArrayList<Integer> list;
public Sorter(ArrayList<Integer> list) { this.list = list;}
public ArrayList<Integer> sortedList(ArrayList<Integer> llist)
{
	Collections.sort(llist);
	return llist;
} 
        @Override
        public void run() {
//Collections.sort(list);
ArrayList<Integer> llist = sortedList(list);
System.out.println("Priniting from Thread Sorted List" +llist+ "\n");

}
}


//Class Implements teh logic of Merging the sorted arrays and sorts 
class Merger implements Runnable{

	ArrayList<Integer> list1;
	ArrayList<Integer> list2;
	ArrayList<Integer> list3;
        private SharedList sl;
	public Merger(ArrayList<Integer> list1, ArrayList<Integer> list2,SharedList sl)
	{
		this.list1= list1;
		this.list2= list2;
                this.sl = sl;
	}

	/*public ArrayList<Integer> merge(ArrayList<Integer> list1,ArrayList<Integer> list2)
	{
        list3= new ArrayList<>();
        list3.addAll(list1);
        list3.addAll(list2);
        return list3;
	}*/
	
        @Override
	public void run() {
	
	//sl.setFullList(merge(list1,list2));
        //System.out.println(list1);
        //System.out.println(list2);
	list3= new ArrayList<>();
        list3.addAll(list1);
        list3.addAll(list2);
        //System.out.println(list3);
         for (int i = 0; i < list3.size(); i++) {

          for (int j = list3.size() - 1; j > i; j--) {
              if (list3.get(i) > list3.get(j)) {

                  int tmp = list3.get(i);
                  list3.set(i,list3.get(j)) ;
                  list3.set(j,tmp);

              }

          }

      }
        sl.setFullList(list3);
        
	
	
	}	

}

//Shared data Class shares the Lists with all the classes
class SharedList{
    private ArrayList<Integer> list1;
    private ArrayList<Integer> list2;
    private ArrayList<Integer> fullList;
    
    public ArrayList<Integer> getList1()
    {
        return list1;
    }
    
     public ArrayList<Integer> getList2()
    {
        return list2;
    }
     
     public ArrayList<Integer> getFullList()
     {
         return fullList;
     }
     
     public void setLists(ArrayList<Integer> list1,ArrayList<Integer> list2)
     {
         this.list1=list1;
         this.list2=list2;
     }
     
     public void setFullList(ArrayList<Integer> fullList)
     {
         this.fullList=fullList;
     }
}

//Main Class

public class Prog1 {


   public static ArrayList<Integer>  ReadFile(String filename)
    {
	
	//Read Integers from File and copy to ArrayList
	String[] text ;
    ArrayList<Integer> values = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename)))
    {
     text = br.readLine().split("\\s");
            //                 System.out.println(text);
            for (String text1 : text) {
                values.add(Integer.parseInt(text1));
            }

	}
	catch(IOException e)
    {
        System.out.println("error");
    }

	
	System.out.println("Initial List:" + values);
	
	//returing Arraylist
	return (values);

   }
	
    public static void main(String[] args){

	
        SharedList sl = new SharedList();
	ArrayList<Integer> lists = ReadFile(args[0]);
	
	//Size of ArrayList
        int size = lists.size();

        //Splitting the ArrayList
        ArrayList<Integer> first = new ArrayList<>(lists.subList(0,(size+1)/2));
        ArrayList<Integer> second = new ArrayList<>(lists.subList((size+1)/2,size));


	
        sl.setLists(first,second);
        sl.setFullList(lists);
	Sorter sorter1 = new Sorter((sl.getList1()));
	Sorter sorter2 = new Sorter((sl.getList2()));
	
	Thread sort1 = new Thread(sorter1); 
        //System.out.println("Sorting thread on first half "+sort1.getName());
        
	Thread sort2 = new Thread(sorter2); 
        //System.out.println("Sortiung thread on second half"+sort2.getName());
	sort1.start();
        sort2.start();
	try {
	    	
	    
		    
		    // waiting until both the threads gets completed
			sort1.join(); 
			sort2.join();
                        
           } catch (InterruptedException e) {
			e.printStackTrace();
		}
			
			/*ArrayList<Integer> s1 = sorter1.sortedList((ArrayList)lists[0]);
			 ArrayList<Integer> s2 = sorter2.sortedList((ArrayList)lists[1]);

			System.out.println("Sorted ArrayList is : "+ s1);
			System.out.println("Sorted ArrayList is : "+ s2);*/
			 
                        System.out.println("Sorted First ArrayList is : "+ sl.getList1());
			System.out.println("Sorted Second ArrayList is : "+ sl.getList2());
			System.out.println();
			
			//merging the two sorted Arrays
			
			 Merger merger= new  Merger(sl.getList1(),sl.getList2(),sl); 
		
			
			Thread mergee = new Thread(merger);
			mergee.start();
                        
                        try{
			mergee.join();
                        
                        } catch (InterruptedException e) {
			e.printStackTrace();
		}
                        
                        System.out.println("Merged and Sorted List : "+ sl.getFullList());
		

			 /*ArrayList<Integer> m1 = merger.Merge(s1,s2);
			 Collections.sort(m1);
			 System.out.println("Merged Sorted ArrayList is : "+ m1);*/

	
    }

}
