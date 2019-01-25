package entity;

public class JsonParseEntity {

   public JsonParseEntity(){
        this.name="";
        this.status=0;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    private Integer id;
    private String name;
    private String field;
    private Integer status;
    private Integer parentId;
    private String uniqueKey;
    //数据类型
    private Integer dataType;
    private Integer sort;

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        final JsonParseEntity entity = (JsonParseEntity) obj;
        if (this == entity) {
            return true;
        } else {
            return (this.sort.equals(entity.sort));
        }
    }
}
