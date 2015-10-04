package twg2.text.templates;

import java.util.List;
import java.util.Set;

import codeTemplate.primitiveTemplate.PrimitiveTypeClassTemplate;

/** A template for a random access type class like an array, {@link List}, {@link Set}, etc.
 * @author TeamworkGuy2
 * @since 2015-2-1
 */
public class RandomAccessTypeInfo extends PrimitiveTypeClassTemplate {
	public String getLength;
	public String getElement;
	public String getElementEnd;

}
