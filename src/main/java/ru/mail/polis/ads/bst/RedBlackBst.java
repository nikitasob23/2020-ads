package ru.mail.polis.ads.bst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * LLRB implementation of binary search tree.
 */
public class RedBlackBst<Key extends Comparable<Key>, Value>
        implements Bst<Key, Value> {

    private static final boolean BLACK = false;
    private static final boolean RED = true;

    private Node root;
    private int size;

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        boolean color;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            color = RED;
        }
    }

    @Nullable
    @Override
    public Value get(@NotNull Key key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    @Nullable
    @Override
    public Value remove(@NotNull Key key) {
        Value removedValue = get(key);
        if (removedValue == null) {
            return null;
        }
        root.color = RED;
        root = remove(root, key);
        size--;
        return removedValue;
    }

    @Nullable
    @Override
    public Key min() {
        Node min = min(root);
        return min == null ? null : min.key;
    }

    @Nullable
    @Override
    public Value minValue() {
        Node min = min(root);
        return min == null ? null : min.value;
    }

    @Nullable
    @Override
    public Key max() {
        Node max = max(root);
        return max == null ? null : max.key;
    }

    @Nullable
    @Override
    public Value maxValue() {
        Node max = max(root);
        return max == null ? null : max.value;
    }

    @Nullable
    @Override
    public Key floor(@NotNull Key key) {
        return floor(root, key);
    }

    @Nullable
    @Override
    public Key ceil(@NotNull Key key) {
        return ceil(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    private Node get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int nodeComparison = key.compareTo(node.key);
        if (nodeComparison < 0) {
            return get(node.left, key);
        } else if (nodeComparison > 0) {
            return get(node.right, key);
        }
        return node;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int nodeComparison = key.compareTo(node.key);
        if (nodeComparison < 0) {
            node.left = put(node.left, key, value);
        } else if (nodeComparison > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return fixUp(node);
    }

    private Node remove(Node node, Key key) {
        if (node == null)
            return null;
        int nodeComparison = key.compareTo(node.key);
        if (nodeComparison < 0) {
            if (!isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = remove(node.left, key);
        } else {
            if (isRed(node.left)) {
                node = rotateRight(node);
                node.right = remove(node.right, key);
            } else if (nodeComparison == 0 && node.right == null) {
                return null;
            } else {
                if (node.right != null && !isRed(node.right) && !isRed(node.right.left)) {
                    node = moveRedRight(node);
                }
                if (node.key == key) {
                    Node minNode = min(node.right);
                    node.value = minNode.value;
                    node.key = minNode.key;
                    node.right = removeMin(node.right);
                } else {
                    node.right = remove(node.right, key);
                }
            }
        }
        return fixUp(node);
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            return null;
        }
        if (!isRed(node.left) && !isRed(node.right.left)) {
            node = moveRedLeft(node);
        }
        node.left = removeMin(node.left);
        return fixUp(node);
    }

    private Node moveRedLeft(Node node) {
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
        }
        return node;
    }

    private Node fixUp(Node node) {
        boolean leftIsRed = isRed(node.left);
        boolean rightIsRed = isRed(node.right);
        if (!leftIsRed && rightIsRed) {
            node = rotateLeft(node);
        } else if (leftIsRed && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (leftIsRed && rightIsRed) {
            node = flipColors(node);
        }
        return node;
    }

    private Node flipColors(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
        return node;
    }

    private Node rotateLeft(Node node) {
        Node rightNode = node.right;
        node.right = rightNode.left;
        rightNode.left = node;
        rightNode.color = node.color;
        node.color = RED;
        return rightNode;
    }

    private Node rotateRight(Node node) {
        Node leftNode = node.left;
        node.left = leftNode.right;
        leftNode.right = node;
        leftNode.color = node.color;
        node.color = RED;
        return leftNode;
    }

    private boolean isRed(Node node) {
        if (node == null || node.color == BLACK) {
            return false;
        }
        return true;
    }

    private Node min(Node min) {
        if (min == null) {
            return null;
        }
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    private Node max(Node max) {
        if (max == null) {
            return null;
        }
        while (max.right != null) {
            max = max.right;
        }
        return max;
    }

    private Key ceil(Node node, Key key) {
        if (node == null) {
            return null;
        }
        Key searchKey;
        int nodeComparison = key.compareTo(node.key);
        if (nodeComparison < 0) {
            searchKey = ceil(node.left, key);
        } else if (nodeComparison > 0) {
            searchKey = ceil(node.right, key);
        } else {
            return node.key;
        }
        if (searchKey == null && nodeComparison < 0) {
            return node.key;
        }
        return searchKey;
    }

    private Key floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        Key searchKey;
        int nodeComparison = key.compareTo(node.key);
        if (nodeComparison < 0) {
            searchKey = floor(node.left, key);
        } else if (nodeComparison > 0) {
            searchKey = floor(node.right, key);
        } else {
            return node.key;
        }
        if (searchKey == null && nodeComparison > 0) {
            return node.key;
        }
        return searchKey;
    }
}
