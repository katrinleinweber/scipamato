package ch.difty.scipamato.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class ScipamatoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DISPLAY_VALUE = "displayValue";

    /**
     * Regex extending the classical \w with non-ASCII characters. To be used within a character class,<p>
     *
     * e.g. {@literal [\\w\\u00C0-\\u024f]}<p>
     *
     * Thanks to hqx5 @see <a href="http://stackoverflow.com/questions/4043307/why-this-regex-is-not-working-for-german-words">stackoverflow</a>
     */
    protected static final String RE_W = "\\w\\u00C0-\\u024f";
    protected static final String RE_WW = "[" + RE_W + "-']+";
    protected static final String RE_WW2 = "[." + RE_W + "-']+";
    // White Space
    protected static final String RE_S = "[ \\t\\f\\r\\n]";

    public static final String CREATED_DV = "createdDisplayValue";
    public static final String MODIFIED_DV = "modifiedDisplayValue";
    public static final String CREATED = "created";
    public static final String CREATOR_ID = "createdBy";
    public static final String CREATOR_NAME = "createdByName";
    public static final String CREATOR_FULL_NAME = "createdByFullName";
    public static final String MODIFIED = "lastModified";
    public static final String MODIFIER_ID = "lastModifiedBy";
    public static final String MODIFIER_NAME = "lastModifiedByName";
    public static final String VERSION = "version";

    private LocalDateTime created;
    private Integer createdBy;
    private LocalDateTime lastModified;
    private Integer lastModifiedBy;
    private int version;

    private transient String createdByName;
    private transient String createdByFullName;
    private transient String lastModifiedByName;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCreatedByFullName() {
        return createdByFullName;
    }

    public void setCreatedByFullName(String createdByFullName) {
        this.createdByFullName = createdByFullName;
    }

    public String getLastModifiedByName() {
        return lastModifiedByName;
    }

    public void setLastModifiedByName(String lastModifiedByName) {
        this.lastModifiedByName = lastModifiedByName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public abstract String getDisplayValue();

    public String getCreatedDisplayValue() {
        return makeDisplayValue(createdByName, created);
    }

    private String makeDisplayValue(String name, LocalDateTime ldt) {
        final StringBuilder sb = new StringBuilder();
        String close = "";
        if (name != null) {
            sb.append(name);
        }
        if (ldt != null) {
            if (sb.length() > 0) {
                sb.append(" (");
                close = ")";
            }
            sb.append(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append(close);
        }
        return sb.toString();
    }

    public String getModifiedDisplayValue() {
        return makeDisplayValue(lastModifiedByName, lastModified);
    }
}