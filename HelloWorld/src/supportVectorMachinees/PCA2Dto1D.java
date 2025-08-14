package supportVectorMachinees;

import java.util.Arrays;

// Using PRINCIPAL COMPONENT ANALISYS to move data from 2d to 1d.
// BASICALLY find a vector along the data varies the most and then project the data onto that vector.
public class PCA2Dto1D {
	
	public static void main(String[] args) {
		// mock data
		double[][] data = {
			{2.5, 2.4},
			{0.5, 0.7},
			{2.2, 2.9},
			{1.9, 2.2},
			{3.1, 3.0},
			{2.3, 2.7},
			{2.0, 1.6},
			{1.0, 1.1},
			{1.5, 1.6},
			{1.1, 0.9}
		};
		
		double[][] covMatrix = computeCovarianceMatrix(data);
		System.out.println("Covariance Matrix:");
		for (double[] row : covMatrix) {
		    System.out.println(Arrays.toString(row));
		}
		
		double[][] eigenInfo = eigenDecompose2x2(covMatrix);
		System.out.println("\nEigenvalues and Eigenvectors:");
		for (double[] row : eigenInfo) {
		    System.out.printf("Eigenvalue: %.4f, Eigenvector: [%.4f, %.4f]%n", row[0], row[1], row[2]);
		}
		
		double[] principalVector = (eigenInfo[0][0] >= eigenInfo[1][0])
		        ? new double[]{eigenInfo[0][1], eigenInfo[0][2]}
		        : new double[]{eigenInfo[1][1], eigenInfo[1][2]};
		
		
		double[] projected = projectOntoPrincipalComponent(data, principalVector);
		System.out.println("\nProjected 1D coordinates:");
		System.out.println(Arrays.toString(projected));
		}
	

	// Compute covariance matrix for 2D points
    public static double[][] computeCovarianceMatrix(double[][] data) {
		int n = data.length;
		double meanX = 0, meanY = 0;

        //Calculate mean for all the points
        for (double[] point : data) {
            meanX += point[0];
            meanY += point[1];
        }
        meanX /= n;
        meanY /= n;

        // center the data by adjusting by mean
        double covXX = 0, covXY = 0, covYY = 0;
        for (double[] point : data) {
            double dx = point[0] - meanX;
            double dy = point[1] - meanY;
            covXX += dx * dx;
            covXY += dx * dy;
            covYY += dy * dy;
        }

        covXX /= (n - 1);
        covXY /= (n - 1);
        covYY /= (n - 1);

        return new double[][]{
            {covXX, covXY},
            {covXY, covYY}
        };
    }
    
    // Eigen-decomposition for a symmetric 2x2 matrix
    public static double[][] eigenDecompose2x2(double[][] matrix) {
        double a = matrix[0][0];
        double b = matrix[0][1];
        double c = matrix[1][1];

        // equation: λ^2 - (a + c)λ + (ac - b^2) = 0
        double trace = a + c;
        double det = a * c - b * b;
        double term = Math.sqrt(trace * trace / 4 - det);

        double lambda1 = trace / 2 + term;
        double lambda2 = trace / 2 - term;

        // eigenvectors
        double[] v1, v2;
        if (b != 0) {
            v1 = new double[]{lambda1 - c, b};
            v2 = new double[]{lambda2 - c, b};
        } else {
            v1 = new double[]{1, 0};
            v2 = new double[]{0, 1};
        }

        // normalize vectors
        v1 = normalize(v1);
        v2 = normalize(v2);

        return new double[][]{
            {lambda1, v1[0], v1[1]},
            {lambda2, v2[0], v2[1]}
        };
    }

    // normalize vector
    private static double[] normalize(double[] v) {
        double len = Math.sqrt(v[0] * v[0] + v[1] * v[1]);
        return new double[]{v[0] / len, v[1] / len};
    }
    
    public static double[] projectOntoPrincipalComponent(double[][] data, double[] principalVector) {
        double[] projections = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            projections[i] = data[i][0] * principalVector[0] + data[i][1] * principalVector[1];
        }
        return projections;
    }
    
}
