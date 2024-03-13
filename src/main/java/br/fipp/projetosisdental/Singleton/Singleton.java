package br.fipp.projetosisdental.Singleton;

import br.fipp.projetosisdental.banco.Entidades.Consulta;
import br.fipp.projetosisdental.banco.Entidades.Dentista;
import br.fipp.projetosisdental.banco.Entidades.Horario;
import br.fipp.projetosisdental.banco.Entidades.Paciente;
import br.fipp.projetosisdental.banco.Entidades.*;

public class Singleton {
    private static Singleton instance;

    private String nomeUsuario;

    private Consulta consulta;

    private Paciente paciente;

    private Dentista dentista;

    private Horario horario;

    private Procedimento procedimento;

    private Material material;

    private boolean modoEdicao = false;
    
    private Singleton() {
        consulta = new Consulta();
        paciente = new Paciente();
        dentista = new Dentista();
        procedimento = new Procedimento();
        material = new Material();
        nomeUsuario = "";
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public boolean getModoEdicao() {
        return modoEdicao;
    }

    public void setModoEdicao(boolean modoEdicao) {
        this.modoEdicao = modoEdicao;
    }

    public static void setInstance(Singleton instance) {
        Singleton.instance = instance;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }


    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
