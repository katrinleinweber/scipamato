package ch.difty.sipamato.service;

import java.io.Serializable;
import java.util.List;

import ch.difty.sipamato.entity.Code;
import ch.difty.sipamato.entity.CodeClassId;

/**
 * Service operating with {@link Code}s.
 *
 * @author u.joss
 */
public interface CodeService extends Serializable {

    /**
     * Find all {@link Code}s of the specified {@link CodeClassId} localized in language with the provided languageCode
     *
     * @param codeClassId
     * @param languageCode
     * @return a list of {@link Code}s
     */
    List<Code> findCodesOfClass(CodeClassId codeClassId, String languageCode);
}