package ru.mail.polis.ads.hash;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Hash<Key, Value> implements HashTable<Key, Value> {

    private int initialCapacity = 16;
    private final float loadFactor = 0.75f;
    private int size = 0;
    private ElementData<Key, Value>[] elementData = new ElementData[initialCapacity];

    private class ElementData<Key, Value> {
        private final List<Key> keys;
        private final List<Value> values;

        public ElementData(Key key, Value value) {
            keys = new ArrayList<>(1);
            keys.add(key);
            values = new ArrayList<>(1);
            values.add(value);
        }

        public void changeOrAdd(Key key, Value value) {
            int keyNum = keys.indexOf(key);
            if (keyNum == -1) {
                keys.add(key);
                values.add(value);
            } else {
                values.set(keyNum, value);
            }
        }

        public Value remove(Key key) {
            for (int keyNum = 0; keyNum < size(); keyNum++) {
                if (keys.get(keyNum).equals(key)) {
                    keys.remove(keyNum);
                    return values.remove(keyNum);
                }
            }
            return null;
        }

        public int size() {
            return keys.size();
        }
    }

    @Override
    public Value get(@NotNull Key key) {
        int hashNum = getHashNum(key);
        ElementData<Key, Value> elem = elementData[hashNum];
        if (elem == null) {
            return null;
        }
        for (int keyNum = 0; keyNum < elem.size(); keyNum++) {
            if (elem.keys.get(keyNum).equals(key)) {
                return elem.values.get(keyNum);
            }
        }
        return null;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        int hashNum = getHashNum(key);
        if (elementData[hashNum] == null) {
            elementData[hashNum] = new ElementData(key, value);
            size++;
        } else {
            elementData[hashNum].changeOrAdd(key, value);
        }
        if (size > loadFactor * initialCapacity) {
            increaseHashSize();
        }
    }

    @Override
    public Value remove(@NotNull Key key) {
        int hashNum = getHashNum(key);
        ElementData<Key, Value> elem = elementData[hashNum];
        if (elem == null) {
            return null;
        }
        Value removeValue = elem.remove(key);
        size--;
        if (elem.size() == 0) {
            elementData[hashNum] = null;
        }
        return removeValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getHashNum(Key key) {
        return (key.hashCode() & 0x7fffffff) % initialCapacity;
    }

    private void increaseHashSize() {
        initialCapacity *= 2;
        size = 0;
        ElementData<Key, Value>[] oldElementData = elementData;
        elementData = new ElementData[initialCapacity];
        for (int hashNum = 0; hashNum < oldElementData.length; hashNum++) {
            if (oldElementData[hashNum] != null) {
                for (int keyNum = 0; keyNum < oldElementData[hashNum].size(); keyNum++) {
                    ElementData<Key, Value> oldElem = oldElementData[hashNum];
                    put(oldElem.keys.get(keyNum), oldElem.values.get(keyNum));
                }
            }
        }
    }
}
