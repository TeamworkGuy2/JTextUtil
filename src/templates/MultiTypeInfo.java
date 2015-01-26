package templates;

import java.util.ArrayList;
import java.util.List;

import codeTemplate.ClassTemplate;

/**
 * @param <T> the {@link ClassTemplateInfo} type of the data stored in this multi type info list
 * 
 * @author TeamworkGuy2
 * @since 2015-2-1
 */
public class MultiTypeInfo<T extends ClassTemplate> extends ClassTemplate {
	public List<MultiType<T>> types;


	public MultiTypeInfo() {
		this.types = new ArrayList<>();
	}


	/**
	 * @param <T> the {@link ClassTemplateInfo} type of the data stored in this multi-type instance
	 * 
	 * @author TeamworkGuy2
	 * @since 2015-2-1
	 */
	public static class MultiType<T extends ClassTemplate> {
		public T t1;
		public T t2;
		public T t3;
		public T t4;
		public T t5;
		public T t6;

		public MultiType(T t1, T t2) {
			this.t1 = t1;
			this.t2 = t2;
		}


		public MultiType(T t1, T t2, T t3, T t4, T t5, T t6) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
		}

	}

}
