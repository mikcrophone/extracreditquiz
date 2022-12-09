public class HashMap {
    private Object keys[];
    private Object values[];
    private int size;
    private int numberofitems = 0;
    

    public HashMap(int size) {
        keys = new Object[size];
        values = new Object[size];
        this.size = size;
    }

    

    private int getPos(Object key) {
        int keyHash = key.hashCode();
        long keyHash2 = 0x00000000ffffffffL & (long)keyHash;
        int pos = (int)(keyHash2 % keys.length);
        // linear probing
        System.out.println("orig:" + pos);
        int origPos = pos;
        if(keys[pos] == null || keys[pos].equals(key)) {
            System.out.println("final:" + pos);

            return pos;
            
        } 
        
        
        else {
            while(true) {
                pos = pos % keys.length;
                if(keys[pos] == null || keys[pos].equals(key)) {
                    System.out.println("final:" + pos);

                    return pos;
                } else if(pos == origPos) {
                    return -1;
                }
            }
            
        }
        
    }
    public Object put(Object key, Object value) {

        if(numberofitems >= (size/2))
        {
            resize(size*2);
        }

        int pos = getPos(key);
        if(pos == -1) return null;
        Object oldValue = null;
        if(keys[pos] != null) {
            oldValue = values[pos];
        }
        keys[pos] = key;
        values[pos] = value;

        numberofitems ++;

        return oldValue;
    }

    private void resize(int cap)
    {
        Object oldkeys [] = keys;
        Object oldvalues [] = values;
        int oldsize = size;
        size = cap;

        keys = new Object[cap];
        values = new Object[cap];

        for(int i = 0; i < oldsize; i++)
        {
            if(oldkeys[i] != null)
            {
                this.put(oldkeys[i],oldvalues[i]);

            }
         }
        
    }

    //Get Or Default.  Function will return entire Hash if it cannot find the key or value given.
    public Object getOrDefault(Object key) {
        int pos = getPos(key);

        if(values[pos] == null) 
        {
            System.out.println("Error! Hash not found. Returning Default Hash");
            return this;
        }
        
        return values[pos];
    }

    // Delete Function
    public void delete(Object key) {
        int pos = getPos(key);
        while(!key.equals(keys[pos]))
            pos = (pos + 1) & size;
        keys[pos] = null;
        values[pos] = null;

        while(keys[pos] != null)
        {
            Object NewKey = keys[pos];
            Object NewValue = values[pos];
            keys[pos] = null;
            values[pos] = null;
            numberofitems--;
            put(NewKey, NewValue);
            pos = (pos + 1) % size;
        }
        numberofitems--;
        if(numberofitems > 0 && numberofitems <= size/8)
        {
            resize(size/2);
        }
        
    }

    //Merging HashMap

    public void MergeHashMap(HashMap MergeHash)
    {
        for(int i = 0; i < MergeHash.size; i++)
        {
            
            while(MergeHash.keys[i] != null)
            {
                Object NewKeys = MergeHash.keys[i];
                Object NewValue = MergeHash.values[i];
                MergeHash.keys[i] = null;
                MergeHash.values[i] = null;
                MergeHash.numberofitems--;
                this.put(NewKeys, NewValue);
                this.numberofitems ++;

            }
        }
        this.numberofitems ++;
        if (this.numberofitems > 0 && this.numberofitems <= this.size/8)
        {
            this.resize(this.size/2);
        }
        
    }
        

        
    


    public static void main(String[] args)
    {
        HashMap hashmap = new HashMap(5);  //Creating HashMap
        HashMap hashmap2 = new HashMap(3); //Creating 2nd HashMap to test merging.

        //First HashMap
    
        hashmap.put("5", 5);
        hashmap.put("hi", "hello");
        hashmap.put("10", 10);
        hashmap.put("EC", "I want extra credit");
        hashmap.put("68", "my grade in class");

        //Second HashMap

        hashmap2.put("merge", "testing merging hashmap");
        hashmap2.put("100", "A1");
        hashmap2.put("work", "did it work");

        //GetOrDefault Method. I wasn't sure what default value it should return other than the entire hashmap.  I modified the original Get function
        
        System.out.print("Testing GetOrDefault Method... \n");
        System.out.println(hashmap.getOrDefault("10"));  // Returns 10
        System.out.println(hashmap.getOrDefault("hi")); //  Returns hello
        System.out.println(hashmap.getOrDefault("lol")); // returns hash
        System.out.println(hashmap.getOrDefault("michael")); // returns hash
        //System.out.println(hashmap2.getOrDefault("work")); why does this one not work?
        System.out.println(hashmap2.getOrDefault("100"));


        //Delete Method.  Commented out code to not interfere with merge method.  Uncomment to use.

        //System.out.println("Testing Delete Method... \n");
        //System.out.println(hashmap.getOrDefault("hi"));
        //hashmap.delete("hi");
        //System.out.println(hashmap.getOrDefault("hi"));

        //Merge HashMap Method

        System.out.println("Testing MergeHashMap function... \n");
        hashmap.MergeHashMap(hashmap2);
        System.out.println(hashmap.getOrDefault("100"));

        




        


        
        
    }

    
}


