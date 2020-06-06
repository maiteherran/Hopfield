import java.io.BufferedReader;
import java.io.InputStreamReader;;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String args[]) throws Exception {
        int nNeurons;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        List<double[]> trainingPatterns = new LinkedList<>();

        System.out.println("Enter number of neurons (pattern length)");
        nNeurons = Integer.valueOf(reader.readLine());
        Hopfield hopfield = new Hopfield(nNeurons);
        boolean trained = false;

        while(running) {
            System.out.println("$> Please choose one of the following commands \n" +
                    "'add' to add a training pattern\n" +
                    "'train' to train the network with the previoulsy added patterns\n" +
                    "'train default'\n" +
                    "'run' to add a new pattern   \n" +
                    "'exit' to exit");
            String cmd = reader.readLine();
            switch(cmd) {
                case "add":
                    System.out.println("$> Insert 1 training pattern: ");
                    int i = 0;
                    double[] trainingPattern = new double[nNeurons];
                    double[][] trainingPatternAux = new double[nNeurons][1];
                    for (String number : reader.readLine().split("\\s")) {
                        if(i >= nNeurons ) {
                            System.out.println("Wrong pattern");
                            break;
                        } else {
                            double x = Double.parseDouble(number);
                            trainingPattern[i] = x;
                            trainingPatternAux[i][0] = x;
                            i++;
                        }
                    }
                    if(i != nNeurons) {
                        System.out.println("Wrong pattern. ");
                    } else {
                        trainingPatterns.add(trainingPattern);
                        printPattern(trainingPattern, nNeurons);
                    }
                    break;
                case "train":
                    if(trainingPatterns.size() == 0){
                        System.out.println("You must first provide training patterns");
                    } else {
                        hopfield.train(trainingPatterns);
                        trained = true;
                    }
                    break;
                case "train default":
                    nNeurons = 25;
                    double[] jPattern = new double[]{1,1,1,1,1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,1,1,1,-1,-1}; //j
                    double[] ePattern = new double[]{1,1,1,1,1,1,-1,-1,-1,-1,1,1,1,1,1,1,-1,-1,-1,-1,1,1,1,1,1};//e
                    double[] cPattern = new double[]{-1,1,1,1,1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,1,1,1};//c
                    double[] hPattern = new double[]{1,-1,-1,-1,1,1,-1,-1,-1,1,1,1,1,1,1,1,-1,-1,-1,1,1,-1,-1,-1,1};//h
                    double[] iPattern = new double[]{-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1}; //i
                    double[] kPattern = new double[]{1,-1,-1,1,-1,1,-1,1,-1,-1,1,1,-1,-1,-1,1,-1,1,-1,-1,1,-1,-1,1,-1};

                    System.out.println("Training patterns: ");
                    printPattern(jPattern, nNeurons);
                    printPattern(ePattern, nNeurons);
                    printPattern(cPattern, nNeurons);
                    printPattern(hPattern, nNeurons);
                    printPattern(iPattern, nNeurons);
                    printPattern(kPattern, nNeurons);

                    trainingPatterns.add(jPattern);
                    trainingPatterns.add(ePattern);
                    trainingPatterns.add(cPattern);
                    trainingPatterns.add(hPattern);
                    trainingPatterns.add(iPattern);
                    trainingPatterns.add(kPattern);

                    hopfield.train(trainingPatterns);
                    trained = true;

                    break;
                case "run":
                    if(!trained){
                        System.out.println("You must first train the network");
                    } else {
                        double[] inputPat = new double[nNeurons];
                        i = 0;
                        for (String number : reader.readLine().split("\\s")) {
                            if(i >= nNeurons ) {
                                System.out.println("Wrong pattern");
                                break;
                            } else {
                                double x = Double.parseDouble(number);
                                inputPat[i] = x;
                                i++;
                            }
                        }
                        if(i != nNeurons) {
                            System.out.println("Wrong pattern.");
                        } else {
                            Matrix outputPat = hopfield.run(inputPat);
                            double[] arr = new double[nNeurons];
                            int k = 0;
                            for (i = 0; i < outputPat.getValuesMatrix().length; i ++){
                                for (int j = 0; j < outputPat.getValuesMatrix()[0].length; j ++){
                                    arr[k++] = (int)outputPat.getValuesMatrix()[i][j];
                                }
                            }
                            System.out.println("Input pattern: ");
                            printPattern(inputPat, nNeurons);
                            System.out.println("Output pattern: ");
                            printPattern(arr, nNeurons);
                        }
                    }

                    break;
                case "exit":
                    running = false;
                    break;
            }
        }
    }

    private static void printPattern(double[] trainingPattern, int nNeurons) {
        int j = 0;
        StringBuffer sbuffer = new StringBuffer();
        for(int i = 0; i < nNeurons ; i ++){
            if(i % Math.sqrt(nNeurons) == 0){
                sbuffer.append("\n");
            }
            if(trainingPattern[i] == 1){
                sbuffer.append((int)trainingPattern[i]);
            } else if (trainingPattern[i] == -1){
                sbuffer.append(" ");
            }
            sbuffer.append(" ");

        }
        System.out.println(sbuffer);
    }


}



