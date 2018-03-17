package com.castiel.model.generator;

import java.util.Date;

import com.castiel.core.base.BaseModel;

@SuppressWarnings("serial")
public class PActRound extends BaseModel {

    private String stuId;
    private String deptId;
    private Date scoreCycleStartTime;
    private Date scoreCycleEndTime;
    private Integer total;
    private Integer total2;
    private Integer isaffirm;
    private Integer isaffirm2;
    private Integer affirmScore;
    private Integer affirmScore2;
    private Date affirmTime;
    private Date affirmTime2;
    private Integer reviewStatus;
    private Integer reviewStatus2;
    private String reviewReason;
    private String reviewReason2;
    private Date reviewTime;
    private Date reviewTime2;
    private String scoreValue;
    private String scoreValue2;
    private String reviewName;
    private String reviewName2;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Date getScoreCycleStartTime() {
        return scoreCycleStartTime;
    }

    public void setScoreCycleStartTime(Date scoreCycleStartTime) {
        this.scoreCycleStartTime = scoreCycleStartTime;
    }

    public Date getScoreCycleEndTime() {
        return scoreCycleEndTime;
    }

    public void setScoreCycleEndTime(Date scoreCycleEndTime) {
        this.scoreCycleEndTime = scoreCycleEndTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal2() {
        return total2;
    }

    public void setTotal2(Integer total2) {
        this.total2 = total2;
    }

    public Integer getIsaffirm() {
        return isaffirm;
    }

    public void setIsaffirm(Integer isaffirm) {
        this.isaffirm = isaffirm;
    }

    public Integer getIsaffirm2() {
        return isaffirm2;
    }

    public void setIsaffirm2(Integer isaffirm2) {
        this.isaffirm2 = isaffirm2;
    }

    public Integer getAffirmScore() {
        return affirmScore;
    }

    public void setAffirmScore(Integer affirmScore) {
        this.affirmScore = affirmScore;
    }

    public Integer getAffirmScore2() {
        return affirmScore2;
    }

    public void setAffirmScore2(Integer affirmScore2) {
        this.affirmScore2 = affirmScore2;
    }

    public Date getAffirmTime() {
        return affirmTime;
    }

    public void setAffirmTime(Date affirmTime) {
        this.affirmTime = affirmTime;
    }

    public Date getAffirmTime2() {
        return affirmTime2;
    }

    public void setAffirmTime2(Date affirmTime2) {
        this.affirmTime2 = affirmTime2;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getReviewStatus2() {
        return reviewStatus2;
    }

    public void setReviewStatus2(Integer reviewStatus2) {
        this.reviewStatus2 = reviewStatus2;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }

    public String getReviewReason2() {
        return reviewReason2;
    }

    public void setReviewReason2(String reviewReason2) {
        this.reviewReason2 = reviewReason2;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime2() {
        return reviewTime2;
    }

    public void setReviewTime2(Date reviewTime2) {
        this.reviewTime2 = reviewTime2;
    }

    public String getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(String scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getScoreValue2() {
        return scoreValue2;
    }

    public void setScoreValue2(String scoreValue2) {
        this.scoreValue2 = scoreValue2;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewName2() {
        return reviewName2;
    }

    public void setReviewName2(String reviewName2) {
        this.reviewName2 = reviewName2;
    }

    @Override
    public String toString() {
        return "PActRound [stuId=" + stuId + ", deptId=" + deptId + ", scoreCycleStartTime=" + scoreCycleStartTime + ", scoreCycleEndTime=" + scoreCycleEndTime + ", total=" + total + ", total2=" + total2 + ", isaffirm=" + isaffirm + ", isaffirm2=" + isaffirm2 + ", affirmScore=" + affirmScore + ", affirmScore2=" + affirmScore2 + ", affirmTime=" + affirmTime + ", affirmTime2=" + affirmTime2 + ", reviewStatus=" + reviewStatus + ", reviewStatus2=" + reviewStatus2 + ", reviewReason=" + reviewReason + ", reviewReason2=" + reviewReason2 + ", reviewTime=" + reviewTime + ", reviewTime2=" + reviewTime2 + ", scoreValue=" + scoreValue + ", scoreValue2=" + scoreValue2 + ", reviewName=" + reviewName + ", reviewName2=" + reviewName2 + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((affirmScore == null) ? 0 : affirmScore.hashCode());
        result = prime * result + ((affirmScore2 == null) ? 0 : affirmScore2.hashCode());
        result = prime * result + ((affirmTime == null) ? 0 : affirmTime.hashCode());
        result = prime * result + ((affirmTime2 == null) ? 0 : affirmTime2.hashCode());
        result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
        result = prime * result + ((isaffirm == null) ? 0 : isaffirm.hashCode());
        result = prime * result + ((isaffirm2 == null) ? 0 : isaffirm2.hashCode());
        result = prime * result + ((reviewName == null) ? 0 : reviewName.hashCode());
        result = prime * result + ((reviewName2 == null) ? 0 : reviewName2.hashCode());
        result = prime * result + ((reviewReason == null) ? 0 : reviewReason.hashCode());
        result = prime * result + ((reviewReason2 == null) ? 0 : reviewReason2.hashCode());
        result = prime * result + ((reviewStatus == null) ? 0 : reviewStatus.hashCode());
        result = prime * result + ((reviewStatus2 == null) ? 0 : reviewStatus2.hashCode());
        result = prime * result + ((reviewTime == null) ? 0 : reviewTime.hashCode());
        result = prime * result + ((reviewTime2 == null) ? 0 : reviewTime2.hashCode());
        result = prime * result + ((scoreCycleEndTime == null) ? 0 : scoreCycleEndTime.hashCode());
        result = prime * result + ((scoreCycleStartTime == null) ? 0 : scoreCycleStartTime.hashCode());
        result = prime * result + ((scoreValue == null) ? 0 : scoreValue.hashCode());
        result = prime * result + ((scoreValue2 == null) ? 0 : scoreValue2.hashCode());
        result = prime * result + ((stuId == null) ? 0 : stuId.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
        result = prime * result + ((total2 == null) ? 0 : total2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PActRound other = (PActRound) obj;
        if (affirmScore == null) {
            if (other.affirmScore != null)
                return false;
        } else if (!affirmScore.equals(other.affirmScore))
            return false;
        if (affirmScore2 == null) {
            if (other.affirmScore2 != null)
                return false;
        } else if (!affirmScore2.equals(other.affirmScore2))
            return false;
        if (affirmTime == null) {
            if (other.affirmTime != null)
                return false;
        } else if (!affirmTime.equals(other.affirmTime))
            return false;
        if (affirmTime2 == null) {
            if (other.affirmTime2 != null)
                return false;
        } else if (!affirmTime2.equals(other.affirmTime2))
            return false;
        if (deptId == null) {
            if (other.deptId != null)
                return false;
        } else if (!deptId.equals(other.deptId))
            return false;
        if (isaffirm == null) {
            if (other.isaffirm != null)
                return false;
        } else if (!isaffirm.equals(other.isaffirm))
            return false;
        if (isaffirm2 == null) {
            if (other.isaffirm2 != null)
                return false;
        } else if (!isaffirm2.equals(other.isaffirm2))
            return false;
        if (reviewName == null) {
            if (other.reviewName != null)
                return false;
        } else if (!reviewName.equals(other.reviewName))
            return false;
        if (reviewName2 == null) {
            if (other.reviewName2 != null)
                return false;
        } else if (!reviewName2.equals(other.reviewName2))
            return false;
        if (reviewReason == null) {
            if (other.reviewReason != null)
                return false;
        } else if (!reviewReason.equals(other.reviewReason))
            return false;
        if (reviewReason2 == null) {
            if (other.reviewReason2 != null)
                return false;
        } else if (!reviewReason2.equals(other.reviewReason2))
            return false;
        if (reviewStatus == null) {
            if (other.reviewStatus != null)
                return false;
        } else if (!reviewStatus.equals(other.reviewStatus))
            return false;
        if (reviewStatus2 == null) {
            if (other.reviewStatus2 != null)
                return false;
        } else if (!reviewStatus2.equals(other.reviewStatus2))
            return false;
        if (reviewTime == null) {
            if (other.reviewTime != null)
                return false;
        } else if (!reviewTime.equals(other.reviewTime))
            return false;
        if (reviewTime2 == null) {
            if (other.reviewTime2 != null)
                return false;
        } else if (!reviewTime2.equals(other.reviewTime2))
            return false;
        if (scoreCycleEndTime == null) {
            if (other.scoreCycleEndTime != null)
                return false;
        } else if (!scoreCycleEndTime.equals(other.scoreCycleEndTime))
            return false;
        if (scoreCycleStartTime == null) {
            if (other.scoreCycleStartTime != null)
                return false;
        } else if (!scoreCycleStartTime.equals(other.scoreCycleStartTime))
            return false;
        if (scoreValue == null) {
            if (other.scoreValue != null)
                return false;
        } else if (!scoreValue.equals(other.scoreValue))
            return false;
        if (scoreValue2 == null) {
            if (other.scoreValue2 != null)
                return false;
        } else if (!scoreValue2.equals(other.scoreValue2))
            return false;
        if (stuId == null) {
            if (other.stuId != null)
                return false;
        } else if (!stuId.equals(other.stuId))
            return false;
        if (total == null) {
            if (other.total != null)
                return false;
        } else if (!total.equals(other.total))
            return false;
        if (total2 == null) {
            if (other.total2 != null)
                return false;
        } else if (!total2.equals(other.total2))
            return false;
        return true;
    }

}
