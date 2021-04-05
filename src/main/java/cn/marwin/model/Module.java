package cn.marwin.model;

import lombok.Data;

import java.util.List;

@Data
public class Module {
    private String modulePath;
    private List<String> packages;
}
