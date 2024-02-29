package business.oficina;


public class Pedido{


    public enum Estado {PorRealizar, ARealizar, Concluido, Incompleto};
    private int idPedido;
    private Estado estado;
    private  Tempo tempo;
    private String cliente;
    private int posto;
    private String veiculo;
    private String servico;


    public Pedido(Tempo tempo, String cliente, int posto, String veiculo, String servico){
        this.idPedido = -1;
        this.estado = Estado.PorRealizar;
        this.tempo = tempo.clone();
        this.cliente = cliente;
        this.posto = posto;
        this.veiculo = veiculo;
        this.servico = servico;
    }
    public Pedido(int idPedido, Estado estado, Tempo tempo, String cliente, int posto, String veiculo, String servico) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.tempo = tempo.clone();
        this.cliente = cliente;
        this.posto = posto;
        this.veiculo = veiculo;
        this.servico = servico;
    }


    public int getIdPedido(){
        return this.idPedido;
    }
    public Estado getEstado(){
        return this.estado;
    }
    public Tempo getTempo() {
        return this.tempo;
    }
    public String getCliente(){
        return this.cliente;
    }
    public int getPosto(){
        return this.posto;
    }
    public String getVeiculo(){
        return this.veiculo;
    }
    public String getServico(){
        return this.servico;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Pedido that = (Pedido) obj;
        return this.idPedido == that.getIdPedido();
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("ID: ").append(this.idPedido);
        buffer.append("\nVeículo: ").append(this.veiculo);
        buffer.append("\nServico: ").append(this.servico);
        buffer.append(this.tempo.toString());
        return buffer.toString();
    }
}