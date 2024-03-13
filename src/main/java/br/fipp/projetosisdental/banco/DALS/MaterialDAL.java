package br.fipp.projetosisdental.banco.DALS;

import br.fipp.projetosisdental.banco.Entidades.Material;
import br.fipp.projetosisdental.banco.Util.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAL implements IDAL<Material>{
    @Override
    public boolean gravar(Material entidade) {
        String sql="insert into material values (default,'#1',#2)";
        sql=sql.replace("#1",entidade.getDescricao());
        sql=sql.replace("#2",""+entidade.getPreco());
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean alterar(Material entidade) {
        String sql="update material set mat_desc='#1', mat_preco=#2 where mat_id="+entidade.getId();
        sql=sql.replace("#1",entidade.getDescricao());
        sql=sql.replace("#2",""+entidade.getPreco());
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean apagar(Material entidade) {
        return DB.getCon().manipular("delete from material where mat_id="+entidade.getId());
    }

    @Override
    public Material get(int id) {
        Material material=null;
        ResultSet rs=DB.getCon().consultar("select * from material where mat_id="+id);
        try {
            if (rs.next())
                material = new Material(rs.getInt("mat_id"), rs.getString("mat_desc"), rs.getDouble("mat_preco"));
        }
        catch (Exception e){  }
        return material;
    }

    @Override
    public List<Material> get(String filtro) {
        List <Material> materiais=new ArrayList<>();
        String sql="select * from material";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs=DB.getCon().consultar(sql);
        try {
            while (rs.next())
                materiais.add(new Material(rs.getInt("mat_id"), rs.getString("mat_desc"), rs.getDouble("mat_preco")));
        }
        catch (Exception e){  }
        return materiais;
    }
}
