public class Tripla<A, B, C> {
    private final A primero;
    private final B segundo;
    private final C tercero;

    public Tripla(A primero, B segundo, C tercero) {
        this.primero = primero;
        this.segundo = segundo;
        this.tercero = tercero;
    }

    public A getPrimero() {
        return primero;
    }

    public B getSegundo() {
        return segundo;
    }

    public C getTercero() {
        return tercero;
    }
}