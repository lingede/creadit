package com.castiel.core.util;

import java.lang.reflect.Type;
import java.util.List;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
/**
 * Created by YangBin on 2016/6/29.
 * 将父类所有元素值复制到子类
 */
public final class FatherToChildCopyUtil {

	public static final <Father, Child extends Father> void Copy(Father father, Child child){
		try {
			Class bc = father.getClass();
			if (father == null || child == null) {
				return;
			}
			DeserializeBeanInfo deserializeBeanInfo = DeserializeBeanInfo
					.computeSetters(child.getClass(), (Type) child.getClass());
			List<FieldInfo> getters = TypeUtils.computeGetters(father.getClass(),
					null);
			List<FieldInfo> setters = deserializeBeanInfo.getFieldList();
			Object v;
			FieldInfo getterfield;
			FieldInfo setterfidld;
			for (int j = 0; j < getters.size(); j++) {
				getterfield = getters.get(j);
				for (int i = 0; i < setters.size(); i++) {
					setterfidld = setters.get(i);
					if (setterfidld.getName().compareTo(getterfield.getName()) == 0) {
						v = getterfield.getMethod().invoke(father);
						setterfidld.getMethod().invoke(child, v);
						break;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}