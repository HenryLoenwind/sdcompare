package info.loenwind.compare;

public class CompData {

  private int count = 0;
  private int likes = 0;

  public int getCount() {
    return count;
  }

  public int getLikes() {
    return likes;
  }

  public void like() {
    count++;
    likes++;
  }

  public void dislike() {
    count++;
  }

  public int getScore() {
    return count == 0 ? 0 : (likes * 1000 / count);
  }

}
