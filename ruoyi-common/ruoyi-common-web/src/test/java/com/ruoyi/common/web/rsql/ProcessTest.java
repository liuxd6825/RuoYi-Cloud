package com.ruoyi.common.web.rsql;

import org.junit.jupiter.api.Test;
import com.ruoyi.common.web.rsql.SQLProcess;

public class ProcessTest
{
    @Test
    public void processRSQL() throws Exception {
        String sql = SQLProcess.Parse("test","name=='lxd' and name!='lxd'");
        System.out.println(sql);
    }
}
