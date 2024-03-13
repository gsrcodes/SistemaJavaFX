package br.fipp.projetosisdental.banco.Entidades;

public class Horario {
    private int hora;
    private Paciente paciente;

    public Horario(int hora, Paciente paciente) {
        this.hora = hora;
        this.paciente = paciente;
    }

    public Horario() {
        this(0,null);
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
