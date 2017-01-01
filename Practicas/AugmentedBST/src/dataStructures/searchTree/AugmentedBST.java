/**
* @author Nacho González-Garilleti <<nachox07@users.noreply.github.com>>
*
* AugmentedBST trees implementation
*/

package dataStructures.searchTree;

/**
 * Search tree implemented using an unbalanced binary search tree augmented with
 * weight on nodes. Note that elements should define an order relation (
 * {@link java.lang.Comparable}).
 *
 * @param <T>
 *            Type of keys.
 */
public class AugmentedBST<T extends Comparable<? super T>> {

    // class for implementing one one in search tree
    private static class Tree<E> {
        E key; // value stored in node
        int weight; // weight of node: total number of elements stored in tree
                    // rooted at this node
        Tree<E> left;
        Tree<E> right;

        public Tree(E k) {
            key = k;
            weight = 1;
            left = null;
            right = null;
        }
    }

    private Tree<T> root; // reference to root node of binary search tree

    /**
     * Creates an empty unbalanced binary search tree.
     * <p>
     * Time complexity: O(1)
     */
    public AugmentedBST() {
        root = null;
    }

    /**
     * <p>
     * Time complexity: O(1)
     */
    public boolean isEmpty() {
        return root == null;
    }

    private static <T> int weight(Tree<T> node) {
        return node == null ? 0 : node.weight;
    }

    /**
     * <p>
     * Time complexity: O(1)
     */
    public int size() {
        return weight(root);
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public void insert(T k) {
        root = insertRec(root, k);
    }

    // returns modified tree
    private Tree<T> insertRec(Tree<T> node, T key) {
        if (node == null) {
            node = new Tree<T>(key);
        } else if (key.compareTo(node.key) < 0)
            node.left = insertRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = insertRec(node.right, key);
        else
            node.key = key;

        // recompute weight for this node after insertion
        node.weight = 1 + weight(node.left) + weight(node.right);

        return node;
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public T search(T key) {
        return searchRec(root, key);
    }

    private static <T extends Comparable<? super T>> T searchRec(Tree<T> node,
            T key) {
        if (node == null)
            return null;
        else if (key.compareTo(node.key) < 0)
            return searchRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            return searchRec(node.right, key);
        else
            return node.key;
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public boolean isElem(T key) {
        return search(key) != null;
    }

    /**
     * precondition: node and temp are non-empty trees Removes node with minimum
     * key from tree rooted at node. Before deletion, key is saved into temp
     * node. returns modified tree (without min key)
     */
    private static <T extends Comparable<? super T>> Tree<T> split(
            Tree<T> node, Tree<T> temp) {
        if (node.left == null) {
            // min node found, so copy min key in temp node
            temp.key = node.key;
            return node.right; // remove node
        } else {
            // remove min from left subtree
            node.left = split(node.left, temp);
            return node;
        }
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public void delete(T key) {
        root = deleteRec(root, key);
    }

    // returns modified tree
    private Tree<T> deleteRec(Tree<T> node, T key) {
        if (node == null)
            ; // key not found; do nothing
        else {
            if (key.compareTo(node.key) < 0)
                node.left = deleteRec(node.left, key);
            else if (key.compareTo(node.key) > 0)
                node.right = deleteRec(node.right, key);
            else {
                if (node.left == null)
                    node = node.right;
                else if (node.right == null)
                    node = node.left;
                else
                    node.right = split(node.right, node);
            }
            // recompute weight for this node after deletion
            node.weight = 1 + weight(node.left) + weight(node.right);
        }
        return node;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        String className = getClass().getName().substring(
                getClass().getPackage().getName().length() + 1);
        return className + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.key + "," + tree.weight + "," + toStringRec(tree.right)
                + ">";
    }

    // You should provide EFFICIENT implementations for the following methods

    // returns i-th smallest key in BST (i=0 means returning the smallest value
    // in tree, i=1 the next one and so on).
    public T select(int i) {

        Tree<T> nodo = root;
        T result = null;

        int peso = weight(nodo) / 2;

        while (result == null && nodo.left != null) {

            if(i > this.size()) {

                nodo.left = null;

            }else if(i < peso || i > peso) {

                int dif = weight(root)/2 - 1;

                peso = (peso > i) ? peso - dif : peso + dif;
                nodo = (peso > i) ? nodo.left : nodo.right;

                if(nodo.left.left == null && i == peso - 1) {

                    result = nodo.left.key;

                } else if (nodo.right.left == null && i == peso - 1) {

                    result = nodo.right.key;

                } else {

                    result = nodo.key;

                }

            } else if(i == peso) {

                result = nodo.key;

            }

        }

        return result;
    }

    // returns largest key in BST whose value is less than or equal to k.
    public T floor(T k) {

        Tree<T> nodo = root;
        T result = null;

        while (result != k && nodo != null) {

            if (k.compareTo(nodo.key) > 0) {

                if((result == null || k.compareTo(result) < 0) || (k.compareTo(nodo.key) >= 0 && result.compareTo(nodo.key) < 0))
                    result = nodo.key;

                nodo = nodo.right;

            } else if(k.compareTo(nodo.key) < 0) {

                if((result == null || k.compareTo(result) < 0) || (k.compareTo(nodo.key) >= 0 && result.compareTo(nodo.key) < 0))
                    result = nodo.key;

                nodo = nodo.left;

            } else if (nodo.key.compareTo(k) == 0){

                result = nodo.key;

            }

            if(result.compareTo(k) > 0)
                result = null;

        }

        return result;

    }

    // returns smallest key in BST whose value is greater than or equal to k.
    public T ceiling(T k) {

        Tree<T> nodo = root;
        T result = null;

        while (result != k && nodo != null) {

            if (k.compareTo(nodo.key) > 0) {

                if((result == null || k.compareTo(result) > 0) || (k.compareTo(nodo.key) <= 0 && result.compareTo(nodo.key) > 0))
                    result = nodo.key;

                nodo = nodo.right;

            } else if(k.compareTo(nodo.key) < 0) {

                if((result == null || k.compareTo(result) > 0) || (k.compareTo(nodo.key) <= 0 && result.compareTo(nodo.key) > 0))
                    result = nodo.key;

                nodo = nodo.left;

            } else if (nodo.key.compareTo(k) == 0){

                result = nodo.key;

            }

            if(result.compareTo(k) < 0)
                result = null;

        }

        return result;

    }

    // returns number of keys in BST whose values are less than k.
    public int rank(T k) {
        return rankRec(root, k);
    }

    private int rankRec(Tree<T> node, T key) {
        int sum = 0;

        if (node != null) {
            if(key.compareTo(node.key) > 0)
                sum++;
            sum += rankRec(node.left, key) + rankRec(node.right, key);
        }

        return sum;
    }

    // returs number of keys in BST whose values are in range lo to hi.
    public int size(T low, T high) {
        return sizeRec(root, low, high);
    }

    private int sizeRec(Tree<T> node, T l, T h) {
        int sum = 0;

        if (node != null) {
            if(h.compareTo(node.key) >= 0 && l.compareTo(node.key) <= 0)
                sum++;
            sum += sizeRec(node.left, l, h) + sizeRec(node.right, l, h);
        }

        return sum;
    }

}
