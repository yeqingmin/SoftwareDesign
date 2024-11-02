package org.example.model.command.display;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;
@Data
@AllArgsConstructor
public class SpellCheckCommand implements Command {
    //管理的html文档
    private HTMLDocument htmlDocument;
    @Override
    public void execute() {
        htmlDocument.spellCheck();
    }

}
