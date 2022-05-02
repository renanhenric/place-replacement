import java.util.*;

class Main 
{
  public static void main(String[] args) throws Exception
  {
    Scanner input = new Scanner(System.in);
    int pId = 2323, blocks, option;
    String reference, repeat;

    Process p1;
    CPU control;

    while(true)
    {
      System.out.println("\n\n-------------------------------------------");
      System.out.println("\t-----Substituição de páginas-----");
      
      System.out.print("\nEscolha o Algoritmo de Substituição de Páginas: \n" +
                         " 1 - FIFO \n 2 - LRU \n 3 - Otimo \n Opção: ");
      option = input.nextInt();

      System.out.print("\nInsira o tamanho da RAM (em quadros): ");
      blocks = input.nextInt();

      input.nextLine();

      System.out.println("\nInsira a cadeia de referencia com os números separados por vírgula: ");
      reference = input.nextLine();

      try
      {
        p1 = new Process(pId, reference);
        control = new CPU(blocks);
        control.start(p1, algorithmOption(option));
      }
      catch(Exception e)
      {
        throw new Exception(e);
      }

      System.out.print("\n\nDeseja simular novamente ? (y/n): ");
      repeat = input.nextLine();

      if(repeat.equals("n")) break;
    }
  }

  public static String algorithmOption(int option)
  {
    String algorithm = " ";

    switch(option)
    {
      case 1:
        algorithm = "FIFO";
      break;
      
      case 2:
        algorithm = "LRU";
      break;

      case 3:
        algorithm = "Otimo";
      break;

      default: 
        System.exit(0);
      break;
    }
    return algorithm;
  }

}
