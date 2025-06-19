import java.util.Random;


public class Hi {

	public static void main(String[] args) {

		int[][] numbers = new int[2][100];
		
		populateArray(numbers);
		printArray(numbers);
		sort2dimArrByRow(numbers, 1);
		printArray(numbers);
	}
	
	
	
	public static void sort2dimArrByRow(int [][] arr, int row) {
		
		for (int i = 0; i < arr[0].length -1; ++i) {
			
			for (int j = 0; j < arr[0].length - 1 - i; ++j) {
				
				if (arr[row][j] > arr[row][j+1]) {
					
					int temp = arr[row][j];
					arr[row][j] = arr[row][j+1];
					arr[row][j+1] = temp;
					
					int otherRow = 1 - row;
					int temp2 = arr[otherRow][j];
					arr[otherRow][j] = arr[otherRow][j+1];
					arr[otherRow][j+1] = temp2;
				}
			}
		}
	}
	
	
	
	public static void printArray(int[][] arr) {
		
		for(int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.printf("%3d", arr[i][j]);
			}
			System.out.println();
			System.out.println();
		}
	}
	
	
	
	public static void populateArray(int[][] arr){
		
		Random rand = new Random();
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j]= rand.nextInt(100); 
			}
		}
	}
}