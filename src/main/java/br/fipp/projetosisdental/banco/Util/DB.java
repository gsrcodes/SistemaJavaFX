package br.fipp.projetosisdental.banco.Util;

public class DB  // classe Singleton
{
    static private Conexao con=new Conexao();
    static public boolean conectar()
    {
        return con.conectar("jdbc:postgresql://localhost:5432/", 
                "sisdentaldb", "postgres", "postgres123");
    }
    static public Conexao getCon()
    {
        return con;
    }

    private DB() {
    }
    
}
