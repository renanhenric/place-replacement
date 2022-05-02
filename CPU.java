import java.io.*;
import java.util.*;

public class CPU
{
  Queue<Integer> inBlocks;
  private int blocks;
  private int[] saved;

  public CPU(int block)
  {
    this.blocks = block;
    this.inBlocks = new LinkedList<>();
    this.saved = new int[block];
  }

  public void setBlocks(int b){ this.blocks = b; }

  public void start(Process process, String algorithm)
  {
    int[] referenceString = process.getReferenceArray();

    int idAlgorithm = 0;

    if(algorithm.equalsIgnoreCase("FIFO"))
        idAlgorithm = 1;
    else if(algorithm.equalsIgnoreCase("Otimo"))
        idAlgorithm = 2;
    else if(algorithm.equalsIgnoreCase("LRU")
            || algorithm.equalsIgnoreCase("LeastRecentlyUsed")
            || algorithm.equalsIgnoreCase("Least recently used"))
        idAlgorithm = 3;

    switch (idAlgorithm)
    {
      case 1: FIFO(referenceString);
              break;
      case 2: Otimo(referenceString);
              break;
      case 3: LeastRecentlyUsed(referenceString);
              break;
      default: System.exit(0);
               break;
    }
  }

  private void FIFO(int[] referenceString)
  {
    System.out.println("\nFIFO");

    int frames = this.blocks;
    int ref_len = referenceString.length;
    int pointer = 0, fault = 0;
    int buffer[] = new int[frames];
    int mem_layout[][] = new int[ref_len][frames];

    for(int j = 0; j < frames; j++)
      buffer[j] = -1;
    
    System.out.println();

    for(int i = 0; i < ref_len; i++)
    {
      int search = -1;
      for(int j = 0; j < frames; j++)
      {
        if(buffer[j] == referenceString[i])
        {
          search = j;
          break;
        } 
      }

      if(search == -1)
      {
        buffer[pointer] = referenceString[i];
        fault++;
        pointer++;
        if(pointer == frames)
        pointer = 0;
      }
      for(int j = 0; j < frames; j++)
        mem_layout[i][j] = buffer[j];
    }
    
    for(int i = 0; i < frames; i++)
    {
      for(int j = 0; j < ref_len; j++)
        System.out.printf("%3d ", mem_layout[j][i]);
      System.out.println();
    }
    
    System.out.println("\nFaltas de página: " + fault);
  }

  private void Otimo(int[] referenceString)
  {
    System.out.println("\nOtimo");
    int frames = this.blocks;
    int ref_len = referenceString.length;
    int pointer = 0, fault = 0;
    boolean isFull = false;
    int buffer[] = new int[frames];
    int mem_layout[][] = new int[ref_len][frames];
    
    for(int j = 0; j < frames; j++)
      buffer[j] = -1;
    
    System.out.println();
    for(int i = 0; i < ref_len; i++)
    {
      int search = -1;
      for(int j = 0; j < frames; j++)
      {
      if(buffer[j] == referenceString[i])
      {
        search = j;
        break;
      } 
      }
      if(search == -1)
      {
      if(isFull)
      {
        int index[] = new int[frames];
        boolean index_flag[] = new boolean[frames];
        for(int j = i + 1; j < ref_len; j++)
        {
        for(int k = 0; k < frames; k++)
        {
          if((referenceString[j] == buffer[k]) && (index_flag[k] == false))
          {
          index[k] = j;
          index_flag[k] = true;
          break;
          }
        }
        }
        int max = index[0];
        pointer = 0;
        if(max == 0)
        max = 200;
        for(int j = 0; j < frames; j++)
        {
        if(index[j] == 0)
          index[j] = 200;
        if(index[j] > max)
        {
          max = index[j];
          pointer = j;
        }
        }
      }
      buffer[pointer] = referenceString[i];
      fault++;
      if(!isFull)
      {
        pointer++;
          if(pointer == frames)
          {
            pointer = 0;
            isFull = true;
          }
      }
      }
        for(int j = 0; j < frames; j++)
            mem_layout[i][j] = buffer[j];
    }
    
    for(int i = 0; i < frames; i++)
    {
        for(int j = 0; j < ref_len; j++)
            System.out.printf("%3d ", mem_layout[j][i]);
        System.out.println();
    }

    System.out.println("\nFaltas de página: " + fault);
  }

  private void LeastRecentlyUsed(int[] referenceString)
  {
    System.out.println("\nLRU");

    int frames = this.blocks;
    int ref_len = referenceString.length;
    int pointer = 0, fault = 0;
    Boolean isFull = false;
    int buffer[] = new int[frames];
    ArrayList<Integer> stack = new ArrayList<Integer>();
    int mem_layout[][] = new int[ref_len][frames];
   
    for(int j = 0; j < frames; j++)
      buffer[j] = -1;
    
    System.out.println();
    for(int i = 0; i < ref_len; i++)
    {
      if(stack.contains(referenceString[i]))
      {
        stack.remove(stack.indexOf(referenceString[i]));
      }
      stack.add(referenceString[i]);
      int search = -1;
      for(int j = 0; j < frames; j++)
      {
        if(buffer[j] == referenceString[i])
        {
          search = j;
          break;
        }
      }
      if(search == -1)
      {
        if(isFull)
        {
          int min_loc = ref_len;
          for(int j = 0; j < frames; j++)
          {
            if(stack.contains(buffer[j]))
            {
                int temp = stack.indexOf(buffer[j]);
                if(temp < min_loc)
                {
                  min_loc = temp;
                  pointer = j;
                }
            }
          }
        }
          buffer[pointer] = referenceString[i];
          fault++;
          pointer++;
          if(pointer == frames)
          {
            pointer = 0;
            isFull = true;
          }
      }
      for(int j = 0; j < frames; j++)
        mem_layout[i][j] = buffer[j];
    }
    
    for(int i = 0; i < frames; i++)
    {
      for(int j = 0; j < ref_len; j++)
        System.out.printf("%3d ", mem_layout[j][i]);
      System.out.println();
    }

    System.out.println("\nFaltas de página: " + fault);
  }
  
}
