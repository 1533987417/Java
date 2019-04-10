package entity;

public class TrainActivityUnifiedConfiguration implements Cloneable {
    private Integer id;
    private String name;
    private String field;
    private Integer status;
    private Integer parentId;
    private Integer vid;
    private String parentField;
    private Integer fieldType;
    private String fieldRemark;
    private Integer sort;
    private String value;

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }

    public TrainActivityUnifiedConfiguration(Integer vid) {
        this.name = "";
        this.status = 0;
        this.field = "";
        this.parentId = 0;
        this.sort = 0;
        this.id = 0;
        this.parentField = "";
        this.value = "";
        this.fieldRemark = "";
        this.vid = vid;
    }

    public TrainActivityUnifiedConfiguration() {
        this.name = "";
        this.status = 0;
        this.field = "";
        this.parentId = 0;
        this.sort = 0;
        this.id = 0;
        this.parentField = "";
        this.value = "";
        this.fieldRemark = "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getParentField() {
        return parentField;
    }

    public void setParentField(String parentField) {
        this.parentField = parentField;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldRemark() {
        return fieldRemark;
    }

    public void setFieldRemark(String fieldRemark) {
        this.fieldRemark = fieldRemark;
    }

    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (sort == null ? 0 : sort.hashCode());
        return hashno;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final TrainActivityUnifiedConfiguration entity = (TrainActivityUnifiedConfiguration) obj;
        if (this == entity) {
            return true;
        } else {
            return (this.sort.equals(entity.sort));
        }
    }
}
