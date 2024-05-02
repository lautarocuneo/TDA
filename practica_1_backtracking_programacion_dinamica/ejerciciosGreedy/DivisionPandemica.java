package ejerciciosGreedy;

import java.util.*;

public class DivisionPandemica {
    // Representa un estudiante
    static class Estudiante {
        int id;
        Set<Estudiante> cercanos = new HashSet<>();
        
        // Constructor
        public Estudiante(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        // Supongamos que tenemos una lista de estudiantes
        List<Estudiante> estudiantes = new ArrayList<>();
        
        // Inicializamos los subcursos
        List<Estudiante> A = new ArrayList<>();
        List<Estudiante> B = new ArrayList<>();
        
        for (Estudiante e : estudiantes) {
            // Asignamos cada estudiante al subcurso que tenga menos estudiantes cercanos a él
            if (contarCercanos(e, A) <= contarCercanos(e, B)) {
                A.add(e);
            } else {
                B.add(e);
            }
        }
        
        // Ahora A y B son los subcursos resultantes
    }
    
    // Cuenta cuántos estudiantes cercanos a e están en el subcurso
    static int contarCercanos(Estudiante e, List<Estudiante> subcurso) {
        int count = 0;
        for (Estudiante s : subcurso) {
            if (e.cercanos.contains(s)) {
                count++;
            }
        }
        return count;
    }
}

