package com.castiel.model.generator;

import com.castiel.core.base.BaseModel;

@SuppressWarnings("serial")
public class AdministerFrontPage extends BaseModel {
    private String id_;
    private String STU_NAME;
    private String STU_MAJOR;
    private String DEPT_NAME;
    private String TOTAL;
    private String SCORE_VALUE;
    private String TOTAL2;
    private String SCORE_VALUE2;

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getSTU_NAME() {
        return STU_NAME;
    }

    public void setSTU_NAME(String sTU_NAME) {
        STU_NAME = sTU_NAME;
    }

    public String getSTU_MAJOR() {
        return STU_MAJOR;
    }

    public void setSTU_MAJOR(String sTU_MAJOR) {
        STU_MAJOR = sTU_MAJOR;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String dEPT_NAME) {
        DEPT_NAME = dEPT_NAME;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String tOTAL) {
        TOTAL = tOTAL;
    }

    public String getSCORE_VALUE() {
        return SCORE_VALUE;
    }

    public void setSCORE_VALUE(String sCORE_VALUE) {
        SCORE_VALUE = sCORE_VALUE;
    }

    public String getTOTAL2() {
        return TOTAL2;
    }

    public void setTOTAL2(String tOTAL2) {
        TOTAL2 = tOTAL2;
    }

    public String getSCORE_VALUE2() {
        return SCORE_VALUE2;
    }

    public void setSCORE_VALUE2(String sCORE_VALUE2) {
        SCORE_VALUE2 = sCORE_VALUE2;
    }


}
