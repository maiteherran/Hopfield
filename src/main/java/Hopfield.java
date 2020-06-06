
import java.util.List;

public class Hopfield {
    private int nNeurons;
    private List<double[]> trainingPatterns;
    private Matrix weights;

    public Hopfield(int n){
        this.nNeurons = n;
    }

    public void train(List<double[]> trainingPatterns) throws Exception {
        this.trainingPatterns = trainingPatterns;
        double[][] trainingPatternsAsCols = trainingPatternsAsCols();
        Matrix k = new Matrix(trainingPatternsAsCols);
        Matrix kt = k.transpose();
        weights = k.multiply(kt);
        weights = weights.multiplyByScalar(1/(double)nNeurons);
        weights = weights.ZeroDiagonal();
        System.out.println("K matrix (patterns as columns): ");
        System.out.println(k.toString());
        System.out.println("Weight matrix: ");
        System.out.println(weights.toString());
    }

    public Matrix run(double[] inputPat) throws Exception {
        double[][] inputPattern = new double[nNeurons][1];
        int i = 0;
        for (double number : inputPat) {
            inputPattern[i][0] = number;
            i++;
        }
        Matrix s = new Matrix(nNeurons, 1);
        Matrix prevS = new Matrix(inputPattern);
        boolean converged = false;
        int it = 0;

        while(!converged) {
            it++;
            System.out.println("Iteration number: " + it);
            System.out.println("Previous state");
            printPattern(prevS.getValuesMatrix(), nNeurons);
            s = weights.multiply(prevS);
            s = s.applySignFunction();
            System.out.println("Current state");
            printPattern(s.getValuesMatrix(), nNeurons);
            if(s.converged(prevS)){
                converged = true;
                System.out.println("Converged in "+ it + " iterations");
            }
            prevS = s;
        }
        return s;
    }

    private double[][] trainingPatternsAsCols() {
        double[][] ret = new double[nNeurons][trainingPatterns.size()];
        for(int i = 0 ; i < nNeurons ; i++) {
            for(int j = 0; j < trainingPatterns.size(); j ++){
                ret[i][j] = trainingPatterns.get(j)[i];
            }
        }
        return ret;
    }

    private static void printPattern(double[][] trainingPattern, int nNeurons) {
        int j = 0;
        StringBuffer sbuffer = new StringBuffer();
        for(int i = 0; i < nNeurons ; i ++){
            if(i % Math.sqrt(nNeurons) == 0){
                sbuffer.append("\n");
            }
            if(trainingPattern[i][0] == 1){
                sbuffer.append((int)trainingPattern[i][0]);
            } else if (trainingPattern[i][0] == -1){
                sbuffer.append(" ");
            }
            sbuffer.append(" ");

        }
        System.out.println(sbuffer);
    }

}
