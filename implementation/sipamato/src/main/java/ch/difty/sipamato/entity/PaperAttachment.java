package ch.difty.sipamato.entity;

/**
 * Attachment to a paper.<p>
 *
 * Note that typically, the paper repository will automatically load the attachments with the paper, but
 * not with the actual binary attachment content. This will have to be loaded separately.<p>
 *
 * The repo will not save the attachments with the paper. They will have to be saved separately too.
 *
 * @author u.joss
 */
public class PaperAttachment extends IdSipamatoEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private static final long BYTES_PER_KB = 1024;

    public static final String PAPER_ID = "paperId";
    public static final String NAME = "name";
    public static final String CONTENT = "content";
    public static final String CONTENT_TYPE = "contentType";
    public static final String SIZE = "size";
    public static final String SIZE_KB = "sizeKb";

    private Long paperId;
    private String name;
    private String contentType;
    private Long size;

    // not persisted or loaded when loading through paper
    private byte[] content;

    public PaperAttachment() {
    }

    public PaperAttachment(Integer id, Long paperId, String name, byte[] content, String contentType, Long size) {
        setId(id);
        setPaperId(paperId);
        setName(name);
        setContent(content);
        setContentType(contentType);
        setSize(size);
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the size in bytes
     */
    public Long getSize() {
        return size;
    }

    /**
     * @return the size in kilo bytes (rounded up)
     */
    public Long getSizeKiloBytes() {
        if (size == null)
            return null;
        if ((size % BYTES_PER_KB) == 0)
            return size / BYTES_PER_KB;
        return size / BYTES_PER_KB + 1;
    }

    /**
     * @param size the size in bytes
     */
    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String getDisplayValue() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PaperAttachment[paperId=");
        builder.append(paperId);
        builder.append(",name=");
        builder.append(name);
        builder.append(",id=");
        builder.append(getId());
        builder.append("]");
        return builder.toString();
    }

}
