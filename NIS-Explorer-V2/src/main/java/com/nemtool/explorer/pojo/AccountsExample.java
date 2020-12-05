package com.nemtool.explorer.pojo;

import java.util.ArrayList;
import java.util.List;

public class AccountsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountsExample() {
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

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(Long value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Long value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Long value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Long value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Long value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Long value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Long> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Long> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Long value1, Long value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Long value1, Long value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBlocksIsNull() {
            addCriterion("blocks is null");
            return (Criteria) this;
        }

        public Criteria andBlocksIsNotNull() {
            addCriterion("blocks is not null");
            return (Criteria) this;
        }

        public Criteria andBlocksEqualTo(Long value) {
            addCriterion("blocks =", value, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksNotEqualTo(Long value) {
            addCriterion("blocks <>", value, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksGreaterThan(Long value) {
            addCriterion("blocks >", value, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksGreaterThanOrEqualTo(Long value) {
            addCriterion("blocks >=", value, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksLessThan(Long value) {
            addCriterion("blocks <", value, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksLessThanOrEqualTo(Long value) {
            addCriterion("blocks <=", value, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksIn(List<Long> values) {
            addCriterion("blocks in", values, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksNotIn(List<Long> values) {
            addCriterion("blocks not in", values, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksBetween(Long value1, Long value2) {
            addCriterion("blocks between", value1, value2, "blocks");
            return (Criteria) this;
        }

        public Criteria andBlocksNotBetween(Long value1, Long value2) {
            addCriterion("blocks not between", value1, value2, "blocks");
            return (Criteria) this;
        }

        public Criteria andFeesIsNull() {
            addCriterion("fees is null");
            return (Criteria) this;
        }

        public Criteria andFeesIsNotNull() {
            addCriterion("fees is not null");
            return (Criteria) this;
        }

        public Criteria andFeesEqualTo(Long value) {
            addCriterion("fees =", value, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesNotEqualTo(Long value) {
            addCriterion("fees <>", value, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesGreaterThan(Long value) {
            addCriterion("fees >", value, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesGreaterThanOrEqualTo(Long value) {
            addCriterion("fees >=", value, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesLessThan(Long value) {
            addCriterion("fees <", value, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesLessThanOrEqualTo(Long value) {
            addCriterion("fees <=", value, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesIn(List<Long> values) {
            addCriterion("fees in", values, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesNotIn(List<Long> values) {
            addCriterion("fees not in", values, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesBetween(Long value1, Long value2) {
            addCriterion("fees between", value1, value2, "fees");
            return (Criteria) this;
        }

        public Criteria andFeesNotBetween(Long value1, Long value2) {
            addCriterion("fees not between", value1, value2, "fees");
            return (Criteria) this;
        }

        public Criteria andLastblockIsNull() {
            addCriterion("lastBlock is null");
            return (Criteria) this;
        }

        public Criteria andLastblockIsNotNull() {
            addCriterion("lastBlock is not null");
            return (Criteria) this;
        }

        public Criteria andLastblockEqualTo(Long value) {
            addCriterion("lastBlock =", value, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockNotEqualTo(Long value) {
            addCriterion("lastBlock <>", value, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockGreaterThan(Long value) {
            addCriterion("lastBlock >", value, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockGreaterThanOrEqualTo(Long value) {
            addCriterion("lastBlock >=", value, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockLessThan(Long value) {
            addCriterion("lastBlock <", value, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockLessThanOrEqualTo(Long value) {
            addCriterion("lastBlock <=", value, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockIn(List<Long> values) {
            addCriterion("lastBlock in", values, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockNotIn(List<Long> values) {
            addCriterion("lastBlock not in", values, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockBetween(Long value1, Long value2) {
            addCriterion("lastBlock between", value1, value2, "lastblock");
            return (Criteria) this;
        }

        public Criteria andLastblockNotBetween(Long value1, Long value2) {
            addCriterion("lastBlock not between", value1, value2, "lastblock");
            return (Criteria) this;
        }

        public Criteria andPublickeyIsNull() {
            addCriterion("publicKey is null");
            return (Criteria) this;
        }

        public Criteria andPublickeyIsNotNull() {
            addCriterion("publicKey is not null");
            return (Criteria) this;
        }

        public Criteria andPublickeyEqualTo(String value) {
            addCriterion("publicKey =", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyNotEqualTo(String value) {
            addCriterion("publicKey <>", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyGreaterThan(String value) {
            addCriterion("publicKey >", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyGreaterThanOrEqualTo(String value) {
            addCriterion("publicKey >=", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyLessThan(String value) {
            addCriterion("publicKey <", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyLessThanOrEqualTo(String value) {
            addCriterion("publicKey <=", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyLike(String value) {
            addCriterion("publicKey like", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyNotLike(String value) {
            addCriterion("publicKey not like", value, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyIn(List<String> values) {
            addCriterion("publicKey in", values, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyNotIn(List<String> values) {
            addCriterion("publicKey not in", values, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyBetween(String value1, String value2) {
            addCriterion("publicKey between", value1, value2, "publickey");
            return (Criteria) this;
        }

        public Criteria andPublickeyNotBetween(String value1, String value2) {
            addCriterion("publicKey not between", value1, value2, "publickey");
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