package cn.chuanwise.wud.detector;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>正则表达式探测器</h1>
 *
 * @author Chuanwise
 */
public class RegexpDetector
    implements Detector {
    
    private final Charset charset;
    private final String group;
    private final Pattern pattern;
    
    public RegexpDetector(Pattern pattern, String group, Charset charset) {
        Objects.requireNonNull(pattern, "Pattern is null!");
        Objects.requireNonNull(group, "Group is null!");
        
        this.pattern = pattern;
        this.group = group;
        this.charset = charset == null ? Charset.defaultCharset() : charset;
    }
    
    @Override
    public List<String> detect(URL url) throws Exception {
        final String content = new String(url.openConnection().getInputStream().readAllBytes(), charset);
        final Matcher matcher = pattern.matcher(content);
        final List<String> results = new ArrayList<>();
        while (matcher.find()) {
            results.add(matcher.group(group));
        }
        return results;
    }
    
    public Charset getCharset() {
        return charset;
    }
    
    public String getGroup() {
        return group;
    }
    
    public Pattern getPattern() {
        return pattern;
    }
}
