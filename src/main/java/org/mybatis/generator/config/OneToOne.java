package org.mybatis.generator.config;




public class OneToOne
{
  private String mappingTable;
  


  private String column;
  


  private String joinColumn;
  


  private String where;
  


  public OneToOne(String mappingTable, String column)
  {
    this.mappingTable = mappingTable;
    this.column = column;
  }
  
  public String getMappingTable() { return mappingTable; }
  
  public void setMappingTable(String mappingTable) {
    this.mappingTable = mappingTable;
  }
  
  public String getColumn() { return column; }
  
  public void setColumn(String column) {
    this.column = column;
  }
  
  public String getJoinColumn() { return joinColumn; }
  
  public void setJoinColumn(String joinColumn) {
    this.joinColumn = joinColumn;
  }
  
  public String getWhere() { return where; }
  
  public void setWhere(String where) {
    this.where = where;
  }
}
