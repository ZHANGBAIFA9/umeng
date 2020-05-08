package com.umeng.hive.udf;

import com.umeng.hive.util.DateUtil;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create By BF On 2020/3/3
 * 按照月份进行格式化计算
 */
public class FormatByMonthUDF extends GenericUDF {
    //对象转换器
    private ObjectInspectorConverters.Converter[] converters = new ObjectInspectorConverters.Converter[4] ;
    public ObjectInspector initialize(ObjectInspector[] ois) throws UDFArgumentException {
        int n  =  ois.length;
        if(n < 1 || n > 4){
            throw new UDFArgumentException("参数个数不符");
        }
        switch (n){
            case 1 :
                if(ois[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.INT){
                    throw new UDFArgumentException("参数{1}需要是int类型");
                }
                converters[0] = ObjectInspectorConverters.getConverter(ois[0] , PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                break;
            case 2 :
                if(ois[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.INT){
                    throw new UDFArgumentException("参数{1}需要是int类型");
                }
                if(ois[1].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[1]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
                    throw new UDFArgumentException("参数{2}需要是string类型");
                }
                converters[0] = ObjectInspectorConverters.getConverter(ois[0] , PrimitiveObjectInspectorFactory.javaIntObjectInspector) ;
                converters[1] = ObjectInspectorConverters.getConverter(ois[1] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
                break;
            case 3 :
                if(ois[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.LONG){
                    throw new UDFArgumentException("参数{1}需要是bigint类型");
                }
                if(ois[1].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[1]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.INT){
                    throw new UDFArgumentException("参数{2}需要是int类型");
                }
                if(ois[2].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[2]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
                    throw new UDFArgumentException("参数{2}需要是string类型");
                }
                converters[0] = ObjectInspectorConverters.getConverter(ois[0] , PrimitiveObjectInspectorFactory.javaLongObjectInspector) ;
                converters[1] = ObjectInspectorConverters.getConverter(ois[1] , PrimitiveObjectInspectorFactory.javaIntObjectInspector) ;
                converters[2] = ObjectInspectorConverters.getConverter(ois[2] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
                break;
            case 4 :
                if(ois[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
                    throw new UDFArgumentException("参数{1}需要是string类型");
                }
                if(ois[1].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[1]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
                    throw new UDFArgumentException("参数{2}需要是string类型");
                }
                if(ois[2].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[2]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.INT){
                    throw new UDFArgumentException("参数{2}需要是int类型");
                }
                if(ois[3].getCategory() != ObjectInspector.Category.PRIMITIVE
                        || ((PrimitiveObjectInspector)ois[3]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING){
                    throw new UDFArgumentException("参数{2}需要是string类型");
                }
                converters[0] = ObjectInspectorConverters.getConverter(ois[0] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
                converters[1] = ObjectInspectorConverters.getConverter(ois[1] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
                converters[2] = ObjectInspectorConverters.getConverter(ois[2] , PrimitiveObjectInspectorFactory.javaIntObjectInspector) ;
                converters[3] = ObjectInspectorConverters.getConverter(ois[3] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
                break;
        }
        //返回字符串
        return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
    }
    /**
     * 实现计算过程
     */
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        int n = arguments.length;
        if(n < 1 || n > 4){
            throw new UDFArgumentException("参数个数不符") ;
        }
        switch (n){
            case 1 :
            {
                //formatMonth(-1)
                int offset = (Integer)converters[0].convert(arguments[0].get()) ;
                return DateUtil.formatMonth(new Date() , offset , "yyyyMM") ;
            }
            case 2 :
            {
                //formatMonth(-1 , 'yyyy/MM/dd')
                int offset = (Integer) converters[0].convert(arguments[0].get());
                String fmt= (String) converters[1].convert(arguments[1].get());
                return DateUtil.formatMonth(new Date(), offset, fmt);
            }
            case 3 :
            {
                //formatMonth(154xxxx ,,-1 , 'yyyy/MM/dd')
                long ts = (Long) converters[0].convert(arguments[0].get());
                int offset = (Integer) converters[1].convert(arguments[1].get());
                String fmt = (String) converters[2].convert(arguments[2].get());
                return DateUtil.formatMonth(new Date(ts), offset, fmt);
            }
            case 4 :
            {
                try {
                    //formatMonth('2018/12/12' , 'yyyy/MM/dd', -1 , 'yyyy/MM/dd')
                    String refDate = (String) converters[0].convert(arguments[0].get());
                    String refFmt = (String) converters[1].convert(arguments[1].get());
                    int offset = (Integer) converters[2].convert(arguments[2].get());
                    String fmt = (String) converters[3].convert(arguments[3].get());
                    SimpleDateFormat sdf = new SimpleDateFormat(refFmt) ;
                    return DateUtil.formatMonth(sdf.parse(refDate), offset, fmt);
                } catch (Exception e) {
                    throw new UDFArgumentException("参数{2}日期格式不对!!!") ;
                }
            }
        }
        return null;
    }

    public String getDisplayString(String[] strings) {
        return "formatbymonth";
    }
}
