package com.entity.p;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

@Entity
@Table(name = "DEPART")
public class Depart implements Serializable {
    @SuppressWarnings("compatibility:759974213003708404")
    private static final long serialVersionUID = 1L;
    @Column(name = "DEPART_LEVEL")
      private BigDecimal departLevel;
      @Column(name = "DUTIER_ID",length = 20)
      private String dutierId;
      @Id
      @Column(nullable = false)
      private BigDecimal id;
      @Column(unique = true,length = 80)
      private String name;
      @Column(name="DEPART_TYpe")
      private BigDecimal departType;
      
      @ManyToOne
      @JoinColumn(name = "HIGHER_DEPART_ID")
      private Depart codeDepart;
      @OneToMany(mappedBy = "codeDepart")
      private List<Depart> codeDepartList;

      public Depart() {
      }

      public Depart(BigDecimal departLevel, String dutierId, Depart codeDepart, BigDecimal id, String name) {
            this.departLevel = departLevel;
            this.dutierId = dutierId;
            this.codeDepart = codeDepart;
            this.id = id;
            this.name = name;
      }

      public BigDecimal getDepartLevel() {
            return departLevel;
      }

      public void setDepartLevel(BigDecimal departLevel) {
            this.departLevel = departLevel;
      }

      public String getDutierId() {
            return dutierId;
      }

      public void setDutierId(String dutierId) {
            this.dutierId = dutierId;
      }

      public BigDecimal getId() {
            return id;
      }

      public void setId(BigDecimal id) {
            this.id = id;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public Depart getCodeDepart() {
            return codeDepart;
      }

      public void setCodeDepart(Depart codeDepart) {
            this.codeDepart = codeDepart;
      }

      public List<Depart> getCodeDepartList() {
            return codeDepartList;
      }

      public void setCodeDepartList(List<Depart> codeDepartList) {
            this.codeDepartList = codeDepartList;
      }

      public Depart addCodeDepart(Depart codeDepart) {
            getCodeDepartList().add(codeDepart);
            codeDepart.setCodeDepart(this);
            return codeDepart;
      }

      public Depart removeCodeDepart(Depart codeDepart) {
            getCodeDepartList().remove(codeDepart);
            codeDepart.setCodeDepart(null);
            return codeDepart;
      }


      public void setDepartType(BigDecimal departType) {
            this.departType = departType;
      }

      public BigDecimal getDepartType() {
            return departType;
      }
}
