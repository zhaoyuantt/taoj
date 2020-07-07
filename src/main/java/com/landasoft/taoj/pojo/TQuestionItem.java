package com.landasoft.taoj.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TQuestionItem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.ID
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.P_ID
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    private String pId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.I_NAME
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    private String iName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.I_STATE
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    private String iState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.CREATED
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date created;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.UPDATED
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updated;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_QUESTION_ITEM.REMARK
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.ID
     *
     * @return the value of T_QUESTION_ITEM.ID
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.ID
     *
     * @param id the value for T_QUESTION_ITEM.ID
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.P_ID
     *
     * @return the value of T_QUESTION_ITEM.P_ID
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public String getpId() {
        return pId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.P_ID
     *
     * @param pId the value for T_QUESTION_ITEM.P_ID
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.I_NAME
     *
     * @return the value of T_QUESTION_ITEM.I_NAME
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public String getiName() {
        return iName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.I_NAME
     *
     * @param iName the value for T_QUESTION_ITEM.I_NAME
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setiName(String iName) {
        this.iName = iName == null ? null : iName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.I_STATE
     *
     * @return the value of T_QUESTION_ITEM.I_STATE
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public String getiState() {
        return iState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.I_STATE
     *
     * @param iState the value for T_QUESTION_ITEM.I_STATE
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setiState(String iState) {
        this.iState = iState == null ? null : iState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.CREATED
     *
     * @return the value of T_QUESTION_ITEM.CREATED
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.CREATED
     *
     * @param created the value for T_QUESTION_ITEM.CREATED
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.UPDATED
     *
     * @return the value of T_QUESTION_ITEM.UPDATED
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.UPDATED
     *
     * @param updated the value for T_QUESTION_ITEM.UPDATED
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_QUESTION_ITEM.REMARK
     *
     * @return the value of T_QUESTION_ITEM.REMARK
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_QUESTION_ITEM.REMARK
     *
     * @param remark the value for T_QUESTION_ITEM.REMARK
     *
     * @mbggenerated Tue Jul 07 09:29:55 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}