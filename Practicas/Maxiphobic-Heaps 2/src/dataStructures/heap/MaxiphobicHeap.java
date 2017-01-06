package dataStructures.heap;

// Solo tienes que completar el método merge.

/**
 * Heap implemented using maxiphobic heap-ordered binary trees.
 *
 * @param <T>
 *            Type of elements in heap.
 */
public class MaxiphobicHeap<T extends Comparable<? super T>> implements Heap<T> {

    private static class Tree<E> {
        private E elem;
        private int size;
        private Tree<E> left;
        private Tree<E> right;

        public Tree(E x, Tree<E> l, Tree<E> r) {
            elem = x;
            left = l;
            right = r;
            size = 1 + size(l) + size(r);
        }
    }

    private static int size(Tree<?> heap) {
        return heap == null ? 0 : heap.size;
    }

    /**
     *
     * Merge implementado creando nuevos nodos
     *
     */
    private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1, Tree<T> h2) {

        // Si algún montículo es null, se devuelve el otro
        if(h1 == null)
            return h2;

        if(h2 == null)
            return h1;

        // Comprobación del montículo con la menor clave
        if ((h1.elem.compareTo(h2.elem) > 0) || (h1.elem == h2.elem && h1.size > h2.size)) {
            Tree<T> aux = h2;
            h2 = h1;
            h1 = aux;
        }

        // Si algún hijo es null, su tamaño es considerado 0
        final int leftSize = (h1.left != null) ? h1.left.size : 0;
        final int rightSize = (h1.right != null) ? h1.right.size : 0;

        // Comprobación del mayor montículo y colocación del mismo a la derecha
        if (leftSize > rightSize && leftSize > h2.size) {

            return new Tree<>(h1.elem, merge(h1.right, h2), h1.left);

        } else if (rightSize > leftSize && rightSize > h2.size){

            return new Tree<>(h1.elem, merge(h1.left, h2), h1.right);

        } else {

            return new Tree<>(h1.elem, merge(h1.left, h1.right), h2);

        }

    }

    private Tree<T> root;

    /**
     * Creates an empty Maxiphobic Heap.
     * <p>
     * Time complexity: O(1)
     */
    public MaxiphobicHeap() {
        root = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     *
     * @throws <code>EmptyHeapException</code>
     *             if heap stores no element.
     */
    @Override
    public T minElem() {
        if (isEmpty())
            throw new EmptyHeapException("minElem on empty heap");
        else
            return root.elem;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     *
     * @throws <code>EmptyHeapException</code>
     *             if heap stores no element.
     */
    @Override
    public void delMin() {
        if (isEmpty())
            throw new EmptyHeapException("delMin on empty heap");
        else
            root = merge(root.left, root.right);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     */
    @Override
    public void insert(T value) {
        Tree<T> newHeap = new Tree<>(value, null, null);
        root = merge(root, newHeap);
    }

    public void clear() {
        root = null;
    }
}
