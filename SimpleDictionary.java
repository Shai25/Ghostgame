package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        if(word == null)return false;
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

        if (prefix==null)
        {
            Random rn=new Random();

            int index=rn.nextInt(9999)+0;

            return words.get(index);
        }

        if(prefix!=null) {
            int low = 0;
            int high = words.size()-1;
            int mid;
            while(low<=high)
            {
                mid = (low + high) / 2;
                String ms=words.get(mid);
                if(ms.startsWith(prefix))
                {
                    low=high;
                    return words.get(mid);
                }

                if(ms.compareTo(prefix)>0)
                {
                    high=mid-1;

                }
                else
                    low=mid+1;


            }
        }

        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
