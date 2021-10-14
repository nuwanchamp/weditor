package textEditor;

import org.api.EditorAPI;

public class DatePlugin implements EditorAPI {
    @Override
    public String onLoad() {
        // Add Button to toolBar
        return "Date.dateBtnClickHandler";
    }
    public void dateBtnClickHandler(){
        System.out.println("This Works too");
    }
}
