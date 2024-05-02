import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;



public class Main {

    public static boolean solucion(String a, String b){

        if(a.equals(b)){
            return true;
        }

        //si la longitud de cualquiera de los inputs es inpar nunca se puede cumplir que sea verdadero por definicion de la consigna.
        if(a.length() % 2 == 1){
            return false;
        }

        if(b.length() % 2 == 1){
            return false;
        }

        if(a.length() == 1 && b.length() == 1){

            return a.equals(b);
                
        }

       
        return (solucion(a.substring(a.length()/2) , b.substring(0 , b.length()/2)) 
        && solucion(a.substring(0 , a.length()/2), b.substring( b.length()/2)))
         || 
         (solucion(a.substring(a.length()/2) , b.substring( b.length()/2)) 
         && solucion(a.substring(0 , a.length()/2) , b.substring(0 , b.length()/2)));
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       
        String a = reader.readLine();
        String b = reader.readLine();

        
        if(solucion(a,b)){
            System.out.println("YES");
        }
        else{
            System.out.println("NO");
        }


    }

}