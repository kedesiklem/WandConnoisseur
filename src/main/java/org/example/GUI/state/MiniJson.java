package org.example.GUI.state;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A tiny, dependency-free JSON reader/writer. The project has no JSON library on the classpath
 * and the data this GUI needs to persist (custom spell categories, saved wand builds) is small
 * and flat, so rather than pulling in Gson/Jackson for two config files, this hand-rolled reader
 * covers exactly what's needed: objects, arrays, strings, numbers, booleans, null.
 *
 * Usage: build a {@code Map<String,Object>}/{@code List<Object>} tree with plain Java values
 * (String, Number, Boolean, null, nested Map/List) and pass it to {@link #write}; {@link #parse}
 * returns the same shape back.
 */
public final class MiniJson{
    private MiniJson(){}

    public static String write(Object value){
        StringBuilder out = new StringBuilder();
        writeValue(value, out);
        return out.toString();
    }

    private static void writeValue(Object value, StringBuilder out){
        if(value == null){
            out.append("null");
        }else if(value instanceof String s){
            writeString(s, out);
        }else if(value instanceof Boolean || value instanceof Number){
            out.append(value);
        }else if(value instanceof Map<?, ?> map){
            out.append('{');
            boolean first = true;
            for(Map.Entry<?, ?> entry : map.entrySet()){
                if(!first){
                    out.append(',');
                }
                first = false;
                writeString(String.valueOf(entry.getKey()), out);
                out.append(':');
                writeValue(entry.getValue(), out);
            }
            out.append('}');
        }else if(value instanceof List<?> list){
            out.append('[');
            boolean first = true;
            for(Object item : list){
                if(!first){
                    out.append(',');
                }
                first = false;
                writeValue(item, out);
            }
            out.append(']');
        }else{
            throw new IllegalArgumentException("Unsupported JSON value: " + value.getClass());
        }
    }

    private static void writeString(String s, StringBuilder out){
        out.append('"');
        for(int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            switch(c){
                case '"' -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default -> {
                    if(c < 0x20){
                        out.append(String.format("\\u%04x", (int)c));
                    }else{
                        out.append(c);
                    }
                }
            }
        }
        out.append('"');
    }

    public static Object parse(String text){
        Parser parser = new Parser(text);
        Object value = parser.parseValue();
        parser.skipWhitespace();
        return value;
    }

    private static final class Parser{
        private final String text;
        private int pos = 0;

        Parser(String text){
            this.text = text;
        }

        void skipWhitespace(){
            while(this.pos < this.text.length() && Character.isWhitespace(this.text.charAt(this.pos))){
                this.pos++;
            }
        }

        char peek(){
            return this.text.charAt(this.pos);
        }

        Object parseValue(){
            skipWhitespace();
            char c = peek();
            return switch(c){
                case '{' -> parseObject();
                case '[' -> parseArray();
                case '"' -> parseString();
                case 't', 'f' -> parseBoolean();
                case 'n' -> parseNull();
                default -> parseNumber();
            };
        }

        Map<String, Object> parseObject(){
            Map<String, Object> result = new LinkedHashMap<>();
            this.pos++; // {
            skipWhitespace();
            if(peek() == '}'){
                this.pos++;
                return result;
            }
            while(true){
                skipWhitespace();
                String key = parseString();
                skipWhitespace();
                this.pos++; // :
                Object value = parseValue();
                result.put(key, value);
                skipWhitespace();
                char c = peek();
                this.pos++;
                if(c == '}'){
                    break;
                }
            }
            return result;
        }

        List<Object> parseArray(){
            List<Object> result = new ArrayList<>();
            this.pos++; // [
            skipWhitespace();
            if(peek() == ']'){
                this.pos++;
                return result;
            }
            while(true){
                Object value = parseValue();
                result.add(value);
                skipWhitespace();
                char c = peek();
                this.pos++;
                if(c == ']'){
                    break;
                }
            }
            return result;
        }

        String parseString(){
            StringBuilder out = new StringBuilder();
            this.pos++; // opening quote
            while(true){
                char c = this.text.charAt(this.pos++);
                if(c == '"'){
                    break;
                }
                if(c == '\\'){
                    char escaped = this.text.charAt(this.pos++);
                    switch(escaped){
                        case '"' -> out.append('"');
                        case '\\' -> out.append('\\');
                        case '/' -> out.append('/');
                        case 'n' -> out.append('\n');
                        case 'r' -> out.append('\r');
                        case 't' -> out.append('\t');
                        case 'u' -> {
                            out.append((char)Integer.parseInt(this.text.substring(this.pos, this.pos + 4), 16));
                            this.pos += 4;
                        }
                        default -> out.append(escaped);
                    }
                }else{
                    out.append(c);
                }
            }
            return out.toString();
        }

        Boolean parseBoolean(){
            if(this.text.startsWith("true", this.pos)){
                this.pos += 4;
                return Boolean.TRUE;
            }
            this.pos += 5;
            return Boolean.FALSE;
        }

        Object parseNull(){
            this.pos += 4;
            return null;
        }

        Double parseNumber(){
            int start = this.pos;
            while(this.pos < this.text.length() && "+-0123456789.eE".indexOf(this.text.charAt(this.pos)) != -1){
                this.pos++;
            }
            return Double.parseDouble(this.text.substring(start, this.pos));
        }
    }
}
