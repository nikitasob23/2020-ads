package ru.mail.polis.ads.bst;

import org.jetbrains.annotations.NotNull;

/**
 * AVL implementation of binary search tree.
 */

public class AvlBst<Key extends Comparable<Key>, Value>
        implements Bst<Key, Value> {
    private Node root;
    private int size;

    private class Node {
        public Key key;
        public Value value;
        public Node left;
        public Node right;
        public int height;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            height = 1;
        }
    }

    @Override
    public Value get(@NotNull Key key) {
        Node searchNode = get(root, key);
        return searchNode == null ? null : searchNode.value;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        root = put(root, key, value);
    }

    @Override
    public Value remove(@NotNull Key key) {
        Node removeNode = get(root, key);
        if (removeNode == null) {
            return null;
        }
        root = remove(root, key);
        size--;
        return removeNode.value;
    }

    @Override
    public Key min() {
        return root == null ? null : min(root).key;
    }

    @Override
    public Value minValue() {
        return root == null ? null : min(root).value;
    }

    @Override
    public Key max() {
        return root == null ? null : max(root).key;
    }

    @Override
    public Value maxValue() {
        return root == null ? null : max(root).value;
    }

    @Override
    public Key floor(@NotNull Key key) {
        return floor(root, key);
    }

    @Override
    public Key ceil(@NotNull Key key) {
        return ceil(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return root != null ? root.height : 0;
    }

    private Node get(Node node, Key key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            return get(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return get(node.right, key);
        }
        return node;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = put(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        fixHeight(node);
        node = balance(node);
        return node;
    }

    private void fixHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private Node balance(Node node) {
        if (factor(node) > 1) {
            if (factor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (factor(node) < -1) {
            if (factor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node rotateLeft(Node node) {
        Node swapNode = node.right;
        node.right = swapNode.left;
        swapNode.left = node;
        fixHeight(node);
        fixHeight(swapNode);
        return swapNode;
    }

    private Node rotateRight(Node node) {
        Node swapNode = node.left;
        node.left = swapNode.right;
        swapNode.right = node;
        fixHeight(node);
        fixHeight(swapNode);
        return swapNode;
    }

    private int factor(Node node) {
        return height(node.left) - height(node.right);
    }

    private int height(Node node) {
        return node != null ? node.height : 0;
    }

    private Node remove(Node node, Key key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
        } else {
            node = removeNode(node);
        }
        return node;
    }

    private Node removeNode(Node node) {
        if (node.right == null) {
            return node.left;
        }
        if (node.left == null) {
            return node.right;
        }
        Node tempNode = node;
        node = min(tempNode.right);
        node.right = removeMin(tempNode.right);
        node.left = tempNode.left;
        return node;
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node max(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private Key ceil(Node node, Key key) {
        if (node == null) {
            return null;
        }
        Key searchKey = null;
        if (key.compareTo(node.key) < 0) {
            searchKey = ceil(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            searchKey = ceil(node.right, key);
        } else {
            return node.key;
        }
        if (searchKey == null && key.compareTo(node.key) < 0) {
            return node.key;
        }
        return searchKey;
    }

    private Key floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        Key searchKey = null;
        if (key.compareTo(node.key) < 0) {
            searchKey = floor(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            searchKey = floor(node.right, key);
        } else {
            return node.key;
        }
        if (searchKey == null && key.compareTo(node.key) > 0) {
            return node.key;
        }
        return searchKey;
    }
}
