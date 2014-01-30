/**
 * Copyright (c) 2012-2014 Axelor. All Rights Reserved.
 *
 * The contents of this file are subject to the Common Public
 * Attribution License Version 1.0 (the “License”); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://license.axelor.com/.
 *
 * The License is based on the Mozilla Public License Version 1.1 but
 * Sections 14 and 15 have been added to cover use of software over a
 * computer network and provide for limited attribution for the
 * Original Developer. In addition, Exhibit A has been modified to be
 * consistent with Exhibit B.
 *
 * Software distributed under the License is distributed on an “AS IS”
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is part of "Axelor Business Suite", developed by
 * Axelor exclusively.
 *
 * The Original Developer is the Initial Developer. The Initial Developer of
 * the Original Code is Axelor.
 *
 * All portions of the code written by Axelor are
 * Copyright (c) 2012-2014 Axelor. All Rights Reserved.
 */
package com.axelor.test.db;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.joda.time.LocalDate;

import com.axelor.db.JPA;
import com.axelor.db.JpaModel;
import com.axelor.db.Query;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.db.internal.EntityHelper;
import com.google.common.collect.Lists;

@Entity
@Table(name = "CONTACT_CONTACT")
public class Contact extends JpaModel {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Title title;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@Widget(search = { "firstName", "lastName" })
	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	private String email;
	
	private String proEmail;

	private String phone;

	private String lang;
	
	@Widget(selection = "food.selection")
	private String food;
	
	private BigDecimal credit;

	private LocalDate dateOfBirth;

	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
	private List<Address> addresses;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch=FetchType.LAZY)
	private Set<Circle> circles;

	@Widget(title = "Photo", help = "Max size 4MB.")
	@Lob @Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Widget(multiline = true)
	private String notes;

	public Contact() {
	}

	public Contact(String firstName) {
		this.firstName = firstName;
	}

	public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return fullName = calculateFullName();
	}

	protected String calculateFullName() {
		fullName = firstName + " " + lastName;
		if (this.title != null) {
			return this.title.getName() + " " + fullName;
		}
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getProEmail() {
		return proEmail;
	}
	
	public void setProEmail(String proEmail) {
		this.proEmail = proEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getFood() {
		return food;
	}
	
	public void setFood(String food) {
		this.food = food;
	}
	
	public BigDecimal getCredit() {
		return credit;
	}
	
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Circle getGroup(int index) {
		if (circles == null) return null;
		return Lists.newArrayList(circles).get(index);
	}

	public Set<Circle> getCircles() {
		return circles;
	}

	public void setCircles(Set<Circle> groups) {
		this.circles = groups;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return EntityHelper.toString(this);
	}

	public Contact find(Long id) {
		return JPA.find(Contact.class, id);
	}

	public static Contact edit(Map<String, Object> values) {
		return JPA.edit(Contact.class, values);
	}

	public static Query<Contact> all() {
		return JPA.all(Contact.class);
	}
}
