package com.castiel.model.sys;

import com.castiel.model.generator.PActRound;

@SuppressWarnings("serial")
public class PActRoundBean extends PActRound {
    private String stuName;
    private String affirmName;
    private Integer scoreApplySetValue1;
    private Integer scoreApplySetValue2;
    private Integer scoreApplySetValue3;
    private Integer scoreApplySetValue4;

    public String getStuName() {
        return this.stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getAffirmName() {
        return this.affirmName;
    }

    public void setAffirmName(String affirmName) {
        this.affirmName = affirmName;
    }

    public Integer getScoreApplySetValue1() {
        return this.scoreApplySetValue1;
    }

    public void setScoreApplySetValue1(Integer scoreApplySetValue1) {
        this.scoreApplySetValue1 = scoreApplySetValue1;
    }

    public Integer getScoreApplySetValue3() {
        return scoreApplySetValue3;
    }

    public void setScoreApplySetValue3(Integer scoreApplySetValue3) {
        this.scoreApplySetValue3 = scoreApplySetValue3;
    }

    public Integer getScoreApplySetValue2() {
        return scoreApplySetValue2;
    }

    public void setScoreApplySetValue2(Integer scoreApplySetValue2) {
        this.scoreApplySetValue2 = scoreApplySetValue2;
    }

    public Integer getScoreApplySetValue4() {
        return scoreApplySetValue4;
    }

    public void setScoreApplySetValue4(Integer scoreApplySetValue4) {
        this.scoreApplySetValue4 = scoreApplySetValue4;
    }



}
