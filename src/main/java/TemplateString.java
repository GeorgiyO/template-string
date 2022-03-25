import java.util.Map;
import java.util.regex.Pattern;

public class TemplateString {
  public static final Pattern templatePattern = Pattern.compile("\\$\\{([^}]+)\\}");
  private final String source;

  public TemplateString(String source) {
    this.source = source;
  }

  public String getSource() {
    return source;
  }

  public String format(Map<String, Object> parameters) {
    return templatePattern.matcher(source)
                          .replaceAll(mr -> {
                            var key = mr.group();
                            key = key.substring(2, key.length() - 1);
                            return extractValue(parameters, key);
                          });
  }

  @SuppressWarnings("rawtypes")
  private String extractValue(Map parameters, String key) {
    var subKeys = key.split("\\.");
    for (int i = 0; i < subKeys.length - 1; i++) {
      var maybeMap = parameters.get(subKeys[i]);
      if (maybeMap instanceof Map map) {
        parameters = map;
      } else {
        throw new IllegalStateException("Expected map, but got: " + maybeMap);
      }
    }
    var finalValue = parameters.get(subKeys[subKeys.length - 1]);
    return String.valueOf(finalValue);
  }
}
