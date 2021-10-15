package textEditor;

import org.api.EditorAPI;
import org.api.TextAreaController;

public class DatePlugin implements EditorAPI {
    @Override
    public String onLoad() {
        // Add Button to toolBar
        return "Date.dateBtnClickHandler";
    }
    public void dateBtnClickHandler(TextAreaController tc){
        tc.insert("2021-10-15");
    }
}
