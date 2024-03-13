package br.fipp.projetosisdental.banco.DALS;

import br.fipp.projetosisdental.banco.Entidades.*;
import br.fipp.projetosisdental.banco.Util.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAL implements IDAL<Pessoa>{
    @Override
    public boolean gravar(Pessoa entidade) {
        String sql="insert into ";
        if(entidade instanceof Dentista)
        {
            sql+="dentista values (default,'#1',#2,'#3','#4')";
            sql=sql.replace("#1",entidade.getNome());
            sql=sql.replace("#2",""+((Dentista) entidade).getCro());
            sql=sql.replace("#3",((Dentista) entidade).getFone());
            sql=sql.replace("#4",((Dentista) entidade).getEmail());
        }
        else if(entidade instanceof Usuario){
            sql+="usuario values (default,'#1',#2,'#3')";
            sql=sql.replace("#1",entidade.getNome());
            sql=sql.replace("#2",""+((Usuario) entidade).getNivel());
            sql=sql.replace("#3",((Usuario) entidade).getSenha());
        }
        else {
            sql+="paciente values (default,'#1','#2','#3','#4','#5','#6','#7','#8','#9','#A','#B')";
            sql=sql.replace("#1", ((Paciente) entidade).getCpf());
            sql=sql.replace("#2",entidade.getNome());
            sql=sql.replace("#3", ((Paciente) entidade).getCep());
            sql=sql.replace("#4", ((Paciente) entidade).getRua());
            sql=sql.replace("#5", ((Paciente) entidade).getNumero());
            sql=sql.replace("#6", ((Paciente) entidade).getBairro());
            sql=sql.replace("#7", ((Paciente) entidade).getCidade());
            sql=sql.replace("#8", ((Paciente) entidade).getUf());
            sql=sql.replace("#9", ((Paciente) entidade).getFone());
            sql=sql.replace("#A", ((Paciente) entidade).getEmail());
            sql=sql.replace("#B", ((Paciente) entidade).getHisto());
        }
        System.out.println(sql);
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean alterar(Pessoa entidade) {
        String sql="update ";
        if(entidade instanceof Dentista)
        {
            sql+="dentista set den_nome='#1',den_cro=#2,den_fone='#3',den_email='#4' where den_id="+ entidade.getId();
            sql=sql.replace("#1",entidade.getNome());
            sql=sql.replace("#2",""+((Dentista) entidade).getCro());
            sql=sql.replace("#3",((Dentista) entidade).getFone());
            sql=sql.replace("#4",((Dentista) entidade).getEmail());
        }
        else if(entidade instanceof Usuario){
            sql+="usuario set uso_nome='#1',uso_nivel=#2,uso_senha='#3' where uso_id="+entidade.getId();;
            sql=sql.replace("#1",entidade.getNome());
            sql=sql.replace("#2",""+((Usuario) entidade).getNivel());
            sql=sql.replace("#3",((Usuario) entidade).getSenha());
        }
        else {
            sql+="paciente set pac_cpf='#1',pac_nome='#2',pac_cep='#3',pac_rua='#4',pac_numero='#5',pac_bairro='#6',pac_cidade='#7',pac_uf='#8',pac_fone='#9',pac_email='#A',pac_histo='#B' where pac_id="+entidade.getId();
            sql=sql.replace("#1", ((Paciente) entidade).getCpf());
            sql=sql.replace("#2",entidade.getNome());
            sql=sql.replace("#3", ((Paciente) entidade).getCep());
            sql=sql.replace("#4", ((Paciente) entidade).getRua());
            sql=sql.replace("#5", ((Paciente) entidade).getNumero());
            sql=sql.replace("#6", ((Paciente) entidade).getBairro());
            sql=sql.replace("#7", ((Paciente) entidade).getCidade());
            sql=sql.replace("#8", ((Paciente) entidade).getUf());
            sql=sql.replace("#9", ((Paciente) entidade).getFone());
            sql=sql.replace("#A", ((Paciente) entidade).getEmail());
            sql=sql.replace("#B", ((Paciente) entidade).getHisto());
        }
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean apagar(Pessoa entidade) {
        String sql="delete from #1 where #2 = "+entidade.getId();
        String dados[]=new String[2];
        if(entidade instanceof Dentista)
        {dados[0]="dentista"; dados[1]="den_id";}
        else if (entidade instanceof Usuario)
        {dados[0]="usuario"; dados[1]="uso_id";}
        else{dados[0]="paciente"; dados[1]="pac_id";}
        sql=sql.replace("#1",dados[0]);
        sql=sql.replace("#2",dados[1]);
        return DB.getCon().manipular(sql);
    }

    @Override
    public Pessoa get(int id) {
        return null;
    }

    @Override
    public List<Pessoa> get(String filtro) {
        return null;
    }

    public Pessoa get(int id, Pessoa entidade) {
        Pessoa pessoa=null;
        String sql="select * from ";
        try {
            if (entidade instanceof Dentista) {
                sql += "dentista where den_id=" + id;
                ResultSet rs = DB.getCon().consultar(sql);
                if(rs.next())
                    pessoa=new Dentista(rs.getInt("den_id"),rs.getString("den_nome"),rs.getInt("den_cro"),
                            rs.getString("den_fone"),rs.getString("den_email"));
            } else if (entidade instanceof Usuario) {
                sql += "usuario where uso_id=" + id;
                ResultSet rs = DB.getCon().consultar(sql);
                if(rs.next())
                    pessoa=new Usuario(rs.getInt("uso_id"),rs.getString("uso_nome"),rs.getInt("uso_nivel"),
                            rs.getString("uso_senha"));
            } else {
                sql += "paciente where pac_id=" + id;
                ResultSet rs = DB.getCon().consultar(sql);
                if(rs.next())
                    pessoa=new Paciente(rs.getInt("pac_id"),rs.getString("pac_nome"),rs.getString("pac_cpf"),
                            rs.getString("pac_cep"),rs.getString("pac_rua"),rs.getString("pac_numero"),
                            rs.getString("pac_bairro"),rs.getString("pac_cidade"),rs.getString("pac_uf"),
                            rs.getString("pac_fone"),rs.getString("pac_email"),rs.getString("pac_histo"));
            }
        }
        catch (Exception e){  };
        return pessoa;
    }

    public List<Pessoa> get(String filtro, Pessoa entidade) {
        List <Pessoa> pessoas=new ArrayList<>();
        String sql="select * from ";
        try {
            if (entidade instanceof Dentista) {
                sql += "dentista";
                if(!filtro.isEmpty())
                    sql+=" where "+filtro;
                ResultSet rs = DB.getCon().consultar(sql);
                while(rs.next())
                    pessoas.add(new Dentista(rs.getInt("den_id"),rs.getString("den_nome"),rs.getInt("den_cro"),
                            rs.getString("den_fone"),rs.getString("den_email")));
            } else if (entidade instanceof Usuario) {
                sql += "usuario";
                if(!filtro.isEmpty())
                    sql+=" where "+filtro;
                ResultSet rs = DB.getCon().consultar(sql);
                while(rs.next())
                    pessoas.add(new Usuario(rs.getInt("uso_id"),rs.getString("uso_nome"),rs.getInt("uso_nivel"),
                            rs.getString("uso_senha")));
            } else {
                sql += "paciente";
                if(!filtro.isEmpty())
                    sql+=" where "+filtro;
                ResultSet rs = DB.getCon().consultar(sql);
                while(rs.next())
                    pessoas.add(new Paciente(rs.getInt("pac_id"),rs.getString("pac_nome"),rs.getString("pac_cpf"),
                            rs.getString("pac_cep"),rs.getString("pac_rua"),rs.getString("pac_numero"),
                            rs.getString("pac_bairro"),rs.getString("pac_cidade"),rs.getString("pac_uf"),
                            rs.getString("pac_fone"),rs.getString("pac_email"),rs.getString("pac_histo")));
            }
        }
        catch (Exception e){  };
        return pessoas;
    }
}
