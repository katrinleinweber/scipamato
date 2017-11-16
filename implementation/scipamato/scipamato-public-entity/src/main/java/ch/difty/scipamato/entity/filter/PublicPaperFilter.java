package ch.difty.scipamato.entity.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PublicPaperFilter extends ScipamatoFilter {

    private static final long serialVersionUID = 1L;

    public static final String NUMBER = "number";
    public static final String AUTHOR_MASK = "authorMask";
    public static final String METHODS_MASK = "methodsMask";
    public static final String PUB_YEAR_FROM = "publicationYearFrom";
    public static final String PUB_YEAR_UNTIL = "publicationYearUntil";

    private Long number;
    private String authorMask;
    private String methodsMask;
    private Integer publicationYearFrom;
    private Integer publicationYearUntil;

}