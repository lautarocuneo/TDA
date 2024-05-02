import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            int cantVendedores = -1;
            
            while(cantVendedores != 0){

            cantVendedores = scanner.nextInt();

            if(cantVendedores == 0){break;}
            
            long acumulacionOfertaDemanda = 0;

            long trabajo = 0;


            for(int i = 0; i < cantVendedores ; i++){
                acumulacionOfertaDemanda += scanner.nextInt();
                trabajo += Math.abs(acumulacionOfertaDemanda);

            }
        

            System.out.println(trabajo);

        }

    }


}
