package cgg.com.threeapp.model.bean;

/**
 * author: Wanderer
 * date:   2018/2/28 13:59
 * email:  none
 */

public class SettingBean {
    private String functionName;
    private String functionIntro;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionIntro() {
        return functionIntro;
    }

    public void setFunctionIntro(String functionIntro) {
        this.functionIntro = functionIntro;
    }

    public SettingBean(String functionName, String functionIntro) {
        this.functionName = functionName;
        this.functionIntro = functionIntro;
    }
}
