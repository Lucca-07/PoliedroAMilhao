
package br.mycompany.poliedroamilhao.persistencia;

import br.mycompany.poliedroamilhao.modelo.Materia;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
 public List<Materia> listarMaterias() throws Exception {
    List<Materia> lista = new ArrayList<>();

    String sql = "SELECT Id_Materia, Nome_Materia FROM Materias";  

    var fabricaDeConexoes = new ConnectionFactory();

    try (
        var conexao = fabricaDeConexoes.obterConexao();
        var ps = conexao.prepareStatement(sql);
        var rs = ps.executeQuery();
    ) {
        while (rs.next()) {
            Materia m = new Materia();
            m.setId(rs.getInt("Id_Materia"));
            m.setNome(rs.getString("Nome_Materia"));
            lista.add(m);
            System.out.println("Materia carregada: " + m.getNome());
        }
    }

    return lista;
}
}
