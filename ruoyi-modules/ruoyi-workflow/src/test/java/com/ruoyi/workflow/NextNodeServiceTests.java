package com.ruoyi.workflow;

import com.ruoyi.workflow.service.impl.NextNodeService;
import com.ruoyi.workflow.service.result.NextNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NextNodeServiceTests {

    @Autowired
    NextNodeService nextNodeService;
    @Test
    void nextNode() {
        List<NextNode> nodes = nextNodeService.getNextNodes("306");
        for (NextNode node : nodes) {
            System.out.println(node.getName());
        }
    }

}
