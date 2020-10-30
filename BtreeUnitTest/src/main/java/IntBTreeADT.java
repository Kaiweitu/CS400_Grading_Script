import java.util.ArrayList;

/**
 * Interface for a BTree of Integers that uses ArrayLists to implement key and child lists
 * must use an inner class for nodes
 * must be consistent with https://www.cs.usfca.edu/~galles/visualization/BTree.html
 */
public interface IntBTreeADT {
	
	
	
	/**
	 * public insert method
	 * must be a recursive implementation
	 * @param key the key to be inserted
	 * @throws IllegalKeyException if key is null or is a duplicate
	 */
	public void insert(Integer key) throws IllegalArgumentException;
	
	/**
	 * visualizes this Btree... code provided in starter file
	 */
	public void visualize();
	
	
	/**
	 * preorder traversal, placed into an ArrayList
	 * First visit a node, adding all its keys to the ArrayList
	 * Use the same logic as the visualize, but just place keys into a single arrayList
	 * @return an ArrayList<Integer> of the preOrder traversal of this BTree
	 */
	public ArrayList<Integer> preOrderTraversal();
	
	
	/**
	 * if key does not exist (or is null), do nothing
	 * otherwise, delete this key, replace with inorder predecessor
	 *  	and rebalance using the B-Tree rules 
	 *  	and that are consistent with the B-Tree visualizer noted in the class header
	 * must be a recursive implementation
	 * @param key the key to delete
	 */
	public void delete(Integer key);
	
	
	/**
	 * searches for key in tree using O(log N) efficiency
	 * @param key the key to search for
	 * @return true if key is in the tree, false if key is null or not in tree
	 */
	public boolean contains(Integer key);
	
}
