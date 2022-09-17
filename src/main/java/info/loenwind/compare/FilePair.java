package info.loenwind.compare;

import java.io.File;
import java.util.Objects;

public class FilePair {

  private final File a, b;

  public FilePair(File a, File b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public int hashCode() {
    return a.compareTo(b) < 0 ? Objects.hash(a, b) : Objects.hash(b, a);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    FilePair other = (FilePair) obj;
    return (Objects.equals(a, other.a) && Objects.equals(b, other.b)) || (Objects.equals(a, other.b) && Objects.equals(b, other.a));
  }

  public File getA() {
    return a;
  }

  public File getB() {
    return b;
  }

}
