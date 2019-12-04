package qdu.together.togethercore.respository;

/**
 * 数据转换对象（DTO）接口
 * 通过实现接口并且明确传入参数来完成数据对象转换操作
 * @param <ValueObject>
 */
public interface DataTransfer<ValueObject>{
    public Object changeData(ValueObject Value);
}