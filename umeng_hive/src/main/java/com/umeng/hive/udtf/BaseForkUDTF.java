package com.umeng.hive.udtf;

import com.alibaba.fastjson.JSONObject;
import com.umeng.common.domain.AppBaseLog;
import com.umeng.common.domain.AppLogAggEntity;
import com.umeng.hive.util.DateUtil;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By BF On 2020/2/16
 */
public abstract class BaseForkUDTF<T> extends GenericUDTF {
    private Class<T> clazz;

    private ObjectInspectorConverters.Converter[] converters ;
    //字段名称列表
    List<String> fieldNames = null ;
    //检查器列表
    List<ObjectInspector> ois = null ;
    //通过构造函数抽取子类的泛型化超类部分
    public BaseForkUDTF(){
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class) type.getActualTypeArguments()[0];
    }
    /**
     * 校验参数合规性
     */
    public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {

        //字段名称集合
        fieldNames = new ArrayList<String>() ;

        //对象检查器集合
        List<ObjectInspector> ois = new ArrayList<ObjectInspector>() ;

        if (args.length != 4) {
            throw new UDFArgumentException("fork()需要4个参数!!!");
        }
        //判断参数的类型
        //1.string
        if(args[0].getCategory()!= ObjectInspector.Category.PRIMITIVE
                || ((PrimitiveObjectInspector)args[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
            throw new UDFArgumentException("参数{1}不是string类型!!!");
        }
        //2.bigint
        if (args[1].getCategory() != ObjectInspector.Category.PRIMITIVE || ((PrimitiveObjectInspector) args[1]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.LONG) {
            throw new UDFArgumentException("参数{2}不是bigint类型!!!");
        }
        //3.string
        if (args[2].getCategory() != ObjectInspector.Category.PRIMITIVE || ((PrimitiveObjectInspector) args[2]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("参数{3}不是string类型!!!");
        }
        //4.string
        if (args[3].getCategory() != ObjectInspector.Category.PRIMITIVE || ((PrimitiveObjectInspector) args[3]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("参数{4}不是string类型!!!");
        }

        //类型转换器
        converters = new ObjectInspectorConverters.Converter[args.length];
        //保持每个参数对应的转换器
        converters[0] = ObjectInspectorConverters.getConverter(args[0] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
        converters[1] = ObjectInspectorConverters.getConverter(args[1] , PrimitiveObjectInspectorFactory.javaLongObjectInspector) ;
        converters[2] = ObjectInspectorConverters.getConverter(args[2] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
        converters[3] = ObjectInspectorConverters.getConverter(args[3] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;

        try {
            popOIS(fieldNames , ois) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结构体对象检查器
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames , ois) ;
    }
    /**
     * 组装对象检查器
     * 将字段名称和对象检查器集合同步组装完成
     * 每个字段都对应各自的对象检查器(ObjectInspector)
     */
    private void popOIS(List<String> fieldNames, List<ObjectInspector> ois) throws Exception {
        //获取clazz类的bean信息
        BeanInfo bi = Introspector.getBeanInfo(clazz) ;

        //得到所有属性
        PropertyDescriptor[] pps = bi.getPropertyDescriptors();

        for(PropertyDescriptor pp :pps){
            String name = pp.getName() ;
            Class type = pp.getPropertyType() ;
            Method get = pp.getReadMethod() ;
            Method set = pp.getWriteMethod() ;
            //
            if(get != null && set != null){
                if(type == Long.class || type == long.class){
                    fieldNames.add(name) ;
                    ois.add(PrimitiveObjectInspectorFactory.javaLongObjectInspector) ;
                }
                else if(type == int.class || type ==Integer.class){
                    fieldNames.add(name);
                    ois.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                }
                else if(type == String.class){
                    fieldNames.add(name);
                    ois.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
                }
            }
        }
    }
    public void process(Object[] args) throws HiveException {
        //检查一下参数的个数有效性
        if (args.length != 4) {
            throw new UDFArgumentException("fork()需要4个参数!!!");
        }

        String servertimestr = (String) converters[0].convert(args[0]);
        Long clienttimems = (Long) converters[1].convert(args[1]);
        String clientip = (String) converters[2].convert(args[2]);
        String json = (String) converters[3].convert(args[3]);

        //替换\"为"
        json = json.replace("\\\"" , "\"") ;

        //解析json，返回日志聚合体对象
        AppLogAggEntity agg = JSONObject.parseObject(json , AppLogAggEntity.class) ;
        //TODO 时间对齐
        alignTime(agg , servertimestr , clienttimems);

        //
        extraProcess(clientip);
        List<T> logs = getLogs(agg) ;
        //外层for循环决定行数
        for(Object log : logs){
            Object[] arr = new Object[fieldNames.size()] ;
            int i = 0 ;
            //内层for循环决定列数，顺序和filedname顺序相同
            for(String fname : fieldNames){
                try {
                    PropertyDescriptor pp = new PropertyDescriptor(fname , clazz) ;
                    Method get = pp.getReadMethod() ;
                    if(get != null){
                        Object retValue = get.invoke(log) ;
                        arr[i] = retValue ;
                    }
                    i ++ ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //转发对象，就是输出一行
            forward(arr);
        }
    }

    /**
     * 额外得数据处理，关于ip地址的处理，默认空实现
     */
    protected void extraProcess(String clientip) {}

    /**
     * 服务器，客户端时间对其方法
     */
    private void alignTime(AppLogAggEntity agg, String servertimestr, Long clienttimems) {
        try {
            long servertimems = DateUtil.parseDateStrInUS(servertimestr);
            //时间差
            long offset = servertimems - clienttimems;
            //获取所有得日志
            List<AppBaseLog> logs = new ArrayList<AppBaseLog>();
            logs.addAll(agg.getStartupLogs());
            logs.addAll(agg.getEventLogs());
            logs.addAll(agg.getErrorLogs());
            logs.addAll(agg.getPageLogs());
            logs.addAll(agg.getUsageLogs());
            //对其所有日志时间
            for(AppBaseLog log : logs){
                log.setCreatedAtMs(log.getCreatedAtMs() + offset);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //抽象方法，子类必须重写
    public abstract List<T> getLogs(AppLogAggEntity agg);

    public void close() throws HiveException {

    }
}
