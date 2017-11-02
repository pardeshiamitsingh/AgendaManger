package aoa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Amit
 * 
 */
public class AgendaManager {

	private List<Rule> inputList = new ArrayList<Rule>();
	int cycle = 1;

	// constructer of Agenda manager
	public AgendaManager(int n) {
		URL url = getClass().getResource("test1.txt");

		final String dir = System.getProperty("user.dir");
		String max = null;
		boolean flag= false;
		String path = dir+ "\\test\\test"+n+".txt";
		//Read file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null && cycle <30 && inputList.size() <=300 ) {
				if(sCurrentLine == null || sCurrentLine.isEmpty())
					continue;
				//  add rules to AgendaManager
				addRules(sCurrentLine, flag);
				flag = true;
				// find max and print it
				max = extractMax();
				System.out.println("===========CYCLE " + cycle + "===============");
				System.out.println("ACTIVATED RULES");
				for(int i = 1 ; i < inputList.size() ; i++)
					System.out.print(inputList.get(i));
				System.out.println();
				System.out.println("EXECUTED RULE");
				System.out.println(max);
				cycle++;
			}
            
			while(inputList.size() > 2 &&  cycle <=30){
				max = extractMax();
				System.out.println("===========CYCLE " + cycle + "===============");
				System.out.println("ACTIVATED RULES");
				for(int i = 1 ; i < inputList.size() ; i++)
					System.out.print(inputList.get(i));
				System.out.println();
				System.out.println("EXECUTED RULE");
				System.out.println(max);
				cycle++;
			}
		} catch (IOException e) {
		System.out.println("Error while reading the file "+e.getMessage());
		}catch(Exception e){
			System.out.println("Error in AgendaManager "+e.getMessage());
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a test file number you want to execute: 1 = test1.txt, 2 = test2.txt, 3 = test3.txt ");
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		//once finished
		reader.close(); 
		long startTime = new Date().getTime();
		new AgendaManager(n);
		long endTime = new Date().getTime();
		System.out.println("Execution time "+(endTime- startTime)+" ms");

	}
	// adds rules to rule enngine
	private void addRules(String rulesStr , boolean flag) {
        rulesStr = rulesStr + ",";
        
        
		List<Rule> readLineList = new ArrayList<Rule>();
		if (cycle == 1) {
			readLineList.add(new Rule());

		}
		
		String[] currentLineArr = rulesStr.split("(\\)\\,)");
		for (String s : currentLineArr) {
			Rule r = new Rule();
			String strArr[] = s.split(",");
			String str1 = strArr[0];
			str1 = str1.trim();
			String name = str1.substring(1);
			if(name.length() > 5){
				System.out.println(" Invalid Rule Name for rule "+name);
				continue;
			}
			r.setName(name);
			String str = strArr[1];
			str = str.substring(0, str.length() );
			str = str.trim();
			r.setPriority(Integer.parseInt(str));
			readLineList.add(r);
		}
		if(!flag){
			//build priority queue
			buildQueue(readLineList);
		}else{
			for(Rule r : readLineList)
				insertHeap(r);
		}
		

	}

	private void insertHeap(Rule r) {
		inputList.add(r);
		int i = inputList.size() -1;
		boolean flag = false;
		while(i > 1 && inputList.get(i/2).getPriority()  < r.getPriority()){
			Rule tmp = inputList.get(i);
			inputList.set(i, inputList.get(i/2));
			inputList.set(i/2, tmp);
			i = i /2 ;
			flag = true;
		}
		if(flag)
			inputList.set(i, r);
	}

	private void buildQueue(List<Rule> rulesList) {
		inputList.addAll(rulesList);
		for (int i = (inputList.size() - 1) / 2; i > 0; i--) {
			maxHeapify(i);
		}

	}

	//Extract max
	private String extractMax() {
		Rule maxPriRule = inputList.get(1);
		int size = inputList.size();
		String max = maxPriRule.getName() + " - " + maxPriRule.getPriority();
		inputList.set(1, inputList.get(size - 1));
		removeRule(size - 1);
		maxHeapify(1);
		return max;
	}
	//remove rules from aganeda mamnger once executed
	private void removeRule(int index) {
		inputList.set(1, inputList.get(index));
		inputList.remove(index);
		
	}

	//max heapify
	private void maxHeapify(int i) {
		int leftChildIndex = 2 * i;
		int rightChildIndex = leftChildIndex + 1;
		int largest;
		Rule currentNode = inputList.get(i);
		if (leftChildIndex < inputList.size()
				&& inputList.get(leftChildIndex).getPriority() > currentNode.getPriority()) {
			largest = leftChildIndex;
		} else {
			largest = i;
		}

		if (rightChildIndex < inputList.size()
				&& inputList.get(rightChildIndex).getPriority() > inputList.get(largest).getPriority()) {
			largest = rightChildIndex;
		}
		if (largest != i) {
			inputList.set(i, inputList.get(largest));
			inputList.set(largest, currentNode);
			maxHeapify(largest);
		}
	}

}
