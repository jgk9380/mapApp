package com.entity.p;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity

public class Employees implements Serializable {
    @SuppressWarnings("compatibility:4856950874988005198")
    private static final long serialVersionUID = 1L;
    @Id
  @Column(nullable = false, length = 20)
  private String id;

  @Column(length = 40)
  private String grade;
  @Column(name = "FIXED_ACHIEVEMENT")
  private BigDecimal fixedAchievement;
  @Column(name = "ACHIEVEMENT_RATION")
  private BigDecimal achievementRation;

  @Column(length = 20)
  private String name;


  @Column(length = 20)
  private String shortdesc;

  @Column(length = 20)
  private String tele;

  @Column(name = "DEPART_ID")
  private BigDecimal departId;

  @Column(name = "POSITION_TYPE_ID")
  private BigDecimal positionTypeId;

  @Column(name = "STAFF_ID")
  private String staffId;

  @Column(name = "FIXED_SALARY")
  private BigDecimal fixedSalary;

  @Column(name = "DYSUBSIDY")
  private BigDecimal dysubsidy;

  @OneToOne(optional = true, cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER)
  @JoinColumn(name = "DEPART_ID", referencedColumnName = "ID",
              updatable = false, insertable = false)
  private CodeDepart depart;




  public Employees() {
  }

  public Employees(BigDecimal departId, String grade, String id, String name,
                   BigDecimal positionTypeId, String shortdesc, String tele) {
    // this.departId = departId;
    this.grade = grade;
    this.id = id;
    this.name = name;
    // this.positionTypeId = positionTypeId;
    this.shortdesc = shortdesc;
    this.tele = tele;
  }


  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getShortdesc() {
    return shortdesc;
  }

  public void setShortdesc(String shortdesc) {
    this.shortdesc = shortdesc;
  }

  public String getTele() {
    return tele;
  }

  public void setTele(String tele) {
    this.tele = tele;
  }




  public void setDepart(CodeDepart depart) {
    this.depart = depart;
  }

  public CodeDepart getDepart() {
    return depart;
  }

  public String getDepartName() {
    return this.getDepart().getName();
  }

  public void setDepartName(String name) {
    //return this.getDepart().getName();
  }


  public void setPositionName(String posiName) {
    //return this.getPositionType().getValue();
  }


  public void setStaffId(String staffId) {
    this.staffId = staffId;
  }

  public String getStaffId() {
    return staffId;
  }

  public void setFixedSalary(BigDecimal fixedSalary) {
    this.fixedSalary = fixedSalary;
  }

  public BigDecimal getFixedSalary() {
    return fixedSalary;
  }

  public void setDysubsidy(BigDecimal dysubsidy) {
    this.dysubsidy = dysubsidy;
  }

  public BigDecimal getDysubsidy() {
    return dysubsidy;
  }

  public void setDepartId(BigDecimal departId) {
    this.departId = departId;
  }

  public BigDecimal getDepartId() {
    return departId;
  }

  public void setPositionTypeId(BigDecimal positionTypeId) {
    this.positionTypeId = positionTypeId;
  }

  public BigDecimal getPositionTypeId() {
    return positionTypeId;
  }



  public void setFixedAchievement(BigDecimal fixedAchievement) {
    this.fixedAchievement = fixedAchievement;
  }

  public BigDecimal getFixedAchievement() {
    return fixedAchievement;
  }

  public void setAchievementRation(BigDecimal achievementRation) {
    this.achievementRation = achievementRation;
  }

  public BigDecimal getAchievementRation() {
    return achievementRation;
  }

      @Override
      public boolean equals(Object object) {
            if (this == object) {
                  return true;
            }
            if (!(object instanceof Employees)) {
                  return false;
            }
            final Employees other = (Employees)object;
            if (!(id == null ? other.id == null : id.equals(other.id))) {
                  return false;
            }
            if (!(name == null ? other.name == null : name.equals(other.name))) {
                  return false;
            }
            if (!(tele == null ? other.tele == null : tele.equals(other.tele))) {
                  return false;
            }
            return true;
      }

      @Override
      public int hashCode() {
            final int PRIME = 37;
            int result = 1;
            result = PRIME * result + ((id == null) ? 0 : id.hashCode());
            result = PRIME * result + ((name == null) ? 0 : name.hashCode());
            result = PRIME * result + ((tele == null) ? 0 : tele.hashCode());
            return result;
      }
}
