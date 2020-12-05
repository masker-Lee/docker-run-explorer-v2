package com.nemtool.explorer.pojo;

import java.util.ArrayList;
import java.util.List;

public class MosaicsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MosaicsExample() {
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

        public Criteria andMosaicidIsNull() {
            addCriterion("mosaicID is null");
            return (Criteria) this;
        }

        public Criteria andMosaicidIsNotNull() {
            addCriterion("mosaicID is not null");
            return (Criteria) this;
        }

        public Criteria andMosaicidEqualTo(String value) {
            addCriterion("mosaicID =", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidNotEqualTo(String value) {
            addCriterion("mosaicID <>", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidGreaterThan(String value) {
            addCriterion("mosaicID >", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidGreaterThanOrEqualTo(String value) {
            addCriterion("mosaicID >=", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidLessThan(String value) {
            addCriterion("mosaicID <", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidLessThanOrEqualTo(String value) {
            addCriterion("mosaicID <=", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidLike(String value) {
            addCriterion("mosaicID like", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidNotLike(String value) {
            addCriterion("mosaicID not like", value, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidIn(List<String> values) {
            addCriterion("mosaicID in", values, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidNotIn(List<String> values) {
            addCriterion("mosaicID not in", values, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidBetween(String value1, String value2) {
            addCriterion("mosaicID between", value1, value2, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicidNotBetween(String value1, String value2) {
            addCriterion("mosaicID not between", value1, value2, "mosaicid");
            return (Criteria) this;
        }

        public Criteria andMosaicnameIsNull() {
            addCriterion("mosaicName is null");
            return (Criteria) this;
        }

        public Criteria andMosaicnameIsNotNull() {
            addCriterion("mosaicName is not null");
            return (Criteria) this;
        }

        public Criteria andMosaicnameEqualTo(String value) {
            addCriterion("mosaicName =", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameNotEqualTo(String value) {
            addCriterion("mosaicName <>", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameGreaterThan(String value) {
            addCriterion("mosaicName >", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameGreaterThanOrEqualTo(String value) {
            addCriterion("mosaicName >=", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameLessThan(String value) {
            addCriterion("mosaicName <", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameLessThanOrEqualTo(String value) {
            addCriterion("mosaicName <=", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameLike(String value) {
            addCriterion("mosaicName like", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameNotLike(String value) {
            addCriterion("mosaicName not like", value, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameIn(List<String> values) {
            addCriterion("mosaicName in", values, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameNotIn(List<String> values) {
            addCriterion("mosaicName not in", values, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameBetween(String value1, String value2) {
            addCriterion("mosaicName between", value1, value2, "mosaicname");
            return (Criteria) this;
        }

        public Criteria andMosaicnameNotBetween(String value1, String value2) {
            addCriterion("mosaicName not between", value1, value2, "mosaicname");
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

        public Criteria andDivisibilityIsNull() {
            addCriterion("divisibility is null");
            return (Criteria) this;
        }

        public Criteria andDivisibilityIsNotNull() {
            addCriterion("divisibility is not null");
            return (Criteria) this;
        }

        public Criteria andDivisibilityEqualTo(Integer value) {
            addCriterion("divisibility =", value, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityNotEqualTo(Integer value) {
            addCriterion("divisibility <>", value, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityGreaterThan(Integer value) {
            addCriterion("divisibility >", value, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityGreaterThanOrEqualTo(Integer value) {
            addCriterion("divisibility >=", value, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityLessThan(Integer value) {
            addCriterion("divisibility <", value, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityLessThanOrEqualTo(Integer value) {
            addCriterion("divisibility <=", value, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityIn(List<Integer> values) {
            addCriterion("divisibility in", values, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityNotIn(List<Integer> values) {
            addCriterion("divisibility not in", values, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityBetween(Integer value1, Integer value2) {
            addCriterion("divisibility between", value1, value2, "divisibility");
            return (Criteria) this;
        }

        public Criteria andDivisibilityNotBetween(Integer value1, Integer value2) {
            addCriterion("divisibility not between", value1, value2, "divisibility");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyIsNull() {
            addCriterion("initialSupply is null");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyIsNotNull() {
            addCriterion("initialSupply is not null");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyEqualTo(Long value) {
            addCriterion("initialSupply =", value, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyNotEqualTo(Long value) {
            addCriterion("initialSupply <>", value, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyGreaterThan(Long value) {
            addCriterion("initialSupply >", value, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyGreaterThanOrEqualTo(Long value) {
            addCriterion("initialSupply >=", value, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyLessThan(Long value) {
            addCriterion("initialSupply <", value, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyLessThanOrEqualTo(Long value) {
            addCriterion("initialSupply <=", value, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyIn(List<Long> values) {
            addCriterion("initialSupply in", values, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyNotIn(List<Long> values) {
            addCriterion("initialSupply not in", values, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyBetween(Long value1, Long value2) {
            addCriterion("initialSupply between", value1, value2, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andInitialsupplyNotBetween(Long value1, Long value2) {
            addCriterion("initialSupply not between", value1, value2, "initialsupply");
            return (Criteria) this;
        }

        public Criteria andSupplymutableIsNull() {
            addCriterion("supplyMutable is null");
            return (Criteria) this;
        }

        public Criteria andSupplymutableIsNotNull() {
            addCriterion("supplyMutable is not null");
            return (Criteria) this;
        }

        public Criteria andSupplymutableEqualTo(Integer value) {
            addCriterion("supplyMutable =", value, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableNotEqualTo(Integer value) {
            addCriterion("supplyMutable <>", value, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableGreaterThan(Integer value) {
            addCriterion("supplyMutable >", value, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableGreaterThanOrEqualTo(Integer value) {
            addCriterion("supplyMutable >=", value, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableLessThan(Integer value) {
            addCriterion("supplyMutable <", value, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableLessThanOrEqualTo(Integer value) {
            addCriterion("supplyMutable <=", value, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableIn(List<Integer> values) {
            addCriterion("supplyMutable in", values, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableNotIn(List<Integer> values) {
            addCriterion("supplyMutable not in", values, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableBetween(Integer value1, Integer value2) {
            addCriterion("supplyMutable between", value1, value2, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andSupplymutableNotBetween(Integer value1, Integer value2) {
            addCriterion("supplyMutable not between", value1, value2, "supplymutable");
            return (Criteria) this;
        }

        public Criteria andTransferableIsNull() {
            addCriterion("transferable is null");
            return (Criteria) this;
        }

        public Criteria andTransferableIsNotNull() {
            addCriterion("transferable is not null");
            return (Criteria) this;
        }

        public Criteria andTransferableEqualTo(Integer value) {
            addCriterion("transferable =", value, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableNotEqualTo(Integer value) {
            addCriterion("transferable <>", value, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableGreaterThan(Integer value) {
            addCriterion("transferable >", value, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableGreaterThanOrEqualTo(Integer value) {
            addCriterion("transferable >=", value, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableLessThan(Integer value) {
            addCriterion("transferable <", value, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableLessThanOrEqualTo(Integer value) {
            addCriterion("transferable <=", value, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableIn(List<Integer> values) {
            addCriterion("transferable in", values, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableNotIn(List<Integer> values) {
            addCriterion("transferable not in", values, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableBetween(Integer value1, Integer value2) {
            addCriterion("transferable between", value1, value2, "transferable");
            return (Criteria) this;
        }

        public Criteria andTransferableNotBetween(Integer value1, Integer value2) {
            addCriterion("transferable not between", value1, value2, "transferable");
            return (Criteria) this;
        }

        public Criteria andLevytypeIsNull() {
            addCriterion("levyType is null");
            return (Criteria) this;
        }

        public Criteria andLevytypeIsNotNull() {
            addCriterion("levyType is not null");
            return (Criteria) this;
        }

        public Criteria andLevytypeEqualTo(Integer value) {
            addCriterion("levyType =", value, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeNotEqualTo(Integer value) {
            addCriterion("levyType <>", value, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeGreaterThan(Integer value) {
            addCriterion("levyType >", value, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("levyType >=", value, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeLessThan(Integer value) {
            addCriterion("levyType <", value, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeLessThanOrEqualTo(Integer value) {
            addCriterion("levyType <=", value, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeIn(List<Integer> values) {
            addCriterion("levyType in", values, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeNotIn(List<Integer> values) {
            addCriterion("levyType not in", values, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeBetween(Integer value1, Integer value2) {
            addCriterion("levyType between", value1, value2, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevytypeNotBetween(Integer value1, Integer value2) {
            addCriterion("levyType not between", value1, value2, "levytype");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientIsNull() {
            addCriterion("levyRecipient is null");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientIsNotNull() {
            addCriterion("levyRecipient is not null");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientEqualTo(String value) {
            addCriterion("levyRecipient =", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientNotEqualTo(String value) {
            addCriterion("levyRecipient <>", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientGreaterThan(String value) {
            addCriterion("levyRecipient >", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientGreaterThanOrEqualTo(String value) {
            addCriterion("levyRecipient >=", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientLessThan(String value) {
            addCriterion("levyRecipient <", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientLessThanOrEqualTo(String value) {
            addCriterion("levyRecipient <=", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientLike(String value) {
            addCriterion("levyRecipient like", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientNotLike(String value) {
            addCriterion("levyRecipient not like", value, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientIn(List<String> values) {
            addCriterion("levyRecipient in", values, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientNotIn(List<String> values) {
            addCriterion("levyRecipient not in", values, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientBetween(String value1, String value2) {
            addCriterion("levyRecipient between", value1, value2, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevyrecipientNotBetween(String value1, String value2) {
            addCriterion("levyRecipient not between", value1, value2, "levyrecipient");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceIsNull() {
            addCriterion("levyNamespace is null");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceIsNotNull() {
            addCriterion("levyNamespace is not null");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceEqualTo(String value) {
            addCriterion("levyNamespace =", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceNotEqualTo(String value) {
            addCriterion("levyNamespace <>", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceGreaterThan(String value) {
            addCriterion("levyNamespace >", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceGreaterThanOrEqualTo(String value) {
            addCriterion("levyNamespace >=", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceLessThan(String value) {
            addCriterion("levyNamespace <", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceLessThanOrEqualTo(String value) {
            addCriterion("levyNamespace <=", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceLike(String value) {
            addCriterion("levyNamespace like", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceNotLike(String value) {
            addCriterion("levyNamespace not like", value, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceIn(List<String> values) {
            addCriterion("levyNamespace in", values, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceNotIn(List<String> values) {
            addCriterion("levyNamespace not in", values, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceBetween(String value1, String value2) {
            addCriterion("levyNamespace between", value1, value2, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevynamespaceNotBetween(String value1, String value2) {
            addCriterion("levyNamespace not between", value1, value2, "levynamespace");
            return (Criteria) this;
        }

        public Criteria andLevymosaicIsNull() {
            addCriterion("levyMosaic is null");
            return (Criteria) this;
        }

        public Criteria andLevymosaicIsNotNull() {
            addCriterion("levyMosaic is not null");
            return (Criteria) this;
        }

        public Criteria andLevymosaicEqualTo(String value) {
            addCriterion("levyMosaic =", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicNotEqualTo(String value) {
            addCriterion("levyMosaic <>", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicGreaterThan(String value) {
            addCriterion("levyMosaic >", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicGreaterThanOrEqualTo(String value) {
            addCriterion("levyMosaic >=", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicLessThan(String value) {
            addCriterion("levyMosaic <", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicLessThanOrEqualTo(String value) {
            addCriterion("levyMosaic <=", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicLike(String value) {
            addCriterion("levyMosaic like", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicNotLike(String value) {
            addCriterion("levyMosaic not like", value, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicIn(List<String> values) {
            addCriterion("levyMosaic in", values, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicNotIn(List<String> values) {
            addCriterion("levyMosaic not in", values, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicBetween(String value1, String value2) {
            addCriterion("levyMosaic between", value1, value2, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevymosaicNotBetween(String value1, String value2) {
            addCriterion("levyMosaic not between", value1, value2, "levymosaic");
            return (Criteria) this;
        }

        public Criteria andLevyfeeIsNull() {
            addCriterion("levyFee is null");
            return (Criteria) this;
        }

        public Criteria andLevyfeeIsNotNull() {
            addCriterion("levyFee is not null");
            return (Criteria) this;
        }

        public Criteria andLevyfeeEqualTo(Long value) {
            addCriterion("levyFee =", value, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeNotEqualTo(Long value) {
            addCriterion("levyFee <>", value, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeGreaterThan(Long value) {
            addCriterion("levyFee >", value, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeGreaterThanOrEqualTo(Long value) {
            addCriterion("levyFee >=", value, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeLessThan(Long value) {
            addCriterion("levyFee <", value, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeLessThanOrEqualTo(Long value) {
            addCriterion("levyFee <=", value, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeIn(List<Long> values) {
            addCriterion("levyFee in", values, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeNotIn(List<Long> values) {
            addCriterion("levyFee not in", values, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeBetween(Long value1, Long value2) {
            addCriterion("levyFee between", value1, value2, "levyfee");
            return (Criteria) this;
        }

        public Criteria andLevyfeeNotBetween(Long value1, Long value2) {
            addCriterion("levyFee not between", value1, value2, "levyfee");
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