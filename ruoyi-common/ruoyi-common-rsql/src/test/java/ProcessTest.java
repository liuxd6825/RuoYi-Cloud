import org.junit.jupiter.api.Test;
import com.ruoyi.common.rsql.Process;
import com.ruoyi.common.rsql.sql.SQLProcess;

public class ProcessTest
{
    @Test
    public void processRSQL() throws Exception {
        SQLProcess sqlPro = new SQLProcess("test");
        Process.parse("name=='lxd'", sqlPro);
        String sql = sqlPro.getSQL();
        System.out.println(sql);
    }
}
