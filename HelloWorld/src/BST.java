import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

import trees.Tree;


public class BST<E> implements Tree<E> {
protected TreeNode<E> root;
protected int size =0;
protected java.util.Comparator<E> c;



@SuppressWarnings("unchecked")
public BST() {
	this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
}
public BST(java.util.Comparator<E> c) {
	this.c = c;
}
@SuppressWarnings("unchecked")
public BST (E[] objects) {
	this.c = (e1, e2) -> ((Comparable<E>)e1).compareTo(e2);
	for (int i = 0; i < objects.length; i++) {
		add(objects[i]);
	}
}



public boolean search(E e) {
	TreeNode<E> current = root;
	
	while (current != null) {
		if (c.compare(e, current.element) < 0) {
			current = current.left;
		} else if (c.compare(e, current.element) > 0) {
			current = current.right;
		} else {
			return true;
		}
	}
	return false;
}




public boolean insert(E e) {
	
	if (root == null) {
		root = createNode(e);
		} else {
			
		TreeNode<E> parent = null;
	 	TreeNode<E> current = root;
	
	 	while (current != null) {
			if (c.compare(e, current.element) < 0){
				parent = current;
				current = current.left;
			} else if (c.compare(e, current.element) > 0) {
				parent = current;
				current = current.right;
			} else {
				return false;
			}
	 	}
			
		if (c.compare(e, parent.element) < 0) {
			parent.left = createNode(e);
		} else {
			parent.right = createNode(e);
		}
		
	}
	size++;
	return true;
}


public boolean BFS(E e) {
	
	Queue<TreeNode<E>> q = new LinkedList<>();
	q.add(root);
	
	
	while (!q.isEmpty()) {
		 
		TreeNode<E> curr = q.poll();
		System.out.print("curr ");
		System.out.println(curr.element);
		
		if (curr.element.equals(e)) {
			return true;
		}
		if (curr.left != null) {
			q.add(curr.left);
		}
		if (curr.right != null) {
			q.add(curr.right);
		}
		
	}
	return false;
}




public TreeNode<E> createNode(E e){
	return new TreeNode<>(e);
}


@Override
public void inorder() {
	inorder(root);
}
protected void inorder(TreeNode<E> root) {
	if (root == null) return;
	
	inorder(root.left);
	System.out.println(root.element);
	inorder(root.right);
}


@Override
public void postorder() {
	postorder(root);
}
protected void postorder(TreeNode<E> root) {
	
	if (root == null) return;
	
	postorder(root.left);
	postorder(root.right);
	System.out.println(root.element);
	
}

@Override
public void preorder() {
	preorder(root);
}
protected void preorder(TreeNode<E> root) {
if (root == null) return;
	
	System.out.println(root.element);
	postorder(root.left);
	postorder(root.right);
	
}




@Override
public Iterator<E> iterator() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public boolean addAll(Collection<? extends E> c) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void clear() {
	// TODO Auto-generated method stub
	
}
@Override
public boolean delete(E e) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public int getSize() {
	// TODO Auto-generated method stub
	return 0;
}

}

