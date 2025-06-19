import java.util.Collection;
import java.util.Iterator;


public class TreeBinary {

	public static void main(String[] args) {
	
	
	BST<Integer> trii = new BST<>();
	
	trii.insert(4);
	trii.insert(6);
	trii.insert(57);
	trii.insert(654);
	trii.insert(754);
	trii.insert(764);
	trii.insert(54);
	trii.insert(17654);
	
	System.out.println(trii.search(4));
	System.out.println(trii.search(45));
	System.out.println(" --- ");
	trii.inorder();
	System.out.println(" --- ");
	trii.postorder();
	System.out.println(" --- ");
	trii.preorder();	
	
	
	System.out.println(trii.BFS(654));
	
	}
	
	
	
}

	
	class TreeNode<E> {
		protected E element;
		protected TreeNode<E> left;
		protected TreeNode<E> right;
		
		public TreeNode(E e) {
			element = e;
		}
	}