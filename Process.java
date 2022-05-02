import java.util.LinkedList;
import java.util.Random;

public class Process
{
  private int id;
  private String reference;

  public Process()
  {
    Random rand = new Random();
    this.id = rand.nextInt(100);
    this.reference = "7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1";
  }

  public Process(int id)
  {
    this.id = id;
    this.reference = null;
  }

  public Process(int id, String reference)
  {
    this.id = id;
    this.reference = reference;
  }

  public void setReferenceString(String refer){ this.reference = refer; }

  public int[] getReferenceArray()
  {
    String[] refer = this.reference.split(",");
    int[] array = new int[refer.length];
    int aux;

    for(int i = 0; i < array.length; i++)
    {
      aux = Integer.parseInt(refer[i]);
      array[i] = aux;
    }

    return array;
  }

}
