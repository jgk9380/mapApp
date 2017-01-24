package com.entity.p;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Table(name = "CODE_DEPART")
public class CodeDepart implements Serializable {
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
      private CodeDepart codeDepart;
      @OneToMany(mappedBy = "codeDepart")
      private List<CodeDepart> codeDepartList;

      public CodeDepart() {
      }

      public CodeDepart(BigDecimal departLevel,String dutierId,CodeDepart codeDepart,BigDecimal id,String name) {
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

      public CodeDepart getCodeDepart() {
            return codeDepart;
      }

      public void setCodeDepart(CodeDepart codeDepart) {
            this.codeDepart = codeDepart;
      }

      public List<CodeDepart> getCodeDepartList() {
            return codeDepartList;
      }

      public void setCodeDepartList(List<CodeDepart> codeDepartList) {
            this.codeDepartList = codeDepartList;
      }

      public CodeDepart addCodeDepart(CodeDepart codeDepart) {
            getCodeDepartList().add(codeDepart);
            codeDepart.setCodeDepart(this);
            return codeDepart;
      }

      public CodeDepart removeCodeDepart(CodeDepart codeDepart) {
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
