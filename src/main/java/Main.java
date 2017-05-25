import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String userName = "root";
        String password = "ct123!@#";
        String url = "jdbc:mysql://localhost:3306/codesafe";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             DSLContext create = DSL.using(conn, SQLDialect.MYSQL)) {
            create.transaction(configuration -> {
                List<CodeVO> result = create.select().from("chk_code").fetch().map(record -> record.into(CodeVO.class));
                for (CodeVO codeVO : result) {
                    System.out.println(codeVO.getCode_name());
                }
            });
        }
        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
