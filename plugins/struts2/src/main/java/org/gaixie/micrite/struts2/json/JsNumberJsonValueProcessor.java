package org.gaixie.micrite.struts2.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsNumberJsonValueProcessor implements JsonValueProcessor {


    public Object processArrayValue( Object value, JsonConfig jsonConfig ) {
       return process( value, jsonConfig );
    }

    public Object processObjectValue( String key, Object value, JsonConfig jsonConfig ) {
       return process( value, jsonConfig );
    }

    private Object process( Object value, JsonConfig jsonConfig ) {
    	return String.valueOf(value);
    }
 }
