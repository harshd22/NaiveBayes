
//took some ideas from a friend
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class NaiveBayes {

	ArrayList<Instance> trainingInstances;
	ArrayList<Instance> testInstances;
	float[] trueSpam;
	float[] falseSpam;
	float[] notTrueSpam;
	float[] notFalseSpam;
	float spam = 1;
	float notSpam = 1;
	int size;

	public NaiveBayes(File train, File test) {
		trainingInstances = new ArrayList<>();
		testInstances = new ArrayList<>();

		readFile(train, trainingInstances, true);
		readFile(test, testInstances, false);
		train();
		test();
	}

	private void test() {

		for (Instance instance : testInstances) {
			float spamProb = spam / (spam + notSpam);
			float notSpamProb = notSpam / (spam + notSpam);
			for (int i = 0; i < size; i++) {
				if (instance.attributes.get(i) == 0) {
					spamProb *= falseSpam[i] / (trueSpam[i] + falseSpam[i]);
					notSpamProb *= notFalseSpam[i] / (notTrueSpam[i] + notFalseSpam[i]);
				} else {
					spamProb *= trueSpam[i] / (trueSpam[i] + falseSpam[i]);
					notSpamProb *= notTrueSpam[i] / (notTrueSpam[i] + notFalseSpam[i]);
				}
			}
			float totalProb = spamProb + notSpamProb;
			System.out.print("Spam probability : " + spamProb / totalProb);
			System.out.print(" | Spam probability : " + notSpamProb / totalProb);
			System.out.print(" | Spam :");
			System.out.print(spamProb > notSpamProb ? "1" : "0");
			System.out.println();
		}

	}

	private void train() {
		size = trainingInstances.get(0).attributes.size();
		trueSpam = new float[size];
		falseSpam = new float[size];
		notTrueSpam = new float[size];
		notFalseSpam = new float[size];
		for (int i = 0; i < size; i++) {
			trueSpam[i] = 1;
			falseSpam[i] = 1;
			notTrueSpam[i] = 1;
			notFalseSpam[i] = 1;
		}
		for (Instance instance : trainingInstances) {
			if (instance.Class == 0) {
				notSpam += 1;
			} else {
				spam += 1;
			}
			for (int i = 0; i < size; i++) {
				if (instance.Class == 0) {
					if (instance.attributes.get(i) == 1) {
						notTrueSpam[i] += 1;
					} else {
						notFalseSpam[i] += 1;
					}
				} else {
					if (instance.attributes.get(i) == 1) {
						trueSpam[i] += 1;
					} else
						falseSpam[i] += 1;
				}
			}

		}

	}

	private void readFile(File train, ArrayList<Instance> instances, boolean isTraining) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(train));
			String line = "";
			while ((line = br.readLine()) != null) {
				Scanner s = new Scanner(line);
				ArrayList<Integer> attributes = new ArrayList<>();
				int Class = -1;
				while (s.hasNextInt()) {
					int attr = s.nextInt();
					if (!s.hasNextInt() && isTraining) {
						Class = attr;
					} else {
						attributes.add(attr);
					}
				}

				instances.add(new Instance(attributes, Class));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		new NaiveBayes(new File(args[0]), new File(args[1]));
	}

}
