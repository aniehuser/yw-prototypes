package org.yesworkflow.annotations;

import org.yesworkflow.YWKeywords;
import org.yesworkflow.extract.Comment;

public class Begin extends Delimiter {
    
    public Begin(Long id, Comment line, String comment) throws Exception {
        super(id, line, comment, YWKeywords.Tag.BEGIN);
    }
}

