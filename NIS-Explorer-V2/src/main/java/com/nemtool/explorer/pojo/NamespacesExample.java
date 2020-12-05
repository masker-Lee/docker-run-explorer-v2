package com.nemtool.explorer.pojo;

import java.util.ArrayList;
import java.util.List;

public class NamespacesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NamespacesExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNamespaceIsNull() {
            addCriterion("namespace is null");
            return (Criteria) this;
        }

        public Criteria andNamespaceIsNotNull() {
            addCriterion("namespace is not null");
            return (Criteria) this;
        }

        public Criteria andNamespaceEqualTo(String value) {
            addCriterion("namespace =", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotEqualTo(String value) {
            addCriterion("namespace <>", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceGreaterThan(String value) {
            addCriterion("namespace >", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceGreaterThanOrEqualTo(String value) {
            addCriterion("namespace >=", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLessThan(String value) {
            addCriterion("namespace <", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLessThanOrEqualTo(String value) {
            addCriterion("namespace <=", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLike(String value) {
            addCriterion("namespace like", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotLike(String value) {
            addCriterion("namespace not like", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceIn(List<String> values) {
            addCriterion("namespace in", values, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotIn(List<String> values) {
            addCriterion("namespace not in", values, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceBetween(String value1, String value2) {
            addCriterion("namespace between", value1, value2, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotBetween(String value1, String value2) {
            addCriterion("namespace not between", value1, value2, "namespace");
            return (Criteria) this;
        }

        public Criteria andNoIsNull() {
            addCriterion("no is null");
            return (Criteria) this;
        }

        public Criteria andNoIsNotNull() {
            addCriterion("no is not null");
            return (Criteria) this;
        }

        public Criteria andNoEqualTo(Long value) {
            addCriterion("no =", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotEqualTo(Long value) {
            addCriterion("no <>", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThan(Long value) {
            addCriterion("no >", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThanOrEqualTo(Long value) {
            addCriterion("no >=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThan(Long value) {
            addCriterion("no <", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThanOrEqualTo(Long value) {
            addCriterion("no <=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoIn(List<Long> values) {
            addCriterion("no in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotIn(List<Long> values) {
            addCriterion("no not in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoBetween(Long value1, Long value2) {
            addCriterion("no between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotBetween(Long value1, Long value2) {
            addCriterion("no not between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceIsNull() {
            addCriterion("rootNamespace is null");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceIsNotNull() {
            addCriterion("rootNamespace is not null");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceEqualTo(String value) {
            addCriterion("rootNamespace =", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceNotEqualTo(String value) {
            addCriterion("rootNamespace <>", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceGreaterThan(String value) {
            addCriterion("rootNamespace >", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceGreaterThanOrEqualTo(String value) {
            addCriterion("rootNamespace >=", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceLessThan(String value) {
            addCriterion("rootNamespace <", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceLessThanOrEqualTo(String value) {
            addCriterion("rootNamespace <=", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceLike(String value) {
            addCriterion("rootNamespace like", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceNotLike(String value) {
            addCriterion("rootNamespace not like", value, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceIn(List<String> values) {
            addCriterion("rootNamespace in", values, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceNotIn(List<String> values) {
            addCriterion("rootNamespace not in", values, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceBetween(String value1, String value2) {
            addCriterion("rootNamespace between", value1, value2, "rootnamespace");
            return (Criteria) this;
        }

        public Criteria andRootnamespaceNotBetween(String value1, String value2) {
            addCriterion("rootNamespace not between", value1, value2, "rootnamespace");
            return (Criteria) this;
        }
        
        public Criteria andRootnamespaceidEqualTo(int value) {
            addCriterion("rootNamespaceId =", value, "rootNamespaceId");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(Integer value) {
            addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(Integer value) {
            addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(Integer value) {
            addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(Integer value) {
            addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(Integer value) {
            addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<Integer> values) {
            addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<Integer> values) {
            addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(Integer value1, Integer value2) {
            addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNull() {
            addCriterion("timeStamp is null");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNotNull() {
            addCriterion("timeStamp is not null");
            return (Criteria) this;
        }

        public Criteria andTimestampEqualTo(Long value) {
            addCriterion("timeStamp =", value, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotEqualTo(Long value) {
            addCriterion("timeStamp <>", value, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThan(Long value) {
            addCriterion("timeStamp >", value, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("timeStamp >=", value, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThan(Long value) {
            addCriterion("timeStamp <", value, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThanOrEqualTo(Long value) {
            addCriterion("timeStamp <=", value, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampIn(List<Long> values) {
            addCriterion("timeStamp in", values, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotIn(List<Long> values) {
            addCriterion("timeStamp not in", values, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampBetween(Long value1, Long value2) {
            addCriterion("timeStamp between", value1, value2, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotBetween(Long value1, Long value2) {
            addCriterion("timeStamp not between", value1, value2, "timeStamp");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeIsNull() {
            addCriterion("expiredTime is null");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeIsNotNull() {
            addCriterion("expiredTime is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeEqualTo(Long value) {
            addCriterion("expiredTime =", value, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeNotEqualTo(Long value) {
            addCriterion("expiredTime <>", value, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeGreaterThan(Long value) {
            addCriterion("expiredTime >", value, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeGreaterThanOrEqualTo(Long value) {
            addCriterion("expiredTime >=", value, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeLessThan(Long value) {
            addCriterion("expiredTime <", value, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeLessThanOrEqualTo(Long value) {
            addCriterion("expiredTime <=", value, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeIn(List<Long> values) {
            addCriterion("expiredTime in", values, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeNotIn(List<Long> values) {
            addCriterion("expiredTime not in", values, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeBetween(Long value1, Long value2) {
            addCriterion("expiredTime between", value1, value2, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andExpiredtimeNotBetween(Long value1, Long value2) {
            addCriterion("expiredTime not between", value1, value2, "expiredtime");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesIsNull() {
            addCriterion("subNamespaces is null");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesIsNotNull() {
            addCriterion("subNamespaces is not null");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesEqualTo(String value) {
            addCriterion("subNamespaces =", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesNotEqualTo(String value) {
            addCriterion("subNamespaces <>", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesGreaterThan(String value) {
            addCriterion("subNamespaces >", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesGreaterThanOrEqualTo(String value) {
            addCriterion("subNamespaces >=", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesLessThan(String value) {
            addCriterion("subNamespaces <", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesLessThanOrEqualTo(String value) {
            addCriterion("subNamespaces <=", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesLike(String value) {
            addCriterion("subNamespaces like", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesNotLike(String value) {
            addCriterion("subNamespaces not like", value, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesIn(List<String> values) {
            addCriterion("subNamespaces in", values, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesNotIn(List<String> values) {
            addCriterion("subNamespaces not in", values, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesBetween(String value1, String value2) {
            addCriterion("subNamespaces between", value1, value2, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andSubnamespacesNotBetween(String value1, String value2) {
            addCriterion("subNamespaces not between", value1, value2, "subnamespaces");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesIsNull() {
            addCriterion("mosaicNames is null");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesIsNotNull() {
            addCriterion("mosaicNames is not null");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesEqualTo(String value) {
            addCriterion("mosaicNames =", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesNotEqualTo(String value) {
            addCriterion("mosaicNames <>", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesGreaterThan(String value) {
            addCriterion("mosaicNames >", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesGreaterThanOrEqualTo(String value) {
            addCriterion("mosaicNames >=", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesLessThan(String value) {
            addCriterion("mosaicNames <", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesLessThanOrEqualTo(String value) {
            addCriterion("mosaicNames <=", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesLike(String value) {
            addCriterion("mosaicNames like", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesNotLike(String value) {
            addCriterion("mosaicNames not like", value, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesIn(List<String> values) {
            addCriterion("mosaicNames in", values, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesNotIn(List<String> values) {
            addCriterion("mosaicNames not in", values, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesBetween(String value1, String value2) {
            addCriterion("mosaicNames between", value1, value2, "mosaicnames");
            return (Criteria) this;
        }

        public Criteria andMosaicnamesNotBetween(String value1, String value2) {
            addCriterion("mosaicNames not between", value1, value2, "mosaicnames");
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