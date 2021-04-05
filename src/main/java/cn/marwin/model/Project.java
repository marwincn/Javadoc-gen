package cn.marwin.model;

import lombok.Data;

import java.util.List;

@Data
public class Project {
    private String projectName;
    private String projectUrl;
    private List<Module> modules;
}
