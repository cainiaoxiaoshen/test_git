package com.gooseeker.fss.commons.mapper.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SUserExample() {
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
        
        /**
         * 属性等于
         * property = value
         * @param property
         * @param value
         * @return
         */
        public Criteria andEqualTo(String property, Object value) {
          addCriterion(property + " = ", value, null);
          return (Criteria) this;
        }
        
        /**
         * 属性不等于
         * property <> value
         * @param property
         * @param value
         * @return
         */
        public Criteria andNotEqualTo(String property, Object value) {
            addCriterion(property + " <>", value, null);
            return (Criteria) this;
        }

        /**
         * 属性大于
         * property > value
         * @param property
         * @param value
         * @return
         */
        public Criteria andGreaterThan(String property, Object value) {
            addCriterion(property + " >", value, null);
            return (Criteria) this;
        }

        /**
         * 大于等于
         * property > value
         * @param property
         * @param value
         * @return
         */
        public Criteria andGreaterThanOrEqualTo(String property, Object value) {
            addCriterion(property + " >=", value, null);
            return (Criteria) this;
        }

        /**
         * 小于
         * property < value
         * @param property
         * @param value
         * @return
         */
        public Criteria andIdLessThan(String property, Object value) {
            addCriterion(property + " <", value, null);
            return (Criteria) this;
        }

        /**
         * 小于等于
         * property <= value
         * @param property
         * @param value
         * @return
         */
        public Criteria andLessThanOrEqualTo(String property, Object value) {
            addCriterion(property + " <=", value, null);
            return (Criteria) this;
        }

        /**
         * property in (aa, bb, cc, dd)
         * @param property
         * @param values
         * @return
         */
        public Criteria andIdIn(String property, List<Object> values) {
            addCriterion(property + " in", values, null);
            return (Criteria) this;
        }

        /**
         * property not in (aa, bb, cc, dd)
         * @param property
         * @param values
         * @return
         */
        public Criteria andIdNotIn(String property, List<Object> values) {
            addCriterion(property + " not in", values, null);
            return (Criteria) this;
        }

        /**
         * property between value1 and value2
         * @param property
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andIdBetween(String property, Object value1, Object value2) {
            addCriterion(property + " between", value1, value2, null);
            return (Criteria) this;
        }

        /**
         * property not between value1 and value2
         * @param property
         * @param value1
         * @param value2
         * @return
         */
        public Criteria andIdNotBetween(String property, Object value1, Object value2) {
            addCriterion(property + " not between", value1, value2, null);
            return (Criteria) this;
        }
        
        /**
         * property like value
         * @param property
         * @param value
         * @return
         */
        public Criteria andLike(String property, String value) {
            addCriterion(property + " like", value, null);
            return (Criteria) this;
        }

        /**
         * property not like value
         * @param property
         * @param value
         * @return
         */
        public Criteria andNotLike(String property, String value) {
            addCriterion(property + " not like", value, null);
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