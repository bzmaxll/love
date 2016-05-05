/* 
 * Created by bzm at 2012-12-3 
 * Copyright HiSupplier.com 
 */

package com.love.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bzm
 *
 */
public class UpdateMap implements java.io.Serializable {

	private static final long serialVersionUID = -8814793885023276598L;
	private String table;
	private String field; // 单个搜索时使用
	private List<Field> fields = new ArrayList<Field>();
	private List<Field> where = new ArrayList<Field>();

	/**
	 * @param table
	 */
	public UpdateMap(String table) {
		super();
		this.table = table;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void addField(String name, String value) {
		fields.add(new Field(name, value, null));
	}

	/**
	 * @param name
	 * @param value
	 */
	public void addField(String name, int value) {
		fields.add(new Field(name, value + "", null));
	}

	/**
	 * @param name
	 * @param operator
	 * @param num
	 */
	public void addField(String name, String operator, int num) {
		fields.add(new Field(name, null, operator, num));
	}

	/**
	 * @param name
	 * @param value
	 */
	public void addWhere(String name, String value) {
		where.add(new Field(name, value, "="));
	}

	/**
	 * @param name
	 * @param value
	 */
	public void addWhere(String name, int value) {
		where.add(new Field(name, value + "", "="));
	}

	/**
	 * @param name
	 * @param value
	 * @param operator
	 */
	public void addWhere(String name, String value, String operator) {
		where.add(new Field(name, value, operator));
	}

	/**
	 * @param name
	 * @param value
	 * @param operator
	 */
	public void addWhere(String name, int value, String operator) {
		where.add(new Field(name, value + "", operator));
	}

	public class Field implements java.io.Serializable {

		private static final long serialVersionUID = 5072553924745908883L;

		private String name;
		private String value;
		private String operator;
		private int num;

		/**
		 * @param name
		 * @param value
		 * @param operator
		 */
		public Field(String name, String value, String operator) {
			super();
			this.name = name;
			this.value = value;
			this.operator = operator;
		}

		/**
		 * @param name
		 * @param value
		 * @param operator
		 * @param num
		 */
		public Field(String name, String value, String operator, int num) {
			super();
			this.name = name;
			this.value = value;
			this.operator = operator;
			this.num = num;
		}

		public String getOperator() {
			return this.operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getNum() {
			return this.num;
		}

		public void setNum(int num) {
			this.num = num;
		}

	}

	public List<Field> getFields() {
		return this.fields;
	}

	public List<Field> getWhere() {
		return this.where;
	}

	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
