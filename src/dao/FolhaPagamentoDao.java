package dao;

public class FolhaPagamentoDao {
    private Connection connection;

    public FolhaPagamentoDao() {
        this.connection = new ConnectionFactory().getConnection();
    }


}