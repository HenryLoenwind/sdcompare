package info.loenwind.compare.tools;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoableEdit;

public class ValueFilter implements UndoableEditListener {

  private static final String INT_STR = "([1-9]\\d*|0)";
  private static final String INTNN_STR = "([1-9]\\d*(\\s*,\\s*[1-9]\\d*)*)";
  private static final String FLOAT_STR = "(0*(\\.\\d*)?|1(\\.0*)?)";
  private static final String COMMA = "\\s*,\\s*";

  public static final Predicate<String> INT = text -> text.matches("^(" + INT_STR + COMMA + ")*" + INT_STR + "?$");
  public static final Predicate<String> INTNN = text -> text.matches("^(" + INTNN_STR + COMMA + ")*" + INTNN_STR + "?$");
  public static final Predicate<String> FLOAT01 = text -> text.matches("^(" + FLOAT_STR + COMMA + ")*" + FLOAT_STR + "?$");
  public static final Predicate<String> STRNQ = text -> text.matches("^[^\"]*$");

  private final List<UndoableEdit> queue = new ArrayList<>();
  private final Predicate<String> tester;
  private UndoableEdit lastClear = null;

  public ValueFilter(Predicate<String> tester) {
    this.tester = tester;
  }

  @Override
  public void undoableEditHappened(UndoableEditEvent e) {
    try {
      UndoableEdit edit = e.getEdit();
      if (!queue.isEmpty()) {
        queue.add(0, edit);
      } else if (edit instanceof DefaultDocumentEvent) {
        Document document = ((DefaultDocumentEvent) edit).getDocument();
        String text = document.getText(0, document.getLength());
        if (text == null || text.isEmpty()) {
          // isEmpty() must be valid because Ctrl-A, Ctrl-V first clears the document before inserting
          lastClear = edit;
        } else if (tester.test(text)) {
          lastClear = null;
        } else {
          if (lastClear != null) {
            // if the last valid edit was clearing the field (e.g. Ctrl-A, Ctrl-V), undo that, too
            // there can be false positives (e.g.: backspace, x), but we sadly don't get a replace event
            queue.add(0, lastClear);
            lastClear = null;
          }
          queue.add(0, edit);
          EventQueue.invokeLater(() -> {
            queue.forEach(UndoableEdit::undo);
            queue.clear();
          });
        }
      }
    } catch (BadLocationException e1) {
    }
  }

}
