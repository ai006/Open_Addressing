import java.util.HashSet;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;

public class Hash
{
    int LinearCollisions;
    int QuadraticCollisions;
    int DoubleCollisions;
    int size;
    
    Hash()
    {
        LinearCollisions = 0;
        QuadraticCollisions = 0;
        DoubleCollisions = 0;
        size = 1009;
    }

    public void resetCollisions()
    {
        LinearCollisions = 0;
        QuadraticCollisions = 0;
        DoubleCollisions = 0;
    }

    int LinearProbing(int key,int i)
    {
        return (key + i) % size;
    }

    int QuadraticProbing(int key, int i)
    {
        int C1 = 1;
        int C2 = 3;
        return (key + (C1 *(i)) + (C2*(i)*(i))) % size;
    }

    int DoubleHashing(int key,int i)
    {
        int h1 = key;
        int h2 = 1 + (key % (size-1));

        return (h1 + i*(h2))% size;


    }

	//how HashInsert works
	//int hash : specifies which hash function method to use among linear probing, quadratic probing, and double hashing
    //int boolean passed : is used to check whether it is the first or second set of data
	//int key: is the int value that going to be inserted in the data structure
	//int [] arr: is the data structure
	
	int HashInsert(int []arr, int key, int hash, Boolean passed)
    {
        int i = 0;
        while(i < arr.length)
        {
           if(hash == 1) //double hashing
           {
                int  j = DoubleHashing(key, i);
                if(arr[j] == -1)
                {
                    arr[j] = key;
                    return j;
                }
                i++;
                if(passed) //check if its 1st set or 2nd of data
                    DoubleCollisions++;
           } 
           else if(hash == 2) //linear probling
           {
                int  j = LinearProbing(key, i);
                if(arr[j] == -1)
                {
                    arr[j] = key;
                    return j;
                }
                i++;
                if(passed) //check if its 1st set or 2nd of data
                    LinearCollisions++;
            } 
            else if (hash == 3)  //quadratic probing
            {
                int  j = QuadraticProbing(key, i);
                if(arr[j] == -1)
                {
                    arr[j] = key;
                    return j;
                }
                i++;
                if(passed) //check if its 1st set or 2nd of data
                    QuadraticCollisions++;
            }
        }
        return -1;
    }

    public static void main(String [] args)
    {
        Random random = new Random();
        Hash hash = new Hash();

        int []arrLinear = new int[hash.size];
        int []arrDouble = new int[hash.size];
        int []arrQuadratic = new int[hash.size];

        int []firstData = new int[900];
        int []secondData = new int[50];
        HashSet<Integer> checker  = new HashSet<Integer>();

        for(int i = 0; i < hash.size;i++)
        {
            arrLinear[i] = -1;
            arrDouble[i] = -1;
            arrQuadratic[i] = -1;
        }
            
        //Double Hashing
        for(int j = 0; j < 900;j++)
        {
            int randomValue = random.nextInt(100000);
            firstData[j] = randomValue; 
            checker.add(randomValue); //will be used when adding 50 distinct random values  
            if(hash.HashInsert(arrDouble, randomValue,1,false) == -1 ) //1 is double hashing
            {
                System.out.println("overflow in Double Hashing");
                break;
            }          
        }

        //Double hashing
        for(int j = 0; j < 50; j++)
        {
            Boolean doesContain = false;

            int randomValue = random.nextInt(100000) + 100000;
            while(!doesContain)
            {
                if(checker.contains(randomValue))
                {
                    randomValue = random.nextInt(100000) + 100000;
                }
                else
                {
                    doesContain = true;
                }
                    
            }



            secondData[j] = randomValue;
            if(hash.HashInsert(arrDouble, randomValue,1,true) == -1 )
            {
                System.out.println("overflow in Double Hashing");
                break;
            }  
        }    
        


        //Linear probing
        for(int x = 0; x < firstData.length; x++)
        {
            if(hash.HashInsert(arrLinear, firstData[x],2,false) == -1 ) //2 is linear probing
            {
                System.out.println("overflow in Linear probing");
                break;
            }   
        }

        //linear pobing
        for(int x = 0; x < secondData.length;x++)
        {
            if(hash.HashInsert(arrLinear,secondData[x],2,true) == -1 ) //2 is linear probing
            {
                System.out.println("overflow in Linear probing");
                break;
            }  
        }
       

        //Quadratic
        for(int x = 0; x < firstData.length; x++)
        {
            if(hash.HashInsert(arrQuadratic, firstData[x],3,false) == -1 ) //2 is linear probing
            {
                System.out.println("overflow in Quadratic probing");
                break;
            }   
        }

        for(int x = 0; x < secondData.length;x++)
        {
            if(hash.HashInsert(arrQuadratic, secondData[x],3,true) == -1 ) //2 is linear probing
            {
                System.out.println("overflow in Quadratic probing");
                break;
            }  
        }
        System.out.println("Number of collisions Linear Probing "+ hash.LinearCollisions);
        System.out.println("Number of collisions Quadratic Probing "+ hash.QuadraticCollisions);
        System.out.println("Number of collisions Double Hashing "+ hash.DoubleCollisions);
        hash.resetCollisions();
    }
        
    
}