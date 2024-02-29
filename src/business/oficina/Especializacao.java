package business.oficina;


public class Especializacao {
    private String Tipoesp;


    public Especializacao(String Tipoesp) {
        this.Tipoesp = Tipoesp;
    }


    public String getTipoesp() {
        return this.Tipoesp;
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Tipoesp: ").append(this.Tipoesp);
        return buffer.toString();
    }
}
