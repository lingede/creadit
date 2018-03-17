package com.castiel.model.generator;

import com.castiel.core.base.BaseModel;
import java.util.Date;

@SuppressWarnings("serial")
public class PActEnrol extends BaseModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p_act_enrol.act_id
     *
     * @mbggenerated
     */
    private String actId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p_act_enrol.stu_id
     *
     * @mbggenerated
     */
    private String stuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p_act_enrol.dept_id
     *
     * @mbggenerated
     */
    private String deptId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p_act_enrol.isenrol
     *
     * @mbggenerated
     */
    private Integer isenrol;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p_act_enrol.enrol_time
     *
     * @mbggenerated
     */
    private Date enrolTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p_act_enrol.act_id
     *
     * @return the value of p_act_enrol.act_id
     *
     * @mbggenerated
     */
    public String getActId() {
        return actId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p_act_enrol.act_id
     *
     * @param actId the value for p_act_enrol.act_id
     *
     * @mbggenerated
     */
    public void setActId(String actId) {
        this.actId = actId == null ? null : actId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p_act_enrol.stu_id
     *
     * @return the value of p_act_enrol.stu_id
     *
     * @mbggenerated
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p_act_enrol.stu_id
     *
     * @param stuId the value for p_act_enrol.stu_id
     *
     * @mbggenerated
     */
    public void setStuId(String stuId) {
        this.stuId = stuId == null ? null : stuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p_act_enrol.dept_id
     *
     * @return the value of p_act_enrol.dept_id
     *
     * @mbggenerated
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p_act_enrol.dept_id
     *
     * @param deptId the value for p_act_enrol.dept_id
     *
     * @mbggenerated
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p_act_enrol.isenrol
     *
     * @return the value of p_act_enrol.isenrol
     *
     * @mbggenerated
     */
    public Integer getIsenrol() {
        return isenrol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p_act_enrol.isenrol
     *
     * @param isenrol the value for p_act_enrol.isenrol
     *
     * @mbggenerated
     */
    public void setIsenrol(Integer isenrol) {
        this.isenrol = isenrol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p_act_enrol.enrol_time
     *
     * @return the value of p_act_enrol.enrol_time
     *
     * @mbggenerated
     */
    public Date getEnrolTime() {
        return enrolTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p_act_enrol.enrol_time
     *
     * @param enrolTime the value for p_act_enrol.enrol_time
     *
     * @mbggenerated
     */
    public void setEnrolTime(Date enrolTime) {
        this.enrolTime = enrolTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table p_act_enrol
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", actId=").append(actId);
        sb.append(", stuId=").append(stuId);
        sb.append(", deptId=").append(deptId);
        sb.append(", isenrol=").append(isenrol);
        sb.append(", enrolTime=").append(enrolTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table p_act_enrol
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PActEnrol other = (PActEnrol) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getActId() == null ? other.getActId() == null : this.getActId().equals(other.getActId()))
            && (this.getStuId() == null ? other.getStuId() == null : this.getStuId().equals(other.getStuId()))
            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
            && (this.getIsenrol() == null ? other.getIsenrol() == null : this.getIsenrol().equals(other.getIsenrol()))
            && (this.getEnrolTime() == null ? other.getEnrolTime() == null : this.getEnrolTime().equals(other.getEnrolTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table p_act_enrol
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getActId() == null) ? 0 : getActId().hashCode());
        result = prime * result + ((getStuId() == null) ? 0 : getStuId().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        result = prime * result + ((getIsenrol() == null) ? 0 : getIsenrol().hashCode());
        result = prime * result + ((getEnrolTime() == null) ? 0 : getEnrolTime().hashCode());
        return result;
    }
}