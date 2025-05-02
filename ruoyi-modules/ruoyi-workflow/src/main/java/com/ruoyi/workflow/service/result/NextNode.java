package com.ruoyi.workflow.service.result;

import java.util.List;

public class NextNode {
    private String id;
    private String name;
    private String type;
    public List<NextNode> outNodes;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NextNode> getOutNodes() {
        return outNodes;
    }

    public void setOutNodes(List<NextNode> outNodes) {
        this.outNodes = outNodes;
    }
}
