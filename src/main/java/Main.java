import com.sun.org.apache.bcel.internal.classfile.Code;
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

        try (Connection conn = DriverManager.getConnection(url, userName, password);
             DSLContext context = DSL.using(conn, SQLDialect.MYSQL)) {
            query(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void query(DSLContext context) {
        List<CodeVO> result = context.select().from("chk_code").fetch().into(CodeVO.class);
        for (CodeVO codeVO : result) {
            System.out.println(codeVO.getCode_name());
        }
    }

    static void insert(DSLContext context) {
        context.transaction(p->{
            CodeVO codeVO=new CodeVO();
            codeVO.setCode_name("jooqTest");
            context.query("insert into chk_code(code_name) values(?)",codeVO.getCode_name()).execute();
        });
    }
}
