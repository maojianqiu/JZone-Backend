package com.blog.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BgmsBlogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BgmsBlogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAppreciationIsNull() {
            addCriterion("appreciation is null");
            return (Criteria) this;
        }

        public Criteria andAppreciationIsNotNull() {
            addCriterion("appreciation is not null");
            return (Criteria) this;
        }

        public Criteria andAppreciationEqualTo(Boolean value) {
            addCriterion("appreciation =", value, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationNotEqualTo(Boolean value) {
            addCriterion("appreciation <>", value, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationGreaterThan(Boolean value) {
            addCriterion("appreciation >", value, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationGreaterThanOrEqualTo(Boolean value) {
            addCriterion("appreciation >=", value, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationLessThan(Boolean value) {
            addCriterion("appreciation <", value, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationLessThanOrEqualTo(Boolean value) {
            addCriterion("appreciation <=", value, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationIn(List<Boolean> values) {
            addCriterion("appreciation in", values, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationNotIn(List<Boolean> values) {
            addCriterion("appreciation not in", values, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationBetween(Boolean value1, Boolean value2) {
            addCriterion("appreciation between", value1, value2, "appreciation");
            return (Criteria) this;
        }

        public Criteria andAppreciationNotBetween(Boolean value1, Boolean value2) {
            addCriterion("appreciation not between", value1, value2, "appreciation");
            return (Criteria) this;
        }

        public Criteria andCommentabledIsNull() {
            addCriterion("commentabled is null");
            return (Criteria) this;
        }

        public Criteria andCommentabledIsNotNull() {
            addCriterion("commentabled is not null");
            return (Criteria) this;
        }

        public Criteria andCommentabledEqualTo(Boolean value) {
            addCriterion("commentabled =", value, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledNotEqualTo(Boolean value) {
            addCriterion("commentabled <>", value, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledGreaterThan(Boolean value) {
            addCriterion("commentabled >", value, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("commentabled >=", value, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledLessThan(Boolean value) {
            addCriterion("commentabled <", value, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledLessThanOrEqualTo(Boolean value) {
            addCriterion("commentabled <=", value, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledIn(List<Boolean> values) {
            addCriterion("commentabled in", values, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledNotIn(List<Boolean> values) {
            addCriterion("commentabled not in", values, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledBetween(Boolean value1, Boolean value2) {
            addCriterion("commentabled between", value1, value2, "commentabled");
            return (Criteria) this;
        }

        public Criteria andCommentabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("commentabled not between", value1, value2, "commentabled");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andFirstPictureIsNull() {
            addCriterion("first_picture is null");
            return (Criteria) this;
        }

        public Criteria andFirstPictureIsNotNull() {
            addCriterion("first_picture is not null");
            return (Criteria) this;
        }

        public Criteria andFirstPictureEqualTo(String value) {
            addCriterion("first_picture =", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureNotEqualTo(String value) {
            addCriterion("first_picture <>", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureGreaterThan(String value) {
            addCriterion("first_picture >", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureGreaterThanOrEqualTo(String value) {
            addCriterion("first_picture >=", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureLessThan(String value) {
            addCriterion("first_picture <", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureLessThanOrEqualTo(String value) {
            addCriterion("first_picture <=", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureLike(String value) {
            addCriterion("first_picture like", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureNotLike(String value) {
            addCriterion("first_picture not like", value, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureIn(List<String> values) {
            addCriterion("first_picture in", values, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureNotIn(List<String> values) {
            addCriterion("first_picture not in", values, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureBetween(String value1, String value2) {
            addCriterion("first_picture between", value1, value2, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andFirstPictureNotBetween(String value1, String value2) {
            addCriterion("first_picture not between", value1, value2, "firstPicture");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Boolean value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Boolean value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Boolean value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Boolean value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Boolean> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Boolean> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagUrlIsNull() {
            addCriterion("flag_url is null");
            return (Criteria) this;
        }

        public Criteria andFlagUrlIsNotNull() {
            addCriterion("flag_url is not null");
            return (Criteria) this;
        }

        public Criteria andFlagUrlEqualTo(String value) {
            addCriterion("flag_url =", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlNotEqualTo(String value) {
            addCriterion("flag_url <>", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlGreaterThan(String value) {
            addCriterion("flag_url >", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlGreaterThanOrEqualTo(String value) {
            addCriterion("flag_url >=", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlLessThan(String value) {
            addCriterion("flag_url <", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlLessThanOrEqualTo(String value) {
            addCriterion("flag_url <=", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlLike(String value) {
            addCriterion("flag_url like", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlNotLike(String value) {
            addCriterion("flag_url not like", value, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlIn(List<String> values) {
            addCriterion("flag_url in", values, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlNotIn(List<String> values) {
            addCriterion("flag_url not in", values, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlBetween(String value1, String value2) {
            addCriterion("flag_url between", value1, value2, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andFlagUrlNotBetween(String value1, String value2) {
            addCriterion("flag_url not between", value1, value2, "flagUrl");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNull() {
            addCriterion("recommend is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("recommend is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(Boolean value) {
            addCriterion("recommend =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(Boolean value) {
            addCriterion("recommend <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(Boolean value) {
            addCriterion("recommend >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(Boolean value) {
            addCriterion("recommend >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(Boolean value) {
            addCriterion("recommend <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(Boolean value) {
            addCriterion("recommend <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<Boolean> values) {
            addCriterion("recommend in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<Boolean> values) {
            addCriterion("recommend not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(Boolean value1, Boolean value2) {
            addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(Boolean value1, Boolean value2) {
            addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andShareStatementIsNull() {
            addCriterion("share_statement is null");
            return (Criteria) this;
        }

        public Criteria andShareStatementIsNotNull() {
            addCriterion("share_statement is not null");
            return (Criteria) this;
        }

        public Criteria andShareStatementEqualTo(Boolean value) {
            addCriterion("share_statement =", value, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementNotEqualTo(Boolean value) {
            addCriterion("share_statement <>", value, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementGreaterThan(Boolean value) {
            addCriterion("share_statement >", value, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementGreaterThanOrEqualTo(Boolean value) {
            addCriterion("share_statement >=", value, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementLessThan(Boolean value) {
            addCriterion("share_statement <", value, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementLessThanOrEqualTo(Boolean value) {
            addCriterion("share_statement <=", value, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementIn(List<Boolean> values) {
            addCriterion("share_statement in", values, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementNotIn(List<Boolean> values) {
            addCriterion("share_statement not in", values, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementBetween(Boolean value1, Boolean value2) {
            addCriterion("share_statement between", value1, value2, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andShareStatementNotBetween(Boolean value1, Boolean value2) {
            addCriterion("share_statement not between", value1, value2, "shareStatement");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andUmsIdIsNull() {
            addCriterion("ums_id is null");
            return (Criteria) this;
        }

        public Criteria andUmsIdIsNotNull() {
            addCriterion("ums_id is not null");
            return (Criteria) this;
        }

        public Criteria andUmsIdEqualTo(Long value) {
            addCriterion("ums_id =", value, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdNotEqualTo(Long value) {
            addCriterion("ums_id <>", value, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdGreaterThan(Long value) {
            addCriterion("ums_id >", value, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ums_id >=", value, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdLessThan(Long value) {
            addCriterion("ums_id <", value, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdLessThanOrEqualTo(Long value) {
            addCriterion("ums_id <=", value, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdIn(List<Long> values) {
            addCriterion("ums_id in", values, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdNotIn(List<Long> values) {
            addCriterion("ums_id not in", values, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdBetween(Long value1, Long value2) {
            addCriterion("ums_id between", value1, value2, "umsId");
            return (Criteria) this;
        }

        public Criteria andUmsIdNotBetween(Long value1, Long value2) {
            addCriterion("ums_id not between", value1, value2, "umsId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}