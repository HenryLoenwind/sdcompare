package info.loenwind.compare;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImgWindowTest {

  @Test
  public final void testInsertNumber() {
    assertEquals("filename_42.ext", ImgWindow.insertNumber("filename.ext", 42));
    assertEquals("filename _42.ext", ImgWindow.insertNumber("filename .ext", 42));
    assertEquals("filename_42.ZI0", ImgWindow.insertNumber("filename.ZI0", 42));
    assertEquals("filename_42", ImgWindow.insertNumber("filename", 42));
  }

}
