package business;


public class Notificacao {

    private int idNot;
    private boolean lida;
    private String mensagem;
    private String cliente;


    public Notificacao(String mensagem, String cliente) {
        this.idNot = -1;
        this.lida = false;
        this.cliente = cliente;
        this.mensagem = mensagem;
    }


    public Notificacao(int idNot, boolean enviada, String message, String cliente) {
        this.idNot = idNot;
        this.lida = enviada;
        this.mensagem = message;
        this.cliente = cliente;
    }


    public int getIdNot() {
        return this.idNot;
    }


    public boolean getLida() {
        return this.lida;
    }


    public String getCliente() {
        return this.cliente;
    }


    public String getMensagem() {
        return this.mensagem;
    }


    public void setLida() {
        this.lida = true;
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Mensagem: ").append(this.mensagem);
        return buffer.toString();
    }
}