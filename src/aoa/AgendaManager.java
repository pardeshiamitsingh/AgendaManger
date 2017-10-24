package aoa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Amit
 * 
 */
public class AgendaManager {

	private List<Rule> inputList = new ArrayList<Rule>();
	int cycle = 0;

	// constructer of Agenda manager
	public AgendaManager() {
		URL url = getClass().getResource("input1.txt");

		final String dir = System.getProperty("user.dir");
		String max = null;
		//Read file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(dir + "\\input1"))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				//  add rules to AgendaManager
				addRules(sCurrentLine);
				// find max and print it
				max = extractMax();
				System.out.println("===========CYCLE " + cycle + "===============");
				System.out.println("ACTIVATED RULES");
				for(int i = 1 ; i < inputList.size() ; i++)
					System.out.print(inputList.get(i));
				System.out.println();
				System.out.println("EXECUTED RULE");
				System.out.println(max);
			}
            
			while(inputList.size() > 2){
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
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		long startTime = new Date().getTime();
		new AgendaManager();
		long endTime = new Date().getTime();
		System.out.println((endTime- startTime));

	}
	// adds rules to rule enngine
	private void addRules(String rulesStr) {

		List<Rule> readLineList = new ArrayList<Rule>();
		if (cycle == 0) {
			readLineList.add(new Rule());

		}
		cycle++;
		String[] currentLineArr = rulesStr.split("\\|");
		for (String s : currentLineArr) {
			Rule r = new Rule();
			String strArr[] = s.split(",");
			String str1 = strArr[0];
			str1 = str1.trim();
			r.setName(str1.substring(1));
			String str = strArr[1];
			str = str.substring(0, str.length() - 1);
			str = str.trim();
			r.setPriority(Integer.parseInt(str));
			readLineList.add(r);
		}
		//build priority queue
		buildQueue(readLineList);

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