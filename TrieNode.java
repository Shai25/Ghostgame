package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;
    private static HashMap<String, ArrayList<String>> buckets = new HashMap<>();

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        if (s.length() != 1) {
            if (buckets.containsKey(s.charAt(0) + "")) {
                ArrayList<String> temp = buckets.get(s.charAt(0) + "");
                if (!temp.contains(s.charAt(1) + ""))
                    temp.add(s.charAt(1) + "");
            } else {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(s.charAt(1) + "");
                buckets.put(s.charAt(0) + "", temp);
            }
        }
        String ch;
        ch = s.charAt(0) + "";
        if (children.containsKey(ch)) {
            TrieNode temp = children.get(ch);
            if (1 != s.length())
                temp.add(s.substring(1));
            else
                temp.isWord = true;
        } else {
            TrieNode temp = new TrieNode();
            children.put(ch, temp);
            if (1 == s.length())
                temp.isWord = true;
            else
                temp.add(s.substring(1));
        }
    }

    public boolean isWord(String s) {
        String ch;
        if ((s == null || s.equals("")))
            return isWord;
        else {
            ch = s.charAt(0) + "";
            if (children.containsKey(ch)) {
                TrieNode temp = children.get(ch);
                return temp.isWord(s.substring(1));
            }
            return false;
        }

    }

    public String getAnyWordStartingWith(String s) {
        if (isWord)
            return "";
        else {
            if (s == null || s.equals("")) {
                ArrayList<String> child = new ArrayList<>(children.keySet());
                if (child.size() == 0)
                    return null;
                Random random = new Random();
                int r = random.nextInt(child.size());
                return child.get(r) + children.get(child.get(r)).getAnyWordStartingWith(s);
            } else {
                if (children.containsKey(s.charAt(0) + "")) {
                    TrieNode temp = children.get(s.charAt(0) + "");
                    String newWord = temp.getAnyWordStartingWith(s.substring(1));
                    if (newWord != null)
                        return s.charAt(0) + newWord;
                    else
                        return null;
                } else
                    return null;
            }
        }
    }

    public String getGoodWordStartingWith(String s) {
        Random random = new Random();
        boolean bluff = random.nextBoolean();
        if (bluff) {
            if (s == null || s.equals("")) {
                return getAnyWordStartingWith(s);
            } else {
                ArrayList<String> temp = buckets.get(s.charAt(s.length() - 1) + "");
                int r = random.nextInt(temp.size());
                return s + temp.get(r);
            }
        } else {
            return getAnyWordStartingWith(s);
        }
    }
}
