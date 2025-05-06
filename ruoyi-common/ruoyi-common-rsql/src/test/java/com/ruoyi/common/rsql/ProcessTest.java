package com.ruoyi.common.rsql;

import org.junit.jupiter.api.Test;
import com.ruoyi.common.rsql.sql.SQLProcess;

public class ProcessTest
{
    @Test
    public void processRSQL() throws Exception {
        SQLProcess proc = new SQLProcess("test");
        Process.parse("name=='lxd' and name!='lxd'", proc);
        String sql = proc.getSQL();
        System.out.println(sql);
    }
}
